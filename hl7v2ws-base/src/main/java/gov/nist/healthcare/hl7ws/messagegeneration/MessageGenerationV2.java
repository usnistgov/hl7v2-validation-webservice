/*
 * NIST HL7 WS
 * MessageGeneration.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.messagegeneration;

import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.generation.message.HL7V2MessageGenerationContextDefinitionDocument;
import gov.nist.healthcare.hl7Ws.generationresult.GenerationErrors;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResult;
import gov.nist.healthcare.hl7Ws.generationresult.HL7V2MessageGenerationResultDocument;
import gov.nist.healthcare.hl7Ws.resource.XmlLoadResourceDocument;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListDocument;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType.Resource;
import gov.nist.healthcare.hl7ws.registry.Registry;
import gov.nist.healthcare.hl7ws.registry.TempRegistry;
import gov.nist.healthcare.hl7ws.utils.MessageGenerationUtils;
import gov.nist.healthcare.hl7ws.utils.XmlUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.commons.codec.binary.Base64;

import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.cxf.jaxrs.ext.Description;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Harold AFFO (NIST)
 */

@Service("messageGenerationV2")
@Path("/MessageGenerationV2/")
@Description(value = "Message Generation V2 Service", title = "MessageGenerationV2")
@WebService(serviceName = "MessageGenerationV2", name = "MessageGenerationV2Service", targetNamespace = "http://gov.nist.healthcare.hl7ws/generation/message")
public class MessageGenerationV2 implements MessageGenerationV2Interface {
	protected final Logger log = Logger.getLogger("gov.nist.healthcare.hl7ws");
	@Autowired
	private Registry registry;
	@Autowired
	private TempRegistry tempRegistry;
	@Autowired
	@Qualifier("messageGenerationUtils")
	private MessageGenerationUtils messageGenerationUtils;

	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/generate")
	@Produces("application/xml")
	@WebMethod(operationName = "generate")
	@WebResult(name = "messageGenerated")
	@Consumes("application/x-www-form-urlencoded")
	public String generate(
			@FormParam("oid") @WebParam(name = "oid") String oid,
			@FormParam("xmlConfig") @WebParam(name = "xmlConfig") String xmlConfig,
			@FormParam("xmlGenerationContext") @WebParam(name = "xmlGenerationContext") String xmlGenerationContext) {
		String result = null;
		try {
			Profile profile = messageGenerationUtils.getProfile(oid);
			String profileVersion = profile.getHl7VersionAsString();
			HL7V2MessageGenerationContextDefinitionDocument xmlGenerationCtxt = messageGenerationUtils
					.getXmlGenerationContext(xmlGenerationContext);
			TableLibraryDocument table = messageGenerationUtils.getTable(
					xmlConfig, profileVersion);
			HL7V2MessageGenerationResultDocument doc = messageGenerationUtils.generate(profile, table, xmlGenerationCtxt);
			result = doc.toString();
			//result = messageGenerationUtils.generate(profile, table, xmlGenerationCtxt);
		} catch (RuntimeException e) {
			log.fatal(e);

			HL7V2MessageGenerationResultDocument dc = HL7V2MessageGenerationResultDocument.Factory
					.newInstance();
			HL7V2MessageGenerationResult res = dc
					.addNewHL7V2MessageGenerationResult();
			GenerationErrors errors = res.addNewGenerationErrors();
			errors.addGenerationError(e.getMessage());
			result = dc.toString();
		} catch (Exception e) {
			log.fatal(e);
			HL7V2MessageGenerationResultDocument dc = HL7V2MessageGenerationResultDocument.Factory
					.newInstance();
			HL7V2MessageGenerationResult res = dc
					.addNewHL7V2MessageGenerationResult();
			GenerationErrors errors = res.addNewGenerationErrors();
			errors.addGenerationError(e.getMessage());
			result = dc.toString();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/generateFromTemplate")
	@Produces("application/xml")
	@WebMethod(operationName = "generateFromTemplate")
	@WebResult(name = "messageGenerated")
	@Consumes("application/x-www-form-urlencoded")
	public String generateFromTemplate(
			@FormParam("template") @WebParam(name = "template") String template,
			@FormParam("oid") @WebParam(name = "oid") String oid,
			@FormParam("xmlConfig") @WebParam(name = "xmlConfig") String xmlConfig,
			@FormParam("xmlGenerationContext") @WebParam(name = "xmlGenerationContext") String xmlGenerationContext) {
		String result = null;
		try {
			log.info("MessageGenerationInterface - User invokes generateFromTemplate())");
			HL7V2MessageGenerationResultDocument doc = messageGenerationUtils.generateFromTemplate(template, oid, xmlConfig,xmlGenerationContext);
			result = doc.toString();
			//result = messageGenerationUtils.generateFromTemplate(template, oid, xmlConfig,xmlGenerationContext);
			System.out.println("generated " + result);
		} catch (RuntimeException e) {
			log.fatal(e);
			HL7V2MessageGenerationResultDocument dc = HL7V2MessageGenerationResultDocument.Factory
					.newInstance();
			HL7V2MessageGenerationResult res = dc
					.addNewHL7V2MessageGenerationResult();
			GenerationErrors errors = res.addNewGenerationErrors();
			errors.addGenerationError(e.getMessage());
			result = dc.toString();
		} catch (Exception e) {
			log.fatal(e);
			HL7V2MessageGenerationResultDocument dc = HL7V2MessageGenerationResultDocument.Factory
					.newInstance();
			HL7V2MessageGenerationResult res = dc
					.addNewHL7V2MessageGenerationResult();
			GenerationErrors errors = res.addNewGenerationErrors();
			errors.addGenerationError(e.getMessage());
			result = dc.toString();
		}
		//try {
		//	System.out.println("encode = " + new String(Base64.encodeBase64(result.getBytes())));
		//} catch (Exception e) {
		//}
		 
		//return new String(Base64Utility.encode(result.getBytes()));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@GET
	@Path("/getServiceStatus")
	@Produces("application/xml")
	@WebMethod(operationName = "getServiceStatus")
	@WebResult(name = "ServiceStatus")
	public String getServiceStatus() {
		XmlResourceListDocument xmlResourceListDoc = XmlResourceListDocument.Factory
				.newInstance();
		try {
			XmlResourceListType xmlResourceListType = XmlResourceListDocument.Factory
					.newInstance().addNewXmlResourceList();
			//registry.addResourceListTo(xmlResourceListType);
			tempRegistry.addResourceListTo(xmlResourceListType);
			xmlResourceListDoc.setXmlResourceList(xmlResourceListType);
		} catch (RemoteException e) {
			XmlResourceListType resourceListType = XmlResourceListDocument.Factory
					.newInstance().addNewXmlResourceList();
			Resource r = resourceListType.addNewResource();
			r.setDescription("Resource failed to load: " + e.getMessage());
			xmlResourceListDoc.setXmlResourceList(resourceListType);
		} catch (Exception e) {
			XmlResourceListType resourceListType = XmlResourceListDocument.Factory
					.newInstance().addNewXmlResourceList();
			Resource r = resourceListType.addNewResource();
			r.setDescription("Resource failed to load: " + e.getMessage());
			xmlResourceListDoc.setXmlResourceList(resourceListType);
		}
		// debug
		XmlUtils.checkXmlObjectForDebug(xmlResourceListDoc, "xmlResourceList");
		return xmlResourceListDoc.toString();
	}

	/**
	 * {@inheritDoc}
	 */

	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/loadResource")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	@WebMethod(operationName = "loadResource")
	@WebResult(name = "XmlResourceLoaded")
	public String loadResource(
			@FormParam("resource") @WebParam(name = "resource") String resource,
			@FormParam("oid") @WebParam(name = "oid") String oid,
			@FormParam("type") @WebParam(name = "type") String type) {
		String xmlResource = null;
		XmlLoadResourceDocument xmlLoadResourceDoc = tempRegistry.loadResource(
				resource, oid, type, registry);
		// debug
		XmlUtils.checkXmlObjectForDebug(xmlLoadResourceDoc, "xmlLoadResource");
		xmlResource = xmlLoadResourceDoc.toString();
		return xmlResource;
	}

}
