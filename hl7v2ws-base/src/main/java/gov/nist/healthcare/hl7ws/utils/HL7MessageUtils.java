package gov.nist.healthcare.hl7ws.utils;

import gov.nist.healthcare.core.MalformedMessageException;
import gov.nist.healthcare.core.MalformedProfileException;
import gov.nist.healthcare.core.message.MessageLocation;
import gov.nist.healthcare.core.message.v2.er7.Er7Message;
import gov.nist.healthcare.core.message.v2.xml.XmlMessage;
import gov.nist.healthcare.generation.DataValueLocationItemGeneration;
import gov.nist.healthcare.generation.MessageInstanceSpecificValuesGeneration;
import gov.nist.healthcare.generation.message.HL7V2MessageGenerationContextDefinitionDocument;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResult;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResultDocument;
import gov.nist.healthcare.message.MessageElement;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xmlbeans.XmlException;
import org.xml.sax.SAXException;

public class HL7MessageUtils {

	public static void main(String[] args) throws IOException,
			MalformedMessageException, XmlException, TransformerException,
			ParserConfigurationException, SAXException,
			MalformedProfileException {
		// get the template
		File mFile = new File("C:/temp/template-valid.er7");
		File gFile = new File(
				"C:/temp/message-generation-context-test-simple.xml");
		// List<ValuedMessageLocation> templateLocations =
		// getValuedMessageLocations(IOUtils
		// .toString(new FileReader(mFile)));
		// updateValuedLocations(IOUtils.toString(new FileReader(gFile)),
		// templateLocations);
		// HL7V2MessageGenerationContextDefinitionDocument context =
		// buildGenerationContext(templateLocations);
		//
		// System.out.println(context.toString());
		//
		// MessageInstanceSpecificValuesGeneration misv = context
		// .getHL7V2MessageGenerationContextDefinition()
		// .getMessageInstanceSpecificValues();
		// Iterator<DataValueLocationItemGeneration> itDVLI = misv
		// .getDataValueLocationItemList().iterator();
		//
		// MessageLocation msh9_1 = new MessageLocation("MSH", 1, 9, 1, 1);
		// MessageLocation msh9_2 = new MessageLocation("MSH", 1, 9, 1, 2);
		// MessageLocation msh9_3 = new MessageLocation("MSH", 1, 9, 1, 3);
		// String msh9_1_value = null;
		// String msh9_2_value = null;
		// String msh9_3_value = null;
		//
		// while (itDVLI.hasNext()) {
		// DataValueLocationItemGeneration item = itDVLI.next();
		// MessageLocation location = HL7MessageUtils.getMessageLocation(item);
		// if (location.equals(msh9_1)) {
		// msh9_1_value = item.getValueArray(0);
		// } else if (location.equals(msh9_2)) {
		// msh9_2_value = item.getValueArray(0);
		// } else if (location.equals(msh9_3)) {
		// msh9_3_value = item.getValueArray(0);
		// }
		// if (msh9_1_value != null && msh9_2_value != null
		// && msh9_3_value != null) {
		// break;
		// }
		// }
		//
		// String pFileName = "C:/temp/profiles/";
		// if (msh9_3_value != null) {
		// pFileName = pFileName + "NIST_" + msh9_3_value + ".xml";
		// }
		//
		// Profile profile = new Profile("", new File(getProfile())));
		//
		// MessageGeneration mg = new MessageGeneration();
		//
		// MessageGenerationResult result = mg.generate(profile, context);
		//
		// List<HL7V2Message> messages = result.getMessages();
		//
		// for (HL7V2Message m : messages) {
		// System.out.println(m.getMessageAsString());
		// }
		//
		// for (String error : result.getErrors()) {
		// System.out.println(error);
		// }

		// System.out.println(result.getMessages().get(0));

		// for (gov.nist.healthcare.hl7ws.messagegeneration.MessageElement
		// element : elements) {
		// System.out.println(element.getLocation().toString() + "="
		// + element.getValue());
		// }

		// System.out.println(context.toString());

	}

	public static boolean isER7(String msgString) {
		String firstSegment = msgString.substring(0, 3).toUpperCase();
		return firstSegment.startsWith("MSH");
	}

	public static boolean isXML(String msgString) {
		char firstCharacter = msgString.charAt(0);
		return firstCharacter == '<';
	}

	/**
	 * Get the path from the data value.
	 * 
	 * @param aDataValueLocationItem
	 *            a DataValueLocationItem.
	 * @return the associated er7path
	 */
	public static MessageLocation getMessageLocation(
			DataValueLocationItemGeneration aDataValueLocationItem) {
		String segName = "";
		int segOccurence = 0;
		int fieldNumber = 0;
		int fieldOccurence = 0;
		int compNumber = 0;
		int subCompNumber = 0;
		MessageElement dataLocation = aDataValueLocationItem.getLocation();
		if (dataLocation.getSegment() != null) {
			segName = aDataValueLocationItem.getLocation().getSegment()
					.getName();
			segOccurence = aDataValueLocationItem.getLocation().getSegment()
					.getInstanceNumber();
			if (segOccurence == 0) {
				segOccurence = 1;
			}
			if (dataLocation.getSegment().getField() != null) {
				fieldNumber = dataLocation.getSegment().getField()
						.getPosition();
				fieldOccurence = dataLocation.getSegment().getField()
						.getInstanceNumber();
				if (dataLocation.getSegment().getField().getComponent() != null) {
					compNumber = dataLocation.getSegment().getField()
							.getComponent().getPosition();
					if (dataLocation.getSegment().getField().getComponent()
							.getSubComponent() != null) {
						subCompNumber = dataLocation.getSegment().getField()
								.getComponent().getSubComponent().getPosition();
					}
				}
			}
		}
		MessageLocation ml = null;
		if (fieldNumber > 0) {
			if (fieldOccurence == 0) {
				fieldOccurence = 1;
			}
			if (compNumber > 0) {
				if (subCompNumber > 0) {
					ml = new MessageLocation(segName, segOccurence,
							fieldNumber, fieldOccurence, compNumber,
							subCompNumber);
				} else {
					ml = new MessageLocation(segName, segOccurence,
							fieldNumber, fieldOccurence, compNumber);
				}
			} else {
				ml = new MessageLocation(segName, segOccurence, fieldNumber,
						fieldOccurence);
			}
		} else {
			ml = new MessageLocation(segName, segOccurence);
		}

		return ml;
	}

	public static boolean checkUpdatedMessage(String result,
			String xmlGenerationContext) throws MalformedMessageException,
			XmlException {
		HL7V2MessageGenerationResultDocument doc = HL7V2MessageGenerationResultDocument.Factory
				.parse(result);
		HL7V2MessageGenerationResult r = doc.getHL7V2MessageGenerationResult();
		String message = r.getMessageGenerated();

		HL7V2MessageGenerationContextDefinitionDocument generationContext = HL7V2MessageGenerationContextDefinitionDocument.Factory
				.parse(xmlGenerationContext);
		MessageInstanceSpecificValuesGeneration misv = generationContext
				.getHL7V2MessageGenerationContextDefinition()
				.getMessageInstanceSpecificValues();

		MessageLocation location = null;
		Iterator<DataValueLocationItemGeneration> itDVLI = misv
				.getDataValueLocationItemList().iterator();
		if (HL7MessageUtils.isXML(message)) {
			XmlMessage xml = new XmlMessage(message);
			while (itDVLI.hasNext()) {
				DataValueLocationItemGeneration item = itDVLI.next();
				location = HL7MessageUtils.getMessageLocation(item);
				// the value from the generation context
				String contextValue = item.getValueArray(0);
				// the value from the message generated
				String templateValue = xml.getValue(location);
				if (!contextValue.equals(templateValue)) {
					return false;
				}
			}
		} else {
			// message = new Er7Message(template);
			Er7Message er7 = new Er7Message(message);
			while (itDVLI.hasNext()) {
				DataValueLocationItemGeneration item = itDVLI.next();
				location = HL7MessageUtils.getMessageLocation(item);
				// the value from the generation context
				String contextValue = item.getValueArray(0);
				// the value from the message generated
				String templateValue = er7.getValue(location);
				if (!contextValue.equals(templateValue)) {
					return false;
				}
			}
		}
		return true;

	}
}
