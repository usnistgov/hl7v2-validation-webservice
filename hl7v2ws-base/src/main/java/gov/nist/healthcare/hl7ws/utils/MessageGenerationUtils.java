/*
 * NIST HL7 WS
 * MessageGenerationUtils.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.utils;

import gov.nist.healthcare.core.generation.MessageGeneration;
import gov.nist.healthcare.core.generation.MessageGenerationResult;
import gov.nist.healthcare.core.message.MessageLocation;
import gov.nist.healthcare.core.message.ValuedMessageLocation;
import gov.nist.healthcare.core.message.v2.HL7V2Message;
import gov.nist.healthcare.core.message.v2.er7.ESegment;
import gov.nist.healthcare.core.message.v2.er7.Er7Message;
import gov.nist.healthcare.core.message.v2.xml.XmlMessage;
import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.generation.DataValueLocationItemGeneration;
import gov.nist.healthcare.generation.MessageInstanceSpecificValuesGeneration;
import gov.nist.healthcare.generation.message.HL7V2MessageGenerationContextDefinition;
import gov.nist.healthcare.generation.message.HL7V2MessageGenerationContextDefinitionDocument;
import gov.nist.healthcare.hl7Ws.generationresult.GenerationErrors;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResult;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResultDocument;
import gov.nist.healthcare.hl7ws.Constants;
import gov.nist.healthcare.hl7ws.registry.Registry;
import gov.nist.healthcare.hl7ws.registry.TempRegistry;
import gov.nist.healthcare.message.Component;
import gov.nist.healthcare.message.EncodingConstants.Enum;
import gov.nist.healthcare.message.MessageElement;
import gov.nist.healthcare.message.Segment;
import gov.nist.healthcare.message.SubComponent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.transform.TransformerFactoryConfigurationError;

import noNamespace.HL7ResourceType;
import noNamespace.XmlConfigDocument;

import org.apache.cxf.helpers.IOUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Harold AFFO (NIST)
 */

public class MessageGenerationUtils {
	protected final Logger log = Logger.getLogger("gov.nist.healthcare.hl7ws");
	private TempRegistry tempRegistry;
	private Registry registry;

	/**
	 * @param profile
	 * @param table
	 * @param xmlGenerationContext
	 * @return
	 * @throws TransformerFactoryConfigurationError
	 * @throws Exception
	 */
	public HL7V2MessageGenerationResultDocument generate(Profile profile,
			TableLibraryDocument table,
			HL7V2MessageGenerationContextDefinitionDocument xmlGenerationContext)
			throws TransformerFactoryConfigurationError, Exception {
//	public String generate(Profile profile,
//			TableLibraryDocument table,
//			HL7V2MessageGenerationContextDefinitionDocument xmlGenerationContext)
//			throws TransformerFactoryConfigurationError, Exception {
		MessageGeneration mg = new MessageGeneration();
		if (xmlGenerationContext == null) {
			throw new Exception("The generation context file is null\n");
		}
		if (profile == null) {
			throw new Exception("The profile is null\n");
		}
		log.info("MessageGeneration - The generation has started");
		//MDI: MessageGenerationResult result = mg.generate(profile,xmlGenerationContext, null, table, null);
		//MDI: added next line
		MessageGenerationResult result=null;
		System.out.println("RESULT -->" + result.getMessages().get(0).getMessageAsString());
		log.info("MessageGeneration - The Generation is finished");
		return getResultDocument(result);  // need to modify the HL7V2MessageGenerationResultsDocument to use CDATA around the message to protect it from being escaped
		//return result.getMessages().get(0).getMessageAsString();
	}

	private HL7V2MessageGenerationResultDocument getResultDocument(
			MessageGenerationResult result) {
		HL7V2MessageGenerationResultDocument doc = HL7V2MessageGenerationResultDocument.Factory
				.newInstance();
		HL7V2MessageGenerationResult res = doc
				.addNewHL7V2MessageGenerationResult();
		if (result.getMessages() != null && !result.getMessages().isEmpty()) {
			//res.setMessageGenerated(result.getMessages().get(0).getMessageAsString());
			res.setMessageGenerated(new String(Base64.encodeBase64(result.getMessages().get(0).getMessageAsString().getBytes())));
		}

		List<String> errors = result.getErrors();
		if (errors != null && !errors.isEmpty()) {
			GenerationErrors xmlErrors = res.addNewGenerationErrors();
			for (String error : errors) {
				xmlErrors.addGenerationError(error);
			}
		}

		return doc;
	}

	/**
	 * @param xmlGenerationContext
	 * @return
	 * @throws Exception
	 */
	public HL7V2MessageGenerationContextDefinitionDocument getXmlGenerationContext(
			String xmlGenerationContext) throws Exception {

		if (xmlGenerationContext == null || "".equals(xmlGenerationContext)) {
			throw new Exception("The generation context is null");
		}
		HL7V2MessageGenerationContextDefinitionDocument xmlGenerationCtxt = HL7V2MessageGenerationContextDefinitionDocument.Factory
				.parse(xmlGenerationContext);
		return xmlGenerationCtxt;
	}

	/**
	 * @param xmlConfig
	 * @param profileVersion
	 * @return
	 * @throws Exception
	 */
	public TableLibraryDocument getTable(String xmlConfig, String profileVersion)
			throws Exception {
		TableLibraryDocument tempTable = null;
		if (!("".equals(xmlConfig) || xmlConfig == null)) {
			XmlConfigDocument xmlConfigDoc = XmlConfigDocument.Factory
					.parse(xmlConfig);
			if (!xmlConfigDoc.validate()) {
				throw new Exception("XmlConfig is invalid");
			}
			List<HL7ResourceType> hl7Resources = Arrays.asList(xmlConfigDoc.getXmlConfig()
					.getHL7V2Config().getHL7ResourceArray());
			// if the table is not registered
			for (Iterator<HL7ResourceType> it = hl7Resources.iterator(); it
					.hasNext();) {
				HL7ResourceType curResourceType = it.next();
				String type = curResourceType.getType().toString();
				String curOid = null;
				if (type.equals("HL7table")) {
					curOid = curResourceType.getOid();
					TableLibraryDocument curTable = tempRegistry
							.getTable(curOid);
					if (curTable != null) {
						String tableversion = curTable.getTableLibrary()
								.getTableLibraryVersion();
						if (tableversion.equals(profileVersion)) {
							tempTable = curTable;
							break;
						}
					}
				}
			}
		}
		return tempTable;
	}

	public Profile getProfile(String oid) throws Exception {
		Profile profile = tempRegistry.getProfile(oid);
		if (profile == null) {
			//profile = registry.getProfile(oid);
		}
		if (profile == null) {
			throw new Exception("Can't find a profile with oid=" + oid);
		}
		return profile;
	}

	/**
	 * @param tempRegistry
	 *            . Needed for dependency injection
	 * 
	 */
	public void setTempRegistry(TempRegistry tempRegistry) {
		this.tempRegistry = tempRegistry;
	}

	/**
	 * @param registry
	 *            the registry to set. Needed for dependency injection
	 */
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public HL7V2MessageGenerationResultDocument generateFromTemplate(
			String template, String oid, String xmlConfig,
			String xmlGenerationContext) throws Exception {
//	public String generateFromTemplate(
//			String template, String oid, String xmlConfig,
//			String xmlGenerationContext) throws Exception {
		checkData(template, xmlGenerationContext);
		HL7V2MessageGenerationContextDefinitionDocument generationContext = getXmlGenerationContext(xmlGenerationContext);
		HL7V2MessageGenerationContextDefinition context = generationContext
				.getHL7V2MessageGenerationContextDefinition();

		Profile profile = null;
		HL7V2Message message = null;
		if (HL7MessageUtils.isER7(template)) {
			message = new Er7Message(template);
		} else if (HL7MessageUtils.isXML(template)) {
			message = new XmlMessage(template);
		}
		if (!("".equals(oid) || oid == null)) {
			profile = getProfile(oid);
			logFile(oid, "profileOID");
		} else {
			if (HL7MessageUtils.isER7(template)) {
				profile = getProfile(message);
			} else if (HL7MessageUtils.isXML(template)) {
				profile = getProfile(message);
			}
		}

		TableLibraryDocument table = getTable(xmlConfig,
				profile.getHl7VersionAsString());

		// generationContext.getHL7V2MessageGenerationContextDefinition().get
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// Calendar cal = Calendar.getInstance();
		// Date date;
		return generateFromTemplate(message, profile, table, context);
	}

	private HL7V2MessageGenerationResultDocument generateFromTemplate(
			HL7V2Message message, Profile profile, TableLibraryDocument table,
			HL7V2MessageGenerationContextDefinition context) throws Exception {
//	private String generateFromTemplate(
//			HL7V2Message message, Profile profile, TableLibraryDocument table,
//			HL7V2MessageGenerationContextDefinition context) throws Exception {
		MessageInstanceSpecificValuesGeneration misv = context
				.getMessageInstanceSpecificValues();
		List<ValuedMessageLocation> templateLocations = getValuedMessageLocations(
				message, profile);
		updateValuedLocations(misv, templateLocations);
		HL7V2MessageGenerationContextDefinitionDocument generationContext = buildGenerationContext(
				templateLocations, context.getEncoding());
		return generate(profile, table, generationContext);
	}

	private void checkData(String template, String xmlGenerationContext)
			throws Exception {
		if (xmlGenerationContext == null || "".equals(xmlGenerationContext)) {
			throw new Exception("The generation context is required");
		}
		if (template == null || "".equals(template)) {
			throw new Exception("The template is required");
		}
		HL7V2MessageGenerationContextDefinitionDocument generationContext = getXmlGenerationContext(xmlGenerationContext);
		String errors = XmlUtils.checkXmlObjectForError(generationContext);
		if (errors != null) {
			throw new Exception("the generation context is not valid.\n"
					+ errors);
		}
	}

	/**
	 * create a list of (MessageLocation, Value) called MessageElement
	 * 
	 * @param template
	 * @return the list of elements
	 * @throws Exception
	 */
	public List<ValuedMessageLocation> getValuedMessageLocations(
			HL7V2Message template, Profile profile) throws Exception {
		List<ValuedMessageLocation> results = new ArrayList<ValuedMessageLocation>();
		if (template instanceof Er7Message) {
			Er7Message er7 = (Er7Message) template;
			Map<String, List<ESegment>> segments = er7.getSegments();
			HashMap<String, Integer> segmentInstanceCounter = new HashMap<String, Integer>();
			Set<String> segmentNames = segments.keySet();
			for (String seg : segmentNames) {
				int segmentInstanceNumber = updateSegmentsCounter(
						segmentInstanceCounter, seg);
				results.addAll(er7.getLocations(profile, new MessageLocation(
						seg, segmentInstanceNumber)));
			}
		} else if (template instanceof XmlMessage) {
			// TODO
			throw new Exception(
					"XML message not supported in this version of the tool");
		}
		return results;
	}

	/**
	 * update the presence instance of a segment
	 * 
	 * @param counter
	 * @param name
	 * @return
	 */
	private static int updateSegmentsCounter(HashMap<String, Integer> counter,
			String name) {
		int instanceNumber = 1;
		if (counter.containsKey(name)) {
			instanceNumber = counter.get(name) + 1;
		}
		counter.put(name, instanceNumber);
		return instanceNumber;
	}

	public static HL7V2MessageGenerationContextDefinitionDocument buildGenerationContext(
			List<ValuedMessageLocation> valuedLocations, Enum encoding) {
		HL7V2MessageGenerationContextDefinitionDocument doc = HL7V2MessageGenerationContextDefinitionDocument.Factory
				.newInstance();
		HL7V2MessageGenerationContextDefinition def = doc
				.addNewHL7V2MessageGenerationContextDefinition();
		def.setEncoding(encoding);
		MessageInstanceSpecificValuesGeneration misv = def
				.addNewMessageInstanceSpecificValues();
		for (ValuedMessageLocation valuedLocation : valuedLocations) {
			DataValueLocationItemGeneration item = misv
					.addNewDataValueLocationItem();
			MessageLocation eLocation = valuedLocation;
			MessageElement location = item.addNewLocation();
			Segment segment = location.addNewSegment();
			segment.setInstanceNumber(eLocation.getSegmentInstanceNumber());
			segment.setName(eLocation.getSegmentName());
			// if there is a field
			if (eLocation.getFieldInstanceNumber() > 0
					&& eLocation.getFieldPosition() > 0) {
				gov.nist.healthcare.message.Field f = segment.addNewField();
				f.setInstanceNumber(eLocation.getFieldInstanceNumber());
				f.setPosition(eLocation.getFieldPosition());
				// if there is a component
				if (eLocation.getComponentPosition() > 0) {
					Component c = f.addNewComponent();
					c.setPosition(eLocation.getComponentPosition());
					// if there is a subcomponent
					if (eLocation.getSubComponentPosition() > 0) {
						SubComponent s = c.addNewSubComponent();
						s.setPosition(eLocation.getSubComponentPosition());
					}
				}

			}
			// set the value
			item.setValueArray(new String[] { valuedLocation.getValue() });
		}
		return doc;
	}

	/**
	 * update a list of value locations with values contained in a generation
	 * context
	 * 
	 * @param generationContext
	 * @param valuedLocations
	 * @throws XmlException
	 */
	private static void updateValuedLocations(
			MessageInstanceSpecificValuesGeneration misv,
			List<ValuedMessageLocation> valuedLocations) throws XmlException {
		Iterator<DataValueLocationItemGeneration> itDVLI = misv
				.getDataValueLocationItemList().iterator();
		while (itDVLI.hasNext()) {
			DataValueLocationItemGeneration item = itDVLI.next();
			MessageLocation location = HL7MessageUtils.getMessageLocation(item);
			ValuedMessageLocation v = getValuedMessageLocation(location,
					item.getValueArray(0));
			if (valuedLocations.contains(v)) {
				valuedLocations.get(valuedLocations.indexOf(v)).setValue(
						item.getValueArray(0));
			} else {
				valuedLocations.add(v);
			}
		}
	}

	/**
	 * create a valueMessageLcoation from a value and a MessageLocation
	 * 
	 * @param location
	 * @param value
	 * @return
	 */

	private static ValuedMessageLocation getValuedMessageLocation(
			MessageLocation location, String value) {
		ValuedMessageLocation vml;
		String segName = location.getSegmentName();
		int segOccurence = location.getSegmentInstanceNumber();
		int fieldNumber = location.getFieldPosition();
		int fieldOccurence = location.getFieldInstanceNumber();
		int compNumber = location.getComponentPosition();
		int subCompNumber = location.getSubComponentPosition();
		if (fieldNumber > 0) {
			if (fieldOccurence == 0) {
				fieldOccurence = 1;
			}
			if (compNumber > 0) {
				if (subCompNumber > 0) {
					vml = new ValuedMessageLocation(
							location.getSegmentGroups(), segName, segOccurence,
							fieldNumber, fieldOccurence, compNumber,
							subCompNumber, value);
				} else {
					vml = new ValuedMessageLocation(
							location.getSegmentGroups(), segName, segOccurence,
							fieldNumber, fieldOccurence, compNumber, value);
				}
			} else {
				vml = new ValuedMessageLocation(location.getSegmentGroups(),
						segName, segOccurence, fieldNumber, fieldOccurence,
						value);
			}
		} else {
			vml = new ValuedMessageLocation(location.getSegmentGroups(),
					segName, segOccurence, value);
		}

		return vml;
	}

	private Profile getProfile(HL7V2Message message) throws Exception {
		String messageStructure = message.getMessageStructureID();
		String pFileName = null;
		String messageCode = message.getMessageCode();
		String messageEvent = message.getMessageEvent();
		if (messageStructure == null) {
			if (messageCode != null && messageEvent != null) {
				messageStructure = messageCode + "_" + messageEvent;
			} else {
				String version = message.getVersionAsString();
				if (messageCode != null) {
					messageStructure = messageCode + "_" + version;
				} else if (messageEvent != null) {
					messageStructure = messageEvent + "_" + version;
				}
			}
		}
		if (messageStructure != null) {
			pFileName = tempRegistry.getProfileName(messageStructure);
			if (pFileName == null) {
				throw new Exception(
						"Can't find the profile associated to the tempalte. MessageStruture must be present or MessageCode and MessageEvent muts be present");
			}
			pFileName = pFileName + ".xml";
		}
		InputStream in = MessageGenerationUtils.class
				.getResourceAsStream(Constants.PROFILE + pFileName);
		String profileContent = IOUtils.toString(in);
		Profile profile = new Profile("", profileContent);
		return profile;
	}

	public void logFile(String messageStr, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssZ");
		String cat = (String) System.getProperties().get("catalina.home");
		File f = new File(cat + "/logs/hl7ws/msgGeneration_" + type + "_"
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

}
