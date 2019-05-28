package gov.nist.healthcare.hl7ws.client;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.URL;
import java.net.URI;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.commons.codec.binary.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;




public class HL7WSClient {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {


		String loadResource;

		// String URL =
		// "http://localhost:8080/services/rest/MessageValidationV2/";
		// MessageValidationV2RestClient mvClient = new
		// MessageValidationV2RestClient(
		// URL + "Hl7MessageValidation");
		// // 1. getServiceStatus() see if profile exists
		// String serviceStatus = mvClient.getServiceStatus();
		// System.out.println(serviceStatus);
		//
		// // 2. load local profile (loadResource)	    
		// String profile = IOUtils.toString(new FileReader(new File(
		// "client/profile.xml")));
		// String loadResource = mvClient.loadResource(profile, null,
		// "PROFILE");
		// System.out.println(loadResource);
		//
		// // 3. call getServiceStatus again
		// serviceStatus = mvClient.getServiceStatus();
		// System.out.println(serviceStatus);
		//
		// // 4. call the validate method
		// String message = IOUtils.toString(new FileReader(new File(
		// "client/message.er7")));
		// String profileOid = "1.3.6.1.4.12559.11.1.1.1";
		// String xmlConfig = IOUtils.toString(new FileReader(new File(
		// "client/xmlconfig.xml")));
		//
		// String xmlValidationContext = IOUtils.toString(new FileReader(new
		// File(
		// "client/validationcontext.xml")));
		// String report = mvClient.validate(message, profileOid, xmlConfig,
		// xmlValidationContext);
		// System.out.println(report);

	    //		String URL = "http://localhost:8080/services/rest/MessageGenerationV2/";

	    //	        String URL = "http://xreg2.nist.gov:8080/HL7WS/services/rest/";


	    //  	        String URL = "http://xreg2.nist.gov:8080/HL7WS/services/soap/MessageGenerationV2/";

	    //String URL = "http://localhost:8080/hl7ws-base/services/soap/MessageGenerationV2/";
		
		// this is the deployed web service
		//DONT USE THIS ANYMORE: String URL = "http://hit-testing2.nist.gov:8090/hl7v2ws//services/soap/MessageValidationV2/";	
  	    String URL = "http://hl7v2.ws.nist.gov/hl7v2ws/services//soap/MessageValidationV2/";
		
		//this is the local deployment of the web service
	    //String URL = "http://localhost:8080/hl7v2ws-base/services/soap/MessageValidationV2/";
	    
	    
		//		MessageGenerationV2SoapClient client = new MessageGenerationV2SoapClient(URL);
		MessageValidationV2SoapClient client = new MessageValidationV2SoapClient(URL);

		URL url = HL7WSClient.class.getResource("/files/xmlconfig.xml");
		System.out.println("Value = " + url);
		
		//try {
			//String xmlConfig = IOUtils.toString(new FileReader(new File(url.toURI())));
		//	String xmlConfig = IOUtils.toString(new FileReader(new File(HL7WSClient.class.getResource("/files/xmlconfig.xml").toURI())));
		//} catch (Exception e) {};
		
		URI dataFileURI=getPath("/files/");
		String dataFilePath=dataFileURI.toString().split(":")[1];
		
		String workingDir = System.getProperty("user.dir");
		System.out.println("Current working directory : " + workingDir);
		   
		String sstatus = client.getServiceStatus();
		System.out.println("service status --------->" + sstatus + "\n\n");


		
		
		// load all tables

		// 2.3.1

		// load HL7Tables (loadResource)
		//		String table1 = IOUtils.toString(new FileReader(new File("files/2.3.1/HL7Tables.xml")));
		//		loadResource = client.loadResource(table1, null, "TABLE");
		//		System.out.println("xmlLoadResource --------->" + loadResource + "\n\n");

		// load UserTables (loadResource)
		//		String table2 = IOUtils.toString(new FileReader(new File("files/2.3.1/UserTables.xml")));
		//		loadResource = client.loadResource(table2, null, "TABLE");
		//		System.out.println("xmlLoadResource --------->" + loadResource + "\n\n");

		// 2.5

		// load HL7Tables (loadResource)
		//		String table3 = IOUtils.toString(new FileReader(new File("files/2.5/HL7Tables.xml")));
		//		loadResource = client.loadResource(table3, null, "TABLE");
		//		System.out.println("xmlLoadResource --------->" + loadResource + "\n\n");

		// load UserTables (loadResource)
		//		String table4 = IOUtils.toString(new FileReader(new File("files/2.5/UserTables.xml")));
		//		loadResource = client.loadResource(table4, null, "TABLE");
		//		System.out.println("xmlLoadResource --------->" + loadResource + "\n\n");


		// Set the xmlConfig, and Generation/Validation Contexts 
		// Assumption is that these are the same for all messages.  Not true, esp. for the Generation/Validation Contexts!
		//String xmlConfig = IOUtils.toString(new FileReader(new File("/files/xmlconfig.xml")));
        //String xmlGenerationContext = IOUtils.toString(new FileReader(new File("/files/generationcontext3.xml")));
		//                String xmlValidationContext = IOUtils.toString(new FileReader(new File("/files/MessageValidationContext-1.xml")));

		// For each message listed in files/messages.txt
		//  (a) open and read it (format:  msg_filename,validation_context_filename)
		//  (b) parse it to determine which profile to use (based on MSH.9)
		//  (c) parse it to determine which table to use (based on MSH.12 HL7 version)
		//  (d) if not already loaded, load that profile
		//  (e) parse the response to get the oid and associate the oid with the profile name using a map
		//  (f) if not already loaded, load the tables (base and user) 
		//  (g) parse the response to get the oids, and associate the oids with the table names using a map
		//  (h) call validate with the message and the profile and table oids (for the profile and tables already loaded)

		String[] msgFields;
		String[] msh9Components;
		String[] msh12Components;
		String message;
		String profileFN;
		String profile;
		String basetablesFN;
		String usertablesFN;
		String baseTables;
		String userTables;
		String[] profileOids = new String[2000];
		String[] baseTableOids = new String[2000];
		String[] userTableOids = new String[2000];
		String[] messages = new String[2000];
		String baseTablesOid;
		String userTablesOid;
                String xmlValidationContext = null;
		ArrayList<String> profileList = new ArrayList<String>();
		ArrayList<String> baseTablesList = new ArrayList<String>();
		ArrayList<String> userTablesList = new ArrayList<String>();
		ArrayList<String> validationContextList = new ArrayList<String>();
		ArrayList<String> validationContextFNList = new ArrayList<String>();
		HashMap map = new HashMap<String,String>();
		int totalMessages = 0;

		// Try to valide a IZ message using the pre-loaded VXU Profile and associated Tables
		String iz_profile_oid="2.16.840.1.113883.3.72.2.2.99001";
		String iz_table_oids="2.16.840.1.113883.3.72.4.2.99001:2.16.840.1.113883.3.72.4.2.99002:2.16.840.1.113883.3.72.4.2.99003";
		
		//String iz_vc_fn=dataFilePath + "vcs/IZ/ContextFree/ValidationContext.xml";
		String iz_vc_fn=dataFilePath + "vcs/IZ/ContextBased/IZ_1_1.1_Admin_Child_Max_ValidationContext.xml";
		String iz_vc=IOUtils.toString(new FileReader(new File(iz_vc_fn)));
		
	    //String iz_msg_fn=dataFilePath + "IZ/IZ_1_Admin_Child.prev/IZ_1_1_Admin_Child/IZ_1_1.1_Max/IZ_1_1.1_Admin_Child_Max_Message.txt";
		String iz_msg_fn=dataFilePath + "IZ/IZ_1_Admin_Child/IZ_1_1_Admin_Child/IZ_1_1.1_Max/IZ_1_1.1_Admin_Child_Max_Message.txt";
		
		String iz_msg=IOUtils.toString(new FileReader(new File(iz_msg_fn)));
	    System.out.println("\n\nValidating (using profile oid = " + iz_profile_oid + " and table oid = " + iz_table_oids  + ") and vc=" + iz_vc_fn + ": " + iz_msg.replaceAll("\r","\n") + "\n\n");
		String res = client.validate(iz_msg,iz_profile_oid, iz_table_oids , iz_vc);
//		String res = client.validate(iz_msg,iz_profile_oid, iz_table_oids , null);
	    System.out.println("Received: " + res.replaceAll("\r","\n") + "\n\n\n");
	    
	    
	    // Try to validate a IZ message using the **NEW** Validator
	    iz_profile_oid="2.16.840.1.113883.3.72.2.3.99001";
	    System.out.println("\n\nValidating with NEW VALIDATOR (using profile oid = " + iz_profile_oid + ") " + ": " + iz_msg.replaceAll("\r","\n") + "\n\n");
	    res = client.validate(iz_msg,iz_profile_oid, null , null);
	    System.out.println("Received: " + res.replaceAll("\r","\n") + "\n\n\n");
	    
		// Try to valide a LRI message using a pre-loaded Profile and associated Tables
		String lri_profile_oid="2.16.840.1.113883.9.19";
		String lri_table_oids="2.16.840.1.113883.3.72.4.2.99004:2.16.840.1.113883.3.72.4.2.99005:2.16.840.1.113883.3.72.4.2.99006:2.16.840.1.113883.3.72.4.2.99007:2.16.840.1.113883.3.72.4.2.99008:2.16.840.1.113883.3.72.4.2.99009:2.16.840.1.113883.3.72.4.2.99010:2.16.840.1.113883.3.72.4.2.99011:2.16.840.1.113883.3.72.4.2.99012";
		String lri_vc_fn=dataFilePath + "vcs/LRI/ContextFree/ValidationContext.xml";
		String lri_vc=IOUtils.toString(new FileReader(new File(lri_vc_fn)));
		String lri_msg_fn=dataFilePath + "LRI/EHR/NG/LRI_1_Sed_Rate/LRI_1.0_Sed_Rate/LRI_1.0-NG_Final/LRI_1.0-NG_Sed_Rate_Final_Message.txt";
		String lri_msg=IOUtils.toString(new FileReader(new File(lri_msg_fn)));
	    System.out.println("\n\nValidating (using profile oid = " + lri_profile_oid + " and table oid = " + lri_table_oids  + ") and vc=" + lri_vc_fn + ": " + lri_msg.replaceAll("\r","\n") + "\n\n");
		res = client.validate(lri_msg,lri_profile_oid, lri_table_oids , lri_vc);
	    System.out.println("Received: " + res.replaceAll("\r","\n") + "\n\n\n");
	    
	    
		// Try to valide a ELR message using the pre-loaded Profile and associated Tables
		String elr_profile_oid="2.16.840.1.113883.9.11";
		String elr_table_oids="2.16.840.1.113883.3.72.4.2.99015:2.16.840.1.113883.3.72.4.2.99016:2.16.840.1.113883.3.72.4.2.99017:2.16.840.1.113883.3.72.4.2.99018:2.16.840.1.113883.3.72.4.2.99019 ";
		String elr_vc_fn=dataFilePath + "vcs/ELR/ContextFree/ValidationContext.xml";
		String elr_vc=IOUtils.toString(new FileReader(new File(elr_vc_fn)));
		String elr_msg_fn=dataFilePath + "ELR/ELR_1_Maximally_Populated_Final_Quantitative_Result/ELR_1_1/ELR_1_1.1_Max/ELR_1_1.1_Message.txt";
		String elr_msg=IOUtils.toString(new FileReader(new File(elr_msg_fn)));
	    System.out.println("\n\nValidating (using profile oid = " + elr_profile_oid + " and table oid = " + elr_table_oids  + ") and vc=" + elr_vc_fn + ": " + elr_msg.replaceAll("\r","\n") + "\n\n");
		res = client.validate(elr_msg,elr_profile_oid, elr_table_oids , elr_vc);
	    System.out.println("Received: " + res.replaceAll("\r","\n") + "\n\n\n");
		
		try{
		    // Open the file that is the first 
		    // command line parameter
		    //InputStream fstream = new URL(getPath("/files/messages.txt").toString()).openStream();
		    FileInputStream fstream = new FileInputStream(dataFilePath + "messages.txt");
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String testMessageInfo;
		    String[] testMessageInfoComponents;
		    String testMessageFN;
		    //		    String xmlConfigFN;
		    String validationContextFN;

		    //Read File Line By Line
		    while ((testMessageInfo = br.readLine()) != null)   {

			testMessageInfoComponents=testMessageInfo.split(",");
		        testMessageFN=testMessageInfoComponents[0];
		        validationContextFN=testMessageInfoComponents[1];

			//System.out.println("validationContextFN = " + validationContextFN + "\n");

  		    if (! "NONE".equals(validationContextFN) ) {
			    //validationContextList.add(IOUtils.toString(new FileReader(new File("/files/" + validationContextFN))));
			    validationContextList.add(IOUtils.toString(new FileReader(new File(dataFilePath + validationContextFN))));
			} else {
			    validationContextList.add(null);
			}
  		    
  		    validationContextFNList.add(validationContextFN);

			// Open and read the test message
        	messages[totalMessages] = IOUtils.toString(new FileReader(new File(dataFilePath + testMessageFN)));
			// Get the profile version to use from MSH.9
			msgFields = messages[totalMessages].split("\\|");
			msh9Components = msgFields[8].split("\\^");
			profileFN = "NIST_" + msh9Components[0] + "_" + msh9Components[1] + ".xml";
			//System.out.println (testMessageFN + " " + msgFields[8] + " " + msh9Components[0] + "_" + msh9Components[1]);
  		        //System.out.println (testMessageFN + " " + profileFN);

			// Get the HL7 version from MSH.12
			// the version is part of the pathname to the tables (e.g. "2.3.1/HL7Tables.xml" and "2.3.1/UserTables.xml")
			msh12Components = msgFields[11].split("\r");
			basetablesFN = msh12Components[0] + "/" + "HL7Tables.xml";
			usertablesFN = msh12Components[0] + "/" + "UserTables.xml";

			// if not loaded already then load the base and user tables and get the oids
			if (!baseTablesList.contains(basetablesFN)) {
			   // load the base table
			   System.out.print("\nLoading tables: " + basetablesFN + " " );
			   baseTables = IOUtils.toString(new FileReader(new File(dataFilePath + "tables/" + basetablesFN)));
			   loadResource = client.loadResource(baseTables, null, "TABLE");                          
			   baseTableOids[totalMessages]=getOid(loadResource);
			   map.put( basetablesFN, baseTableOids[totalMessages] );
 			   System.out.println("oid = " + baseTableOids[totalMessages]);

		           // load the user table
   		           System.out.print("\nLoading tables: " + usertablesFN + " " );
			   userTables = IOUtils.toString(new FileReader(new File(dataFilePath + "tables/" + usertablesFN)));
			   loadResource = client.loadResource(userTables, null, "TABLE");                          
			   userTableOids[totalMessages]=getOid(loadResource);
			   map.put( usertablesFN, userTableOids[totalMessages] );
 			   System.out.println("oid = " + userTableOids[totalMessages]);
			   
			}
		        baseTablesList.add(basetablesFN);
		        userTablesList.add(usertablesFN);

			// load any necessary tables called for in the xmlConfig file

			// if not loaded already then load the profile and get the oid
			if (!profileList.contains(profileFN)) {
			  System.out.print("\nLoading profile: " + profileFN + " " );
 			  // load local profile (loadResource)
			  profile = IOUtils.toString(new FileReader(new File(dataFilePath + "profiles/" + profileFN)));
			  loadResource = client.loadResource(profile, null, "PROFILE");
			  //System.out.println("xmlLoadResource --------->" + loadResource + "\n\n");
			  profileOids[totalMessages]=getOid(loadResource);
			  map.put( profileFN, profileOids[totalMessages] );
			  System.out.println("oid = " + profileOids[totalMessages]);
			}

	                profileList.add(profileFN);
		        totalMessages += 1;
		    }
		    //Close the input stream
		    in.close();

		} catch (Exception e){//Catch exception if any
		    System.err.println("Error: " + e.getMessage());
		}


	    //System.exit(1);
		
	    System.out.println("\n\nTotal messages = " + totalMessages);

		System.out.println("\n\nMessage to base table mapping:");
		for (int i = 0; i < baseTablesList.size(); i++) {
		    System.out.println("message[" + i +"] = " + baseTablesList.get(i));
		}

		System.out.println("\n\nMessage to user table mapping:");
		for (int i = 0; i < userTablesList.size(); i++) {
		    System.out.println("message[" + i +"] = " + userTablesList.get(i));
		}

		System.out.println("\n\nMessage to profile mapping:");
		for (int i = 0; i < profileList.size(); i++) {
		    System.out.println("message[" + i +"] = " + profileList.get(i));
		}


		System.out.println("\n\nMap = " +  map);


		for (int i = 0; i < totalMessages; i++) {
		    //System.out.println("\n\nValidating (using profile oid = " + map.get(profileList.get(i)) +    " ) : " + messages[i].replaceAll("\r","\n") + "\n\n");
		    //		    String result = client.validate(messages[i],(String) map.get(profileList.get(i)),null,null);
		    baseTablesOid=(String)map.get(baseTablesList.get(i));
		    userTablesOid=(String)map.get(userTablesList.get(i));
		    String tableOids = baseTablesOid + ":" + userTablesOid;
		    //System.out.println("\n\nValidating (using profile oid = " + map.get(profileList.get(i)) + " and table oid = " + tableOids  + ") and vc=" + validationContextList.get(i)+ ": " + messages[i].replaceAll("\r","\n") + "\n\n");
		    System.out.println("\n\nValidating (using profile oid = " + map.get(profileList.get(i)) + " and table oid = " + tableOids  + ") and vc=" + validationContextFNList.get(i)+ ": " + messages[i].replaceAll("\r","\n") + "\n\n");
		    String result = client.validate(messages[i],(String) map.get(profileList.get(i)), tableOids ,validationContextList.get(i));
		    
		    System.out.println("vc content = " + validationContextList.get(i));
		    
		    System.out.println("Received: " + result.replaceAll("\r","\n") + "\n\n\n");
		}

	       
	}

	private static URI getPath(String fn) {
        URI uri=null;
		try {
			uri = HL7WSClient.class.getResource(fn).toURI();
		} catch (Exception e) {
			System.out.println("Exception caught: " + e.toString());
		};
		return uri;
	}
	
    private static String getValue(String tag, Element element) {
	NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
	Node node = (Node) nodes.item(0);
	return node.getNodeValue();
    }

    // parse the response to get the oid
    private static String getOid(String xmlResponse) {

	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	Document doc=null;
	String oid=null;

	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (Exception ex) {};

	try{
  	  doc = dBuilder.parse(new InputSource(new StringReader(xmlResponse)));
	} catch (Exception ex) {};
	doc.getDocumentElement().normalize();
	NodeList nodes = doc.getElementsByTagName("xmlLoadResource");
	//System.out.println("# nodes = " + nodes.getLength());
	for (int i = 0; i < nodes.getLength(); i++) {
	  Node node = nodes.item(i);
	  if (node.getNodeType() == Node.ELEMENT_NODE) {
	    Element element = (Element) node;
	    oid=getValue("oid", element);
          }
	}
	return oid;
    }



}

