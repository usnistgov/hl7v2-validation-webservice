/*
 * NIST HL7 WS
 * MessageValidation.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.messagevalidation;

//import gov.nist.healthcare.hl7ws.messagegeneration.xmlConfig;
//import gov.nist.healthcare.hl7ws.messagegeneration.xmlGenerationContext;
//import gov.nist.healthcare.hl7ws.messagegeneration.xmlResourceList;

import java.util.ArrayList;

import gov.nist.healthcare.unified.enums.Context;
import hl7.v2.validation.content.ConformanceContext;
import hl7.v2.validation.content.DefaultConformanceContext;
import hl7.v2.validation.vs.ValueSetLibrary;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.FormParam;

/**
 * NIST HL7 V2 Message Validation Web Service
 * <p>
 * This web service provides an interface for validating HL7 version 2 (V2)
 * message instances against a message profile and/or a validation context. The
 * interface is designed for use in a state-less web-service environment.
 * <p>
 * This service supports validation for HL7 Version 2.3.1, 2.4, 2.5, and 2.5.1.
 * The version that is used for validation is indicated by HL7 version specified
 * in the message profile.
 * <p>
 * Note that the validation can be performed against a profile, a validation
 * context, or both. One of these three options must be specified.
 * <p>
 * 
 * @author Robert Snelick (NIST)
 */
@WebService
public interface MessageValidationV2Interface {

	/**
	 * Perform the message validation and return a report.
	 * <p>
	 * Message validation requires an HL7 v2 message encoded in ER7 or XML. The
	 * validation may also use a specification (i.e., an HL7 V2 conformance
	 * profile), a list of one or more HL7 table files, and a validation context.
	 * The validation context specifies specific content tests related to the message.
	 * The validation can be performed against a profile, a validation context, or both. 
	 * One of these three options must be specified.
	 * <p>
	 * 
	 * @param message
	 *            the HL7 V2 message that is being validated. The encoding of
	 *            the message can be XML or ER7. The validation service
	 *            determines the encoding type by examining the content of the
	 *            message parameter.
	 *            <p>
	 * @param profileOID
	 *            the profileOID is an OID that references the profile used in validation. The
	 *            profileOID must reference a valid HL7 V2 conformance profile that is
	 *            loaded on the server. The service requires that the profile is
	 *            pre-loaded in the repository and validated.  Profiles must conform
	 *            to the appropriate <b>HL7 V2 Conformance Profile</b> schema.
	 *            <p>
	 *            If a profile is not specified in this parameter, then the validation 
	 *            is performed against the validation context only. 
	 *            <p>
	 * @param tableOIDList
	 *            the tableOIDList is a colon (":") separated list of OIDs which reference
	 *            valid HL7 V2 table(s) loaded on the server. The service requires
	 *            all tables are pre-loaded in the repository and validated before usage.
	 *            Tables must conform to the NIST <b>Table Library</b> schema.
	 *            <p>
	 * @param xmlValidationContext
	 *            describes specific data content tests associated with a
	 *            specific HL7 V2 message. The validation context describes the
	 *            location and values to be tested. The validation context
	 *            information is used in scenario base testing. The validation
	 *            context must adhere to the NIST <b>Validation Context</b> schema.
	 *            <p>
	 *            The xmlValidationContext can also set general validation
	 *            configuration parameters that specify how certain failures are
	 *            interpreted. For example, length failures may be detected by
	 *            the validation service but ignored.
	 *            <p>
	 * @return xmlResults an XML document for reporting the results of the HL7
	 *         V2 message validation. For details of the report see the
	 *         NIST <b>xmlResults</b> schema.
	 *         If the validation process couldn't be performed this is indicated along with the 
	 *         reason why in the results report.
	 *         <p>
	 *        
	 * 
	 */
	String validate(String message, String profileOID, String tableOIDList,
			String xmlValidationContext);

	/**
	 * Perform the message validation and return a report.
	 * <p>
	 * Message validation requires an HL7 v2 message encoded in ER7 or XML. The
	 * validation may also use a specification (i.e., an HL7 V2 conformance
	 * profile), a list of one or more HL7 table files, a validation context and a list of DQA 
	 * errors to capture.
	 * The validation context specifies specific content tests related to the message.
	 * The validation can be performed against a profile, a validation context, or both. 
	 * One of these three options must be specified.
	 * <p>
	 * 
	 * @param message
	 *            the HL7 V2 message that is being validated. The encoding of
	 *            the message can be XML or ER7. The validation service
	 *            determines the encoding type by examining the content of the
	 *            message parameter.
	 *            <p>
	 * @param profileOID
	 *            the profileOID is an OID that references the profile used in validation. The
	 *            profileOID must reference a valid HL7 V2 conformance profile that is
	 *            loaded on the server. The service requires that the profile is
	 *            pre-loaded in the repository and validated.  Profiles must conform
	 *            to the appropriate <b>HL7 V2 Conformance Profile</b> schema.
	 *            <p>
	 *            If a profile is not specified in this parameter, then the validation 
	 *            is performed against the validation context only. 
	 *            <p>
	 * @param tableOIDList
	 *            the tableOIDList is a colon (":") separated list of OIDs which reference
	 *            valid HL7 V2 table(s) loaded on the server. The service requires
	 *            all tables are pre-loaded in the repository and validated before usage.
	 *            Tables must conform to the NIST <b>Table Library</b> schema.
	 *            <p>
	 * @param xmlValidationContext
	 *            describes specific data content tests associated with a
	 *            specific HL7 V2 message. The validation context describes the
	 *            location and values to be tested. The validation context
	 *            information is used in scenario base testing. The validation
	 *            context must adhere to the NIST <b>Validation Context</b> schema.
	 *            <p>
	 *            The xmlValidationContext can also set general validation
	 *            configuration parameters that specify how certain failures are
	 *            interpreted. For example, length failures may be detected by
	 *            the validation service but ignored.
	 *            <p>
	 * @param DQAOptions
	 *            the DQAOptions is a colon (":") separated list of DQA errors identifiers.
	 * @return xmlResults an XML document for reporting the results of the HL7
	 *         V2 message validation as well as DQA Detections. For details of the report see the
	 *         NIST <b>xmlResults</b> schema.
	 *         If the validation process couldn't be performed this is indicated along with the 
	 *         reason why in the results report.
	 *         <p>
	 *        
	 * 
	 */
	String validateDQA(String message, String profileOID, String tableOIDList,
			String xmlValidationContext, String DQAOptions) throws Exception ;
	
	String validateWithResources(
			String message,
			String profile,
			String constraints,
			String valueSetLibrary,
			String message_id) throws Exception;
	/**
	 * The getServiceStatus() method returns a list of the profiles and tables
	 * currently supported by the validation service. Note that if the service
	 * is not operational, the call will not return. Therefore the
	 * getServiceStatus() invocation can be used to query the service for a list
	 * of supported profiles and tables or to ping the operational status of the
	 * service. The supported profiles are return in the xmlResourceList
	 * document. Profiles and tables that are loaded with the loadResource()
	 * method are included in this list. String xmlResourceList =
	 * getServiceStatus();
	 * 
	 * @return xmlResourceList- an XML string is returned 
	 *         (refer to the NIST <b>xmlResourceList</b> schema)
	 *         that provides descriptive information about the available
	 *         validation resources. For HL7 version 2 resources include
	 *         profiles and tables. The xmlResourceList will provide this
	 *         information for all the available validation resources provided
	 *         by the service. The following attributes describe a particular
	 *         resource and is provided if the information is available.
	 *         <p>
	 *         resourceID: This identifier references the validation resource.
	 *         For HL7 V2 the resourceID is a OID. The resourceID can be used in
	 *         the validate() method to indicate the resource. Both profiles and 
	 *         tables are referenced by an OID parameter
	 *         <p>
	 *         name: A descriptive name of the profile/table.
	 *         <p>
	 *         version: The version of the profile/table.
	 *         <p>
	 *         organization: The organization that created, provided, or owns
	 *         the profile/table.
	 *         <p>
	 *         date: The date the profile/table was created.
	 *         <p>
	 *         description: A detailed description of the profile/table.
	 *         <p>
	 *         HL7version: The HL7 version of the profile/table (e.g, 2.5.1).
	 *         <p>
	 */
	String getServiceStatus();

	/**
	 * The loadResource() method is used to load an HL7 V2 conformance profile
	 * or table (resources) that can be used in a subsequence validate()
	 * invocation. The length of time the resource is available for use in
	 * validation is the duration of the web service session. Note that
	 * resources that are loaded with this call are reported in the
	 * getServiceStatus() method. String xmlLoadResource = loadResource (String
	 * resource, String OID, String type);
	 * 
	 * @return xmlLoadResource � an XML document
	 *         (refer to the NIST <b>xmlLoadResource</b> schema) 
	 *         indicating the results from the loadResource() invocation. The document contains the following
	 *         attributes.
	 *         <p>
	 *         status: true if the resource was loaded and validated
	 *         successfully; false otherwise.
	 *         <p>
	 *         OID: identifier that references the resource. If the OID is not
	 *         specified, the validation service provides an OID. On the NIST
	 *         server the OID root for a profile is
	 *         2.16.840.1.113883.3.72.2.2.XXXXX. For a table resource the OID is
	 *         2.16.840.1.113883.3.72.4.2.XXXXX. The �XXXXX� value is assigned
	 *         by the server. . If the OID has the same value as an OID that is
	 *         already registered on the service, it is an error. The original
	 *         resource referenced by the OID is not altered. The user must
	 *         select another OID to load their resource or need to check to see
	 *         if the resource they want is already available.
	 *         <p>
	 *         errorDescription: This field provides a detailed description
	 *         indicating the reason the resource could not be loaded. For a
	 *         profile, this will include the results from a profile validation.
	 * @param resource
	 *            � is the resource to load. A resource is either a profile or a
	 *            table. The profile is a valid HL7 conformance profile (i.e.,
	 *            it adheres to the HL7 conformance profile schema and
	 *            additional requirements place on it by the NIST service). A
	 *            table is validated by the hl7TableLibrary schema.
	 * @param oid
	 *            an identifier that references the resource.
	 * @param type
	 *            � is the type of the resource. The type is either PROFILE or
	 *            TABLE.
	 */
	String loadResource(String resource, String oid, String type);
}
