/*
 * NIST HL7 WS
 * MessageValidation.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.messagevalidation;



import gov.nist.healthcare.core.message.v2.HL7V2Message;
import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.core.validation.message.v2.MessageValidationContextV2;
import gov.nist.healthcare.core.validation.message.v2.MessageValidationResultV2;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.hl7Ws.resource.XmlLoadResourceDocument;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListDocument;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType.Resource;
import gov.nist.healthcare.hl7ws.model.CBResource;
import gov.nist.healthcare.hl7ws.model.ResourceRegistry;
import gov.nist.healthcare.hl7ws.model.CFResource;
import gov.nist.healthcare.hl7ws.registry.Registry;
import gov.nist.healthcare.hl7ws.registry.TempRegistry;
import gov.nist.healthcare.hl7ws.utils.MessageValidationV2Utils;
import gov.nist.healthcare.hl7ws.utils.XmlUtils;
import gov.nist.healthcare.mu.core.stat.StatTimer;
import gov.nist.healthcare.mu.core.stat.StatTimer.TestContext;
import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.proxy.ValidationProxy;
import gov.nist.healthcare.validation.message.hl7.v2.report.HL7V2MessageValidationReportDocument;
import hl7.v2.profile.XMLDeserializer;
import hl7.v2.validation.content.ConformanceContext;
import hl7.v2.validation.content.DefaultConformanceContext;
import hl7.v2.validation.vs.ValueSetLibrary;
import hl7.v2.validation.vs.ValueSetLibraryImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
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

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.ext.Description;
import org.apache.log4j.Logger;
import org.openimmunizationsoftware.dqa.nist.CompactReportModel;
import org.openimmunizationsoftware.dqa.nist.ProcessMessageHL7;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




/**
 * This class represents the SOAP and REST services for the Validation of V2
 * Message
 * 
 * @author Harold AFFO (NIST) The validation service delegator
 */

@Description(value = "Message Validation V2 Service", title = "MessageValidationV2")
@Service("messageValidationV2")
@Path("/MessageValidationV2/")
@WebService(serviceName = "MessageValidationV2", name = "MessageValidationV2Service", targetNamespace = "http://gov.nist.healthcare.hl7ws/validation/message")
public class MessageValidationV2 implements MessageValidationV2Interface {
	
	
	@Autowired
	private MessageValidationV2Utils messageValidationV2Utils;
	
	@Autowired
	private Registry registry;
	
	@Autowired
	private TempRegistry tempRegistry;
	
	private static ResourceRegistry newValidationResources;
	
	protected final Logger log = Logger.getLogger("gov.nist.healthcare.hl7ws");
	
	static int counter=0;
	
	@PostConstruct
	public void InitMe() {	
	//MessageValidationV2() {		
       //tempRegistry = new TempRegistry();
	   String profile;
	   String table;
	   String table1;
	   String table2;
	   String table3;
	   
		if (++counter < 2) {
			
			newValidationResources = Utils.InitNewValidationResources();
			
			//URI dataFileURI=MessageValidationV2.class.getResource("/files/").getFile();
			String dataFilePath=MessageValidationV2.class.getResource("/files/").getFile();	

			System.out.println("--------------Loading default Profiles: IZ -----------------\n");
			String defaultProfilePath = dataFilePath + "profiles_preloaded/IZ/immunization-profile.xml";
			//String defaultProfilePath = dataFilePath + "profiles_preloaded/IZ/VXU_Profile_IGAMT_V1.5.xml";
			String defaultTable3Path =  dataFilePath + "tables_preloaded/IZ/Reduced_HL7tablesV2.5.1.xml";
			String defaultTable1Path =  dataFilePath + "tables_preloaded/IZ/IG_PHINVADS_IZVocab.xml";
			String defaultTable2Path =  dataFilePath + "tables_preloaded/IZ/NoCheckTables_IZVocab.xml";
			try {
				profile = IOUtils.toString(new FileReader(new File(defaultProfilePath)));
			    System.out.println("GOT HERE -1 \n");
			    //System.out.println("profile = " + profile);
				String res=loadResource(profile, "2.16.840.1.113883.3.72.2.2.99001","PROFILE");
				System.out.println("loadResource: " + res);
				System.out.println("GOT HERE -2 \n");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			
			try {
				table1 = IOUtils.toString(new FileReader(new File(defaultTable1Path)));
				System.out.println("GOT HERE -3 \n");
				loadResource(table1, "2.16.840.1.113883.3.72.4.2.99001","TABLE");
				System.out.println("GOT HERE -4 \n");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			
			try {
				table2 = IOUtils.toString(new FileReader(new File(defaultTable2Path)));
				System.out.println("GOT HERE -5 \n");
				loadResource(table2, "2.16.840.1.113883.3.72.4.2.99002","TABLE");
				System.out.println("GOT HERE -6 \n");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			
			try {
				table3 = IOUtils.toString(new FileReader(new File(defaultTable3Path)));
				System.out.println("GOT HERE -7 \n");
				loadResource(table3, "2.16.840.1.113883.3.72.4.2.99003","TABLE");
				System.out.println("GOT HERE -8 \n");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			
			
			System.out.println("--------------Loading default Profiles: LRI -----------------\n");
			String defaultProfilePaths_LRI[] =   { dataFilePath + "profiles_preloaded/LRI/NIST_LRI_ORU_R01_GU_RU_Profile_V1.0_Nov06-2012.xml",
					 								dataFilePath + "profiles_preloaded/LRI/NIST_LRI_ORU_R01_GU_RN_Profile_V1.0_Nov06-2012.xml",
					 								dataFilePath + "profiles_preloaded/LRI/NIST_LRI_ORU_R01_NG_RU_Profile_V1.0_Nov06-2012.xml",
					 								dataFilePath + "profiles_preloaded/LRI/NIST_LRI_ORU_R01_NG_RN_Profile_V1.0_Nov06-2012.xml"};
			int index=9;
			int poid_next=17;
			for (String s : defaultProfilePaths_LRI) {
				try {
					profile = IOUtils.toString(new FileReader(new File(s)));
					System.out.println("GOT HERE - " + index++ + "\n");
					//System.out.println("profile = " + profile);
					String res=loadResource(profile, "2.16.840.1.113883.9." + poid_next++,"PROFILE");
					System.out.println("loadResource: " + res);
					System.out.println("GOT HERE - " + index++ + "\n");
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
			
//			String defaultTablePaths_LRI[]={ dataFilePath + "tables_preloaded/LRI/LRIVOCAB.xml",
//					 dataFilePath + "tables_preloaded/LRI/LRIVOCAB_NIST_COMPLIANT.xml",
//					 dataFilePath + "tables_preloaded/LRI/LOINC_CATEGORIZED.xml",
//					 dataFilePath + "tables_preloaded/LRI/LOINC_ALL_CODES.xml",
//					 dataFilePath + "tables_preloaded/LRI/UCUM.xml",
//					 dataFilePath + "tables_preloaded/LRI/UCUM_ALL_CODES.xml",
//					 dataFilePath + "tables_preloaded/LRI/SNOMED_CODES.xml",
//					 dataFilePath + "tables_preloaded/LRI/SNOMED_SCT_ALL_CODES.xml",
//					 dataFilePath + "tables_preloaded/LRI/LRI_VALUE-SETS.xml"};
					 
			String defaultTablePaths_LRI[]={ dataFilePath + "tables_preloaded/LRI/LRI_VALUE-SETS.xml",
					 dataFilePath + "tables_preloaded/LRI/LRIVOCAB_NIST_COMPLIANT.xml",
					 dataFilePath + "tables_preloaded/LRI/LOINC_ALL_CODES.xml",
					 dataFilePath + "tables_preloaded/LRI/SNOMED_SCT_ALL_CODES.xml",
					 dataFilePath + "tables_preloaded/LRI/UCUM_ALL_CODES.xml"};
			
			int toid_next=99004;
			for (String s : defaultTablePaths_LRI) {
				try {
					table = IOUtils.toString(new FileReader(new File(s)));
					System.out.println("GOT HERE - " + index++ + "\n");
					//System.out.println("table = " + table);
					String res=loadResource(table, "2.16.840.1.113883.3.72.4.2." + toid_next++,"TABLE");
					System.out.println("loadResource: " + res);
					System.out.println("GOT HERE - " + index++ + "\n");
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
			
			
			System.out.println("--------------Loading default Profiles: SS -----------------\n");
			String defaultProfilePaths_SS[] =   { dataFilePath + "profiles_preloaded/SS/Syndromic_ADT_A01.xml",
					 							  dataFilePath + "profiles_preloaded/SS/Syndromic_ADT_A03.xml",
					 							  dataFilePath + "profiles_preloaded/SS/Syndromic_ADT_A04.xml",
					 							  dataFilePath + "profiles_preloaded/SS/Syndromic_ADT_A08.xml"};
			poid_next=99002;
			for (String s : defaultProfilePaths_SS) {
				try {
					profile = IOUtils.toString(new FileReader(new File(s)));
					System.out.println("GOT HERE - " + index++ + "\n");
					//System.out.println("profile = " + profile);
					String res=loadResource(profile, "2.16.840.1.113883.3.72.2.2." + poid_next++,"PROFILE");
					System.out.println("loadResource: " + res);
					System.out.println("GOT HERE - " + index++ + "\n");
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
			
	
//			String defaultTablePaths_SS[]={ dataFilePath + "tables_preloaded/SS/RestrictedTables_SYNDROMICVocab.xml",
//					 dataFilePath + "tables_preloaded/SS/Reduced_HL7tablesV2.5.1.xml",
//					 dataFilePath + "tables_preloaded/SS/NoCheckTables_SYNDROMICVocab.xml",
//					 dataFilePath + "tables_preloaded/SS/MergedTables_SYNDROMICVocab.xml",
//					 dataFilePath + "tables_preloaded/SS/MergedAndRestrictedTables_SYNDROMICVocab.xml",
//					 dataFilePath + "tables_preloaded/SS/IG_PHINVADS_SYNDROMICVocab.xml"};
					 
			
			String defaultTablePaths_SS[]={ dataFilePath + "tables_preloaded/SS/IG_PHINVADS_SYNDROMICVocab.xml",
					 dataFilePath + "tables_preloaded/SS/RestrictedTables_SYNDROMICVocab.xml",
					 dataFilePath + "tables_preloaded/SS/MergedTables_SYNDROMICVocab.xml",
					 dataFilePath + "tables_preloaded/SS/MergedAndRestrictedTables_SYNDROMICVocab.xml",
					 dataFilePath + "tables_preloaded/SS/NoCheckTables_SYNDROMICVocab.xml",
					 dataFilePath + "tables_preloaded/SS/Reduced_HL7tablesV2.5.1.xml"};
			
			for (String s : defaultTablePaths_SS) {
				try {
					table = IOUtils.toString(new FileReader(new File(s)));
					System.out.println("GOT HERE - " + index++ + "\n");
					//System.out.println("table = " + table);
					String res=loadResource(table, "2.16.840.1.113883.3.72.4.2." + toid_next++,"TABLE");
					System.out.println("loadResource: " + res);
					System.out.println("GOT HERE - " + index++ + "\n");
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
			
			
			
			System.out.println("--------------Loading default Profiles: ELR -----------------\n");
			defaultProfilePath= dataFilePath + "profiles_preloaded/ELR/elr-profile.xml";
			try {
				profile = IOUtils.toString(new FileReader(new File(defaultProfilePath)));
				System.out.println("GOT HERE - " + index++ + "\n");
				//System.out.println("profile = " + profile);
				String res=loadResource(profile, "2.16.840.1.113883.9.11","PROFILE");
				System.out.println("loadResource: " + res);
				System.out.println("GOT HERE - " + index++ + "\n");
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			String defaultTablePaths_ELR[]={ 
//					 dataFilePath + "tables_preloaded/ELR/IG_PHINVADS_ELRVocab.xml",
//					 dataFilePath + "tables_preloaded/ELR/MergedTables_ELRVocab.xml",
//					 dataFilePath + "tables_preloaded/ELR/RestrictedTables_ELRVocab.xml",
//					 dataFilePath + "tables_preloaded/ELR/NoCheckTables_ELRVocab.xml",
//					 dataFilePath + "tables_preloaded/ELR/Reduced_HL7tablesV2.5.1.xml"
					 
					 dataFilePath + "tables_preloaded/ELR/CDC_VocabularyAnalysis.xml",
					 dataFilePath + "tables_preloaded/ELR/EXTERNAL_VocabularyAnalysis.xml",
					 dataFilePath + "tables_preloaded/ELR/ICD_VocabularyAnalysis.xml",
					 dataFilePath + "tables_preloaded/ELR/HL7_VocabularyAnalysis.xml",
					 dataFilePath + "tables_preloaded/ELR/ELR_DynamicDatatypeMapping.xml"
					 };

			
			for (String s : defaultTablePaths_ELR) {
				try {
					table = IOUtils.toString(new FileReader(new File(s)));
					System.out.println("GOT HERE - " + index++ + "\n");
					//System.out.println("table = " + table);
					String res=loadResource(table, "2.16.840.1.113883.3.72.4.2." + toid_next++,"TABLE");
					System.out.println("loadResource: " + res);
					System.out.println("GOT HERE - " + index++ + "\n");
				} catch (Exception e) {// Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
		} //if
	} //InitMe
	
	
	private String oldValidation(String message, String oid, String tables, String xmlContext, boolean dqa, ArrayList<String> DQAOptions) throws Exception{
		
		HL7V2MessageValidationReportDocument reportDocument = null;
		HL7V2Message hl7message = null;
		Profile profile = null;
		MessageValidationContextV2 messageValidContext = null;		
		ArrayList<TableLibraryDocument> resources = new ArrayList<TableLibraryDocument>();
		StatTimer timer = new StatTimer(TestContext.TEST_CONTEXT_FREE," ");

		// retrieve the profile
		if (!("".equals(oid) || oid == null)) {
			profile = getProfile(oid);
			messageValidationV2Utils.logFile(oid, "profileOID");
			//version = profile.getHl7VersionAsString();
		}
		
		// retrieve the message validation context
		if (!("".equals(xmlContext) || xmlContext == null)) {
			messageValidContext = messageValidationV2Utils
					.getMVC(xmlContext);
		}
		
		if (!("".equals(tables) || tables == null)) {
			System.out.println("xmlConfig2 = " + tables);
			String[] resourceList = tables.split(":");
		    for(String s : resourceList){
		       System.out.println("adding table with oid = "+ s + "\n");
			   resources.add(tempRegistry.getTable(s));
		     }
		}
		
		hl7message = messageValidationV2Utils.getMessage(message);
		
		timer.start();
		
		// validate
		MessageValidationResultV2 mvResult = messageValidationV2Utils
				.validate(profile, hl7message, messageValidContext,
						resources, true);
		
		reportDocument = mvResult.getReport();
		XmlUtils.checkXmlObjectForDebug(reportDocument,
				"HL7V2ValidationReport");
		
		timer.endSuccess();
		
		if(dqa){
			EnhancedReport er = EnhancedReport.from(reportDocument.toString(), "xml");
			CompactReportModel cc= ProcessMessageHL7.getInstance().process(message, "1223");
			
			if(DQAOptions.isEmpty()){
				ArrayList<Section> sects = cc.toSections();
				if(sects.size() != 0)
					er.put(cc.toSections());
			}
			else {
				ArrayList<Section> sects = cc.toSections(DQAOptions);
				if(sects.size() != 0)
					er.put(cc.toSections(DQAOptions));
			}
			
        	return er.to("xml").toString();
		}
		else
			return reportDocument.toString();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/validate")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	@WebMethod(operationName = "validate")
	@WebResult(name = "ValidationReport")
	public String validate(
			@FormParam("message") @WebParam(name = "message") String message,
			@FormParam("oid") @WebParam(name = "oid") String oid,
			@FormParam("xmlConfig") @WebParam(name = "xmlConfig") String xmlConfig,
			@FormParam("xmlValidationContext") @WebParam(name = "xmlValidationContext") String xmlValidationContext) {
		HL7V2MessageValidationReportDocument reportDocument = null;

		String testSel = null;
		ValidationProxy vp = new ValidationProxy("NIST HL7V2 Message Validation","NIST");

		ArrayList<String> newValidatorOIDs = new ArrayList<String>();		
		StatTimer timer = new StatTimer(TestContext.TEST_CONTEXT_FREE," ");
		String[] oidlist={"2.16.840.1.113883.3.72.2.3.99001","2.16.840.1.113883.3.72.2.3.99002","2.16.840.1.113883.3.72.2.3.99003","2.16.840.1.113883.3.72.2.3.99004","2.16.840.1.113883.3.72.2.3.99005","2.16.840.1.113883.3.72.2.3.99006",
				"2.16.840.1.113883.3.72.2.3.99007","2.16.840.1.113883.3.72.2.3.99008","2.16.840.1.113883.3.72.2.3.99009",
				"2.16.840.1.113883.3.72.2.4.99001",
				"2.16.840.1.113883.3.72.2.4.99002",
				"2.16.840.1.113883.3.72.2.4.99003",
				"2.16.840.1.113883.3.72.2.4.99004",
				"2.16.840.1.113883.3.72.2.4.99005",
				"2.16.840.1.113883.3.72.2.4.99006",
				"2.16.840.1.113883.3.72.2.4.99007",
				"2.16.840.1.113883.3.72.2.4.99008",
				"2.16.840.1.113883.3.72.2.4.99009",
				"2.16.840.1.113883.3.72.2.4.99010",
				"2.16.840.1.113883.3.72.2.4.99011",
				"2.16.840.1.113883.3.72.2.4.99012",
				"2.16.840.1.113883.3.72.2.4.99013",
				"2.16.840.1.113883.3.72.2.4.99014",
				"2.16.840.1.113883.3.72.2.5.99001","2.16.840.1.113883.3.72.2.5.99002","2.16.840.1.113883.3.72.2.5.99003","2.16.840.1.113883.3.72.2.5.99004","2.16.840.1.113883.3.72.2.5.99005","2.16.840.1.113883.3.72.2.5.99006",
				"2.16.840.1.114222.4.10.3.1",
				"2.16.840.1.114222.4.10.3.2",
				"2.16.840.1.114222.4.10.3.3",
				"2.16.840.1.114222.4.10.3.4",
				"2.16.840.1.114222.4.10.3.5",
				"2.16.840.1.114222.4.10.3.6",
				"2.16.840.1.114222.4.10.3.7",
				"2.16.840.1.114222.4.10.3.8",
				"2.16.840.1.114222.4.10.3.9",
				"2.16.840.1.114222.4.10.3.10",
				"2.16.840.1.114222.4.10.3.11",
				"2.16.840.1.114222.4.10.3.12"
				};


		for (String s:oidlist) {
			newValidatorOIDs.add(s);
		}
	
		try {
			checkParameters(message, xmlValidationContext, oid);
			System.out.println("Receieved Message : " + message);
			System.out.println("Receieved OID : " + oid);
			
			if (newValidatorOIDs.contains(oid)) {
				System.out.println("trying to validate for OID = " + oid);
				CFResource resource = newValidationResources.getResource(oid);
				System.out.println("******resource.getProfile() = " + resource.getProfile());
				System.out.println("******resource.getContraints() = " + resource.getConstraints());
				System.out.println("******resource.getValueSets() = " + resource.getValueSets());
				System.out.println("******resource.getOID() = " + resource.getOID());
				return resource.validate(vp, message, false, null);
            }
			else if (!(testSel=NVContextBased(oid)).equals("")) {
				CBResource resource = Utils.getCBResource(testSel);
				return resource.validate(vp, message, false, null);         
			}
			else {
				return this.oldValidation(message, oid, xmlConfig, xmlValidationContext, false, null);
			}

		} catch (RuntimeException e) {
			reportDocument = messageValidationV2Utils.buildExceptionReport(e
					.getMessage());
            timer.endError();
		} catch (Exception e) {
			reportDocument = messageValidationV2Utils.buildExceptionReport(e
					.getMessage());
            timer.endError();
			// throw new WebApplicationException(e);
		}
		
		return reportDocument.toString();

	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/validateDQA")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	@WebMethod(operationName = "validateDQA")
	@WebResult(name = "ValidationReport")
	public String validateDQA(
			@FormParam("message") @WebParam(name = "message") String message,
			@FormParam("oid") @WebParam(name = "oid") String oid,
			@FormParam("xmlConfig") @WebParam(name = "xmlConfig") String xmlConfig,
			@FormParam("xmlValidationContext") @WebParam(name = "xmlValidationContext") String xmlValidationContext,
			@FormParam("DQAOption") @WebParam(name = "DQAOption") String DQAOption) throws Exception {
		
		ValidationProxy vp = new ValidationProxy("NIST HL7V2 Message Validation","NIST");
		ArrayList<String> filter = new ArrayList<String>();
		
		if (!("".equals(DQAOption) || DQAOption == null)) {
			String[] idList = DQAOption.split(":");
		    for(String s : idList){
		    	if(!s.equals(""))
		    		filter.add(s);
		     }
		}
		
		if(oid.equals("2.16.840.1.113883.3.72.2.3.99001")){
			CFResource resource = newValidationResources.getResource(oid);
			return resource.validate(vp, message, true, filter);
		}
		else if(oid.equals("2.16.840.1.113883.3.72.2.2.99001")){
			return this.oldValidation(message, oid, xmlConfig, xmlValidationContext, true, filter);
		}
		else{
			throw new Exception();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@POST
	@Path("/validateWithResources")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	@WebMethod(operationName = "validateWithResources")
	@WebResult(name = "ValidationReport")
	public String validateWithResources(
			@FormParam("message") @WebParam(name = "message") String message,
			@FormParam("profile") @WebParam(name = "profile") String profile,
			@FormParam("constraints") @WebParam(name = "constraints") String constraints,
			@FormParam("valueSetLibrary") @WebParam(name = "valueSetLibrary") String valueSetLibrary,
			@FormParam("messageId") @WebParam(name = "messageId") String messageId) throws Exception {
		System.out.println("IN WS VWR");
		
		InputStream cStream = new ByteArrayInputStream(constraints.getBytes(StandardCharsets.UTF_8));
		ConformanceContext cO = DefaultConformanceContext.apply(Arrays.asList(cStream)).get();
		
		InputStream vStream = new ByteArrayInputStream(valueSetLibrary.getBytes(StandardCharsets.UTF_8));
		ValueSetLibrary valueSetLibraryO = ValueSetLibraryImpl.apply(vStream).get();
		
		ValidationProxy vp = new ValidationProxy("NIST HL7V2 Message Validation","NIST");
		EnhancedReport er = vp.validate(message, profile, cO, valueSetLibraryO, messageId,Context.Free);  
		
		System.out.println("V");
		
		return er.to("json").toString();
	}


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
			//MDI: registry.addResourceListTo(xmlResourceListType);
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
	 * Get the profile.
	 * 
	 * @param oid
	 *            the profile's OID
	 * @return the profile
	 * @throws Exception
	 *             if the profile cannot be retrieved
	 */
	private Profile getProfile(final String oid) throws Exception {
		Profile profile = tempRegistry.getProfile(oid);
		//MDI: if (profile == null) {
		//	profile = registry.getProfile(oid);
		//}
		return profile;
	}

	/**
	 * check the parameter of the validate method
	 * 
	 * @param message
	 * @param validationContext
	 * @param oid
	 * @throws Exception
	 */
	private void checkParameters(String message, String xmlValidationContext,
			String oid) throws Exception {
		if ("".equals(message) || message == null) {
			throw new Exception("Message is required.");
		}
		if (("".equals(oid) || oid == null)
				&& ("".equals(xmlValidationContext) || xmlValidationContext == null)) {
			throw new Exception(
					"Either an OID or the validation context is required.");
		}
	}
	
    
    private static String NVContextBased(String oid) 
    {
    	String rv="";
    	String[] tokens= oid.split("[.]");
    	if (tokens.length == 10) {
          System.out.println("next to last octet = " + tokens[8]);
          System.out.println("last octet = " + tokens[9]);
          if (tokens[8].equals("4") && tokens[9].length() == 9) {        
            rv=tokens[9];
          }
    	}
      return rv;
    }
    

//	private static SyncHL7Validator createValidator(
//			String profileFileName,
//			String constraintsFileName,
//			String valueSetLibFileName) throws Exception {
//		
//		
//
//		// The profile in XML
//		InputStream profileXML = MessageValidationV2.class.getResourceAsStream(profileFileName);
//		System.out.println("profileFileName=" + profileFileName);
//		System.out.println("profileXML: " + profileXML);
//		
//		
//		// ### [Update]
//		// The conformance context file XML
//		InputStream confContext = MessageValidationV2.class.getResourceAsStream(constraintsFileName);
//		System.out.println("constraintsFilesName=" + constraintsFileName);
//		
//		
//		System.out.println("MDI: GOT HERE! 1.1\n");
//		
//		// The get() at the end will throw an exception if something goes wrong
//		hl7.v2.profile.Profile profile = XMLDeserializer.deserialize(profileXML).get();
//
//		
//		System.out.println("MDI: GOT HERE! 1.2\n");
//		
//		// ### [Update]
//		// The get() at the end will throw an exception if something goes wrong
//		ConformanceContext c = DefaultConformanceContext.apply(confContext).get();
//		
//		System.out.println("MDI: GOT HERE! 1.3\n");
//
//		// The plugin map. This should be empty if no plugin is used
//		Map<String, Function3<Plugin, hl7.v2.instance.Element, Separators, EvalResult>> pluginMap = Map$.MODULE$.empty();
//		
//		System.out.println("MDI: GOT HERE! 1.4\n");
//		
//		
//		InputStream vsLibXML = MessageValidationV2.class.getResourceAsStream(valueSetLibFileName);
//		System.out.println("valueSetLibFileName=" + valueSetLibFileName);
//		ValueSetLibrary valueSetLibrary = ValueSetLibrary.apply(vsLibXML).get();
//
//		// A java friendly version of an HL7 validator
//		// This should be a singleton for a specific tool. We create it once and reuse it across validations
//		return new SyncHL7Validator(profile, valueSetLibrary, c, pluginMap);
//	}
//    
//	
	
	
//    public static String NVContextFree(String oid) 
//    {
//    	String rv="";
//    	String[] tokens= oid.split("[.]");
//        System.out.println("the last octet = " + tokens[9]);
//        if (tokens[9].length() == 5) {        
//          String lead2 = tokens[9].substring(0,2);
//          //System.out.println("lead2 = " + lead2);
//          if (lead2.equals("99")) {
//        	  rv= tokens[9].substring(2,5);
//          }          
//        }
//      return rv;
//    }
//    


// Cleaned Comments
//	if (!("".equals(xmlConfig) || xmlConfig == null)) {
//	// validate the xmlConfig
//	ArrayList<XmlError> validationErrors = new ArrayList<XmlError>();
//	XmlOptions validationOptions = new XmlOptions();
//	validationOptions.setErrorListener(validationErrors);
//	xmlConfigDoc = XmlConfigDocument.Factory.parse(xmlConfig);
//	if (!xmlConfigDoc.validate(validationOptions)) {
//		Iterator<XmlError> iter = validationErrors.iterator();
//		StringBuffer sb = new StringBuffer(
//				"The xmlConfig file is not valid.");
//		while (iter.hasNext()) {
//			XmlError xe = iter.next();
//			sb.append(" {").append(xe.getMessage()).append("}");
//		}
//		throw new Exception(sb.toString());
//	}
//	// retrieve the resources
//	System.out.println("Calling getResources\n");
//	
//	
//	// connectathon
//	messageValidationV2Utils.logFile(xmlConfigDoc.toString(),
//			"xmlConfig");
//	// connectathon
//	messageValidationV2Utils.logFile(xmlValidationContext,
//			"xmlValidationContext");
//}
//

	/* MDI: added next lines
	String r=null;
	try {
	r = new String(Base64.encodeBase64(reportDocument.toString().getBytes("ISO-8859-1")));
	} catch (Exception e) {}
	
	
	//MDI: added next lines
	try {
	System.out.println("\n\ndecoded report = " + new String(Base64.decodeBase64(r.getBytes("ISO-8859-1"))));
	} catch (Exception e) {}
	
	return r;
	*/

//	// IZ
//  testPlan="ONC 2015 Test Plan";
//		// Group 1
//  testGroup="Administration Group";
//  		// Case 1
//  testCase="IZ-AD-1_Admin_Child";
//  			// Step 1
//  testStep="IZ-AD-1.1_Send_V04_Z22";
//  			// Step 2
//  testStep="IZ-AD-1.2_Receive_ACK_Z23";
//  		// Case 2
//  testCase="IZ-AD-2_Admin_Adult";
//  			// Step 1
//  testStep="IZ-AD-2.1_Send_V04_Z22";
//  			// Step 2
//  testStep="IZ-AD-2.2_Receive_ACK_Z23";
//  		// Case 3
//  testCase="IZ-AD-3_No_Consent";
//				// Step 1
//  testStep="IZ-AD-3.1_Send_V04_Z22";
//				// Step 2
//  testStep="IZ-AD-3.2_Receive_ACK_Z23";
//  		// Case 4
//  testCase="IZ-AD-4_Delete_Record";
//  			// Step 1
//	testStep="IZ-AD-4.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-4.2_Receive_ACK_Z23";
//			// Case 5
//	testCase="IZ-AD-5_Refusal";
//				// Step 1
//	testStep="IZ-AD-5.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-5.2_Receive_ACK_Z23";
//			// Case 6
//	testCase="IZ-AD-6_Update_Record";
//				// Step 1
//	testStep="IZ-AD-6.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-6.2_Receive_ACK_Z23";
//			// Case 7
//	testCase="IZ-AD-7_Historical_IIS-Error";
//				// Step 1
//	testStep="IZ-AD-7.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-7.2_Receive_ACK_Z23";
//			// Case 8
//	testCase="IZ-AD-8_Admin_IIS-Warning";
//				// Step 1
//	testStep="IZ-AD-8.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-8.2_Receive_ACK_Z23";
//			// Case 9
//	testCase="IZ-AD-9_Admin_IIS-2Warings";
//				// Step 1
//	testStep="IZ-AD-9.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ-AD-9.2_Receive_ACK_Z23";
//			// Case 10
//	testCase="IZ-AD-10_Historical_IIS-SysError";
//				// Step 1
//	testStep="IZ-AD-10.1_Send_V04_Z22";
//				// Step 2
//	testStep="IZ_AD-10.2_Receive_ACK_Z23";
	//MDI: resources = messageValidationV2Utils.getResources(xmlConfigDoc,registry, tempRegistry, version, true);
	//MDI: build resources list by parsing newly added parameter containing ":" separated list of oids for the tables and retrieving them one by one
	//MDI_TODO: note that later on if we want to automatically load default tables based on the version we could retrieve them here before calling validate
}
