/*
 * NIST HL7 WS
 * MessageValidationUtils.java Feb 15, 2008
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.utils;

import gov.nist.healthcare.core.message.v2.HL7V2Message;
import gov.nist.healthcare.core.message.v2.er7.Er7Message;
import gov.nist.healthcare.core.message.v2.xml.XmlMessage;
import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.core.validation.message.MessageValidationConstants;
import gov.nist.healthcare.core.validation.message.v2.MessageValidationContextV2;
import gov.nist.healthcare.core.validation.message.v2.MessageValidationResultV2;
import gov.nist.healthcare.core.validation.message.v2.MessageValidationV2;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.hl7ws.registry.Registry;
import gov.nist.healthcare.hl7ws.registry.TempRegistry;
import gov.nist.healthcare.validation.message.ReportHeader;
import gov.nist.healthcare.validation.message.ResultOfTestType;
import gov.nist.healthcare.validation.message.StandardTypeType;
import gov.nist.healthcare.validation.message.ValidationStatusType;
import gov.nist.healthcare.validation.message.ValidationType;
import gov.nist.healthcare.validation.message.hl7.v2.report.HL7V2MessageValidationReportDocument;
import gov.nist.healthcare.validation.message.hl7.v2.report.HL7V2MessageValidationReportDocument.HL7V2MessageValidationReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import noNamespace.HL7ResourceDataType;
import noNamespace.HL7ResourceType;
import noNamespace.XmlConfigDocument;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * Utilities for the MessageValidation.
 * 
 * @author Roch Bertucat (NIST)
 */
public class MessageValidationV2Utils {

	protected final Logger log = Logger.getLogger("gov.nist.healthcare.hl7ws");
	private Map<String, String> hl7DefaultTables;

	public void setHl7DefaultTables(Map<String, String> hl7DefaultTables) {
		this.hl7DefaultTables = hl7DefaultTables;
	}

	/**
	 * Get the message validation context object from its string representation.
	 * 
	 * @param mvc
	 *            the string representation
	 * @return the message validation context
	 * @throws XmlException
	 *             if the parsing fails
	 */
	public MessageValidationContextV2 getMVC(String mvc) throws XmlException {
		log.info("MessageValidationInterface - User sets a message validation context");
		MessageValidationContextV2 messageValidContext = new MessageValidationContextV2();
		messageValidContext.load(mvc);
		return messageValidContext;
	}

	/**
	 * Validate a message against a profile or against a message validation
	 * context.
	 * 
	 * @param profile
	 *            a profile
	 * @param message
	 *            a message
	 * @param messageValidContext
	 *            a validation context
	 * @param resources
	 *            some resources
	 * @return the message validation result
	 * @throws Exception
	 *             if an exception occurs
	 */
	public MessageValidationResultV2 validate(Profile profile,
			HL7V2Message message,
			MessageValidationContextV2 messageValidContext,
			ArrayList<TableLibraryDocument> resources, boolean addDefaultTable)
			throws Exception {
		MessageValidationResultV2 mvResult = null;
		if ("".equals(messageValidContext) || messageValidContext == null) {
			messageValidContext = new MessageValidationContextV2();
		}
		log.info("MessageValidationInterface - User runs a Validation");
		// connectathon
		logFile(message.toString(), "message");
		// Get report
		MessageValidationV2 validator = new MessageValidationV2();
		if (profile == null) {
			mvResult = validator.validate(message, messageValidContext);
		} else if (resources.isEmpty() && addDefaultTable) {
			System.out.println("Calling validate without additional resources!!!!!\n");
			mvResult = validator
					.validate(message, profile, messageValidContext);
		} else {
			System.out.println("Calling validate with additional resources\n");
			mvResult = validator.validate(message, profile,
					messageValidContext, resources);
		}
		if (mvResult == null) {
			throw new Exception("Report is null. Please try again later.");
		}
		// connectathon
		logFile(mvResult.getReport().toString(), "report");
		return mvResult;
	}

	/**
	 * Get the AbstractMessage from its string representation
	 * 
	 * @param messageStr
	 *            the string representation of a message
	 * @return the AbstractMessage
	 * @throws Exception
	 */
	public HL7V2Message getMessage(String messageStr) throws Exception {
		HL7V2Message message = null;
		// determine if the message is ER7 or XML
		if (HL7MessageUtils.isXML(messageStr)) {
			// parse with sax: xml file
			InputSource inputSource = new InputSource(new BufferedReader(
					new StringReader(messageStr)));
			SAXParser saxParser;
			try {
				saxParser = SAXParserFactory.newInstance().newSAXParser();
				saxParser.parse(inputSource, new DefaultHandler2());
				message = new XmlMessage(messageStr);
			} catch (ParserConfigurationException e) {
				throw new Exception(
						"Internal error: ParserConfigurationException");
			} catch (SAXException e) {
				throw new Exception("Invalid message: Cannot be parsed.");
			}
		} else {
			message = new Er7Message(messageStr);
		}
		return message;
	}

	public void logFile(String messageStr, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssZ");
		String cat = (String) System.getProperties().get("catalina.home");
		File f = new File(cat + "/logs/hl7ws/msgValid_" + type + "_"
				+ sdf.format(new Date()) + ".xml");
		try {
			PrintWriter out = new PrintWriter(new FileWriter(f));
			out.print(messageStr);
			out.close();
		} catch (IOException e) {
			log.info("Couldn't create the file " + f.getAbsolutePath()
					+ " to log the message. (MV)");
		}
	}

	/**
	 * Extract the resources (tables) from the xmlConfig. The process is quite
	 * delicate. See documentation for more details.
	 * 
	 * @param registry
	 *            the registry to get the default tables from
	 * @param tempRegistry
	 *            the temp registry to get the user tables from
	 * @param version
	 *            the version of the profile if no default table is specified
	 * @return an array of resources
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ArrayList<TableLibraryDocument> getResources(
			XmlConfigDocument xmlConfigDoc, Registry registry,
			TempRegistry tempRegistry, String version, boolean addDefaultTable)
			throws Exception {
		
		System.out.println("Got here!!!!\n");
		ArrayList<TableLibraryDocument> resources = new ArrayList<TableLibraryDocument>();
		List<HL7ResourceType> hl7Resources = Arrays.asList(xmlConfigDoc.getXmlConfig()
				.getHL7V2Config().getHL7ResourceArray());

		System.out.println("getRescources: length of hl7Resources list = " + hl7Resources.size());
		
		// need to sort the resources for the validation
		TreeMap<String, String> toSort = new TreeMap<String, String>();

		// we add the default table by default
		for (HL7ResourceType curResource : hl7Resources) {
			if (HL7ResourceDataType.HL_7_TABLE == curResource.getType()) {
				String order = curResource.getOrder();
				String oid = curResource.getOid();

				// if order=0 and the table's oid is specified, we don't add the
				// default table
				System.out.println("getResoures: order = " + order);
				if ("0".equals(order)
						&& hl7DefaultTables.values().contains(oid)) {
					addDefaultTable = false;
				} else {
					// we add the table following its order
					System.out.println("getResources: putting " + order + " " + oid);
					toSort.put(order, oid);
				}
			}
		}
		
		addDefaultTable = false; // MDI
		
		// default
		if (addDefaultTable) {
			System.out.println("getResources: addDefaultTable is still True\n");
			String lastOrder = "1";
			if (!toSort.isEmpty()) {
				lastOrder = String
						.valueOf(Integer.valueOf(toSort.lastKey()) + 1);
			}
			if (getStandardVersion(version) != null) {
				System.out.println("getRescources: lastOrder = " + lastOrder);
				toSort.put(lastOrder, hl7DefaultTables.get(version));
			}
		}
		
		// MDI
		System.out.println("Forcing the add of two table resources!\n");
		resources.add(tempRegistry.getTable("2.16.840.1.113883.3.72.4.2.99003"));
		resources.add(tempRegistry.getTable("2.16.840.1.113883.3.72.4.2.99004"));
		resources.add(tempRegistry.getTable("2.16.840.1.113883.3.72.4.2.99001"));
		resources.add(tempRegistry.getTable("2.16.840.1.113883.3.72.4.2.99002"));

		return resources;
//
//		// fill out the resources list with the actual table files following
//		// the order
//		for (String oid : toSort.values()) {
//			System.out.println("Looping: oid = " + oid);
//			if (hl7DefaultTables.values().contains(oid)) {
//				// Get the defaultTable from the registry
//				//MDI: TableLibraryDocument defaultTable = registry.getTable(oid);
//			    TableLibraryDocument defaultTable = tempRegistry.getTable(oid);
//				System.out.println("Retrieved default Table OK, OID = " + oid);
//				resources.add(defaultTable);
//			} else {
//				TableLibraryDocument curTable = null;
//				try {
//					//MDI: curTable = registry.getTable(oid);
//					curTable = tempRegistry.getTable(oid);
//			//MDI } catch (FileNotFoundException fe) {		
//				} catch (Exception fe) {
//					// check in the tempRegistry
//					System.out.println("Caught exception!!!!!!\n");
//					curTable = tempRegistry.getTable(oid);
//				}
//				if (curTable == null) {
//					// error
//					throw new Exception(
//							"A specified OID in the xmlConfig is not available. "
//									+ "Use loadResource().");
//				}
//				resources.add(curTable);
//			}
//		}
//		return resources;
	}

	/**
	 * Build the generic message validation report.
	 * 
	 * @param mvResult
	 *            the MessageValidationResult from the core
	 * @param message
	 *            the message
	 * @param profile
	 *            the profile
	 * @param xmlConfigDoc
	 *            the xmlConfig document
	 * @param oid
	 *            the profile OID
	 * @throws Exception
	 *             if an exception occurs
	 */
	public void buildReport(MessageValidationResultV2 mvResult,
			HL7V2Message message, Profile profile,
			XmlConfigDocument xmlConfigDoc, String oid) throws Exception {
		// ReportHeader reportHeader = buildReportHeader(report, profile, oid);
		// // finish the reportHeader
		// if (mvResult.isValid()) {
		// reportHeader.setResultOfTest(ResultOfTestType.PASSED);
		// } else {
		// reportHeader.setResultOfTest(ResultOfTestType.FAILED);
		// }
		// reportHeader.setErrorCount(mvResult.getErrorCount());
		// reportHeader.setWarningCount(mvResult.getWarningCount());
		// reportHeader.setAlertCount(mvResult.getAlertCount());
		// reportHeader.setIgnoreCount(mvResult.getIgnoreCount());
		// reportHeader.setAffirmCount(mvResult.getAffirmationCount());
		// // report details
		// HL7V2MessageReport reportDetails = report.addNewSpecificReport();
		// if (profile != null) {
		// reportDetails.getMetaData().getProfile()
		// .setOrganization(profile.getOrganization());
		// } else {
		// reportDetails.getMetaData().getProfile().setOrganization("");
		// }
		// Message metaDataMessage = reportDetails.getMetaData().getMessage();
		// if (xmlConfigDoc != null
		// && xmlConfigDoc.getXmlConfig().getHL7V2Config()
		// .getMessageFilename() != null
		// && xmlConfigDoc.getXmlConfig().getHL7V2Config().getMessageID() !=
		// null) {
		// metaDataMessage.setFilename((xmlConfigDoc.getXmlConfig()
		// .getHL7V2Config().getMessageFilename()));
		// metaDataMessage.setId(xmlConfigDoc.getXmlConfig().getHL7V2Config()
		// .getMessageID());
		// } else {
		// metaDataMessage.setFilename("");
		// metaDataMessage.setId("");
		// }
		// if (message instanceof XmlMessage) {
		// metaDataMessage.setEncoding(EncodingConstants.XML);
		// } else if (message instanceof Er7Message) {
		// metaDataMessage.setEncoding(EncodingConstants.ER_7);
		// }
		// metaDataMessage.setTransformed(false);
		// // copy the MV report
		// XmlObject xmlObject = mvResult
		// .getReport()
		// .getHL7V2MessageValidationReport()
		// .changeType(
		// gov.nist.hl7.ws.messagevalidation.hl7v2report.Hl7V2ValidationReportType.type);
		// Hl7V2ValidationReportType validReportType =
		// Hl7V2ValidationReportType.Factory
		// .parse(xmlObject.newInputStream());
		// reportDetails.setHl7V2ValidationReport(validReportType);
		// include XmlMessage + Er7Message representation

		// TODO: add message representation
		// if (xmlConfigDoc != null) {
		// boolean includeXmlMessage = xmlConfigDoc.getXmlConfig()
		// .getHL7V2Config().getXmlMessage();
		// reportDetails.setXmlMessage(includeXmlMessage);
		// boolean includeEr7Message = xmlConfigDoc.getXmlConfig()
		// .getHL7V2Config().getEr7Message();
		// reportDetails.setEr7Message(includeEr7Message);
		// reportDetails.getMetaData().get
		//
		// addMessageRepresentation(reportDetails.getHl7V2ValidationReport()
		// .getMessageValidation(), message, profile,
		// includeXmlMessage, includeEr7Message);
		// }
	}

	/**
	 * Build the header of the generic message validation report.
	 * 
	 * @param report
	 *            the generic message validation report
	 * @param profile
	 *            the profile
	 * @param oid
	 *            the profile OID
	 * @return the report header
	 */
	// public ReportHeader buildReportHeader(HL7V2MessageValidationReport
	// report,
	// Profile profile, String oid) {
	// // report header
	// ReportHeader reportHeader = report.addNewHeaderReport();
	// Calendar dateOfTest = Calendar.getInstance();
	// dateOfTest.clear(Calendar.ZONE_OFFSET);
	//
	// // TODO: set from xml configuration files
	// reportHeader.setValidationStatus(ValidationStatusType.COMPLETE);
	// reportHeader.setServiceName("HL7 V2 Validation");
	// reportHeader.setServiceProvider("NIST");
	// reportHeader.setServiceVersion("1.0");
	// reportHeader.setStandardType("HL7 V2");
	// if (profile != null) {
	// reportHeader.setStandardVersion(getStandardVersion(profile
	// .getHl7VersionAsString()));
	// reportHeader.setProfileName(profile.getName());
	// reportHeader.setProfileType(profile.getMessageStructureID());
	// reportHeader.setProfileVersion(profile.getVersion());
	// } else {
	// reportHeader.setStandardVersion(StandardVersionType.N_A);
	// }
	// reportHeader.setValidationType("Automated");
	// reportHeader.setTestIdentifier("testId");
	// reportHeader.setDateOfTest(dateOfTest);
	// reportHeader.setTimeOfTest(Calendar.getInstance());
	//
	// if (oid != null) {
	// reportHeader.setValidationObjectReference(oid);
	// }
	// // if (profile != null) {
	// // reportHeader.setTestObjectReference(profile.getFilename());
	// // }
	//
	// return reportHeader;
	// }

	/**
	 * Add the message representation to the validation result.
	 * 
	 * @param mvResult
	 *            the validation result
	 * @param message
	 *            the message
	 * @param profile
	 *            the profile
	 * @param includeXmlMessage
	 *            if the XML representation of the message has to be included
	 * @param includeEr7Message
	 *            if the er7 representation of the message has to be included
	 */
	// private void addMessageRepresentation(MessageValidationType mv,
	// HL7Message message, Profile profile, boolean includeXmlMessage,
	// boolean includeEr7Message) {
	// String er7Message, xmlMessage;
	// // add XMLMessage and ER7Message if available
	// if (message instanceof Er7Message) {
	// er7Message = ((Er7Message) message).getMessageAsString();
	// xmlMessage =
	// "Cannot transform the message in its XML representation. Service not available";
	//
	// // try {
	// // xmlMessage = Bar2Xml.parse(er7Message, profile).toString();
	// // } catch (Exception e) {
	// // xmlMessage =
	// // "Cannot transform the message in its XML representation.";
	// // }
	//
	// } else {
	// xmlMessage = ((XmlMessage) message).getMessageAsString();
	// try {
	// er7Message = Xml2Bar.parse((XmlMessage) message)
	// .getMessageAsString();
	// } catch (Exception e) {
	// er7Message = "Cannot transform the message in its ER7 representation.";
	// }
	// }
	//
	// if (includeEr7Message) {
	// mv.setER7Message(er7Message);
	// }
	//
	// if (includeXmlMessage) {
	// mv.setXMLMessage(xmlMessage);
	// }
	// }

	/**
	 * Get the standard version type from the string.
	 * 
	 * @param version
	 *            the string version
	 * @return the enum version
	 */
	private gov.nist.healthcare.validation.message.StandardTypeType.Enum getStandardVersion(
			String version) {
		gov.nist.healthcare.validation.message.StandardTypeType.Enum ret = null;
		if ("2.5.1".equals(version) || "2.5".equals(version)
				|| "2.3.1".equals(version) || "2.4".equals(version)) {
			ret = StandardTypeType.HL_7_V_2;
		}
		return ret;
	}

	public HL7V2MessageValidationReportDocument buildExceptionReport(
			String exception) {
		HL7V2MessageValidationReportDocument doc = HL7V2MessageValidationReportDocument.Factory
				.newInstance();
		HL7V2MessageValidationReport report = doc
				.addNewHL7V2MessageValidationReport();
		setExceptionReportHeader(report, exception);
		return doc;
	}

	private void setExceptionReportHeader(HL7V2MessageValidationReport report,
			String exception) {
		ReportHeader header = report.addNewHeaderReport();
		header.setValidationStatus(ValidationStatusType.INCOMPLETE);
		header.setServiceName("NIST HL7V2 Message Validation");
		header.setServiceProvider("NIST");
		header.setServiceVersion(MessageValidationConstants.MESSAGE_VALIDATION_V2_VERSION);
		header.setStandardType(StandardTypeType.HL_7_V_2);
		header.setValidationType(ValidationType.AUTOMATED);
		header.setTestIdentifier("");
		header.setValidationStatusInfo(exception);
		header.setAffirmCount(0);
		header.setErrorCount(1);
		header.setWarningCount(0);
		header.setIgnoreCount(0);
		header.setAlertCount(0);
		header.setResultOfTest(ResultOfTestType.FAILED);
	}
}
