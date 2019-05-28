package gov.nist.healthcare.hl7ws.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

public class XmlUtils {
	/**
	 * Check if the xmlObject is valid. For debug use only.
	 * 
	 * @param xmlObject
	 *            the xmlObject.
	 */
	public static void checkXmlObjectForDebug(XmlObject xmlObject, String name) {
		ArrayList<XmlError> validationErrors = new ArrayList<XmlError>();
		XmlOptions validationOptions = new XmlOptions();
		validationOptions.setErrorListener(validationErrors);
		if (!xmlObject.validate(validationOptions)) {
			System.err.println(name + " is not valid: ");
			Iterator<XmlError> iter = validationErrors.iterator();
			while (iter.hasNext()) {
				XmlError xe = iter.next();
				System.err.println(xe.getMessage());
			}
		}
	}

	public static String checkXmlObjectForError(XmlObject xmlObject) {
		String result = null;
		ArrayList<XmlError> validationErrors = new ArrayList<XmlError>();
		XmlOptions validationOptions = new XmlOptions();
		validationOptions.setErrorListener(validationErrors);
		if (!xmlObject.validate(validationOptions)) {
			Iterator<XmlError> iter = validationErrors.iterator();
			StringBuffer buf = new StringBuffer();
			while (iter.hasNext()) {
				XmlError xe = iter.next();
				buf.append(xe.getMessage());
			}
			result = buf.toString();
		}
		return result;
	}

}
