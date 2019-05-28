package gov.nist.healthcare.hl7ws.messagevalidation;

import gov.nist.healthcare.hl7ws.model.CBResource;
import gov.nist.healthcare.hl7ws.model.CFResource;
import gov.nist.healthcare.hl7ws.model.ResourceRegistry;

public class Utils {

	public static ResourceRegistry InitNewValidationResources(){
		
		ResourceRegistry newValidationResources = new ResourceRegistry();
		CFResource tmp = new CFResource();
		tmp.setOID("aa72383a-7b48-46e5-a74a-82e019591fe7");
		tmp.setProfile("/files/newvalidator/iz/Global/VXU-Z22/VXU-Z22_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/VXU-Z22/VXU-Z22_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/VXU-Z22/VXU-Z22_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99001", tmp);
		
		tmp = new CFResource();
		tmp.setOID("8da46a56-9f9a-4897-b7f1-08862c73d66a");
		tmp.setProfile("/files/newvalidator/iz/Global/ACK-Z23/ACK-Z23_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/ACK-Z23/ACK-Z23_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/ACK-Z23/ACK-Z23_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99002", tmp);
		
		tmp = new CFResource();
		tmp.setOID("2c1dc85c-4d4f-48ea-948a-835c1dc6436a");
		tmp.setProfile("/files/newvalidator/iz/Global/RSP-Z31/RSP-Z31_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/RSP-Z31/RSP-Z31_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/RSP-Z31/RSP-Z31_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99003", tmp);

		tmp = new CFResource();
		tmp.setOID("dc3368ee-35e9-4724-bc27-37f774867090");
		tmp.setProfile("/files/newvalidator/iz/Global/RSP-Z32/RSP-Z32_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/RSP-Z32/RSP-Z32_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/RSP-Z32/RSP-Z32_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99004", tmp);
		
		tmp = new CFResource();
		tmp.setOID("6a54b5a1-3d50-423e-9f84-8a721ad76342");
		tmp.setProfile("/files/newvalidator/iz/Global/RSP-Z33/RSP-Z33_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/RSP-Z33/RSP-Z33_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/RSP-Z33/RSP-Z33_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99005", tmp);
		
		tmp = new CFResource();
		tmp.setOID("89df2062-96c4-4cbc-9ef2-817e4b4bc4f1");
		tmp.setProfile("/files/newvalidator/iz/Global/QBP-Z34/QBP-Z34_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/QBP-Z34/QBP-Z34_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/QBP-Z34/QBP-Z34_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99006", tmp);
		
		tmp = new CFResource();
		tmp.setOID("e13dfbab-8832-4c3f-bfa5-0e9c18e644c4");
		tmp.setProfile("/files/newvalidator/iz/Global/RSP-Z42/RSP-Z42_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/RSP-Z42/RSP-Z42_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/RSP-Z42/RSP-Z42_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99007", tmp);
		
		tmp = new CFResource();
		tmp.setOID("b760d322-9afd-439e-96f5-43db66937c4e");
		tmp.setProfile("/files/newvalidator/iz/Global/QBP-Z44/QBP-Z44_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/QBP-Z44/QBP-Z44_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/QBP-Z44/QBP-Z44_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99008", tmp);
		
		tmp = new CFResource();
		tmp.setOID("8da46a56-9f9a-4897-b7f1-08862c73d66a");
		tmp.setProfile("/files/newvalidator/iz/Global/ACK-Z23/ACK-Z23_Profile.xml");
		tmp.setConstraints("/files/newvalidator/iz/Global/ACK-Z23a/ACK-Z23_Constraints.xml");
		tmp.setValueSets("/files/newvalidator/iz/Global/ACK-Z23/ACK-Z23_ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.3.99009", tmp);
		
		// Vital Records
		
		// CCOD_A04
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155cCCODA04");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99001", tmp);
		
		// CCOD_A08
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6CCDOA08");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99002", tmp);
	
		// CCOD_A11
		tmp = new CFResource();
		tmp.setOID("c1bd660c-0912-416b-928b-a146fCCODA11");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99003", tmp);

		// CREI_08
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6CREIA08");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99004", tmp);
		
		// CREI_A11
		tmp = new CFResource();
		tmp.setOID("c1bd660c-0912-416b-928b-a146f2e1CREI");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99005", tmp);
		
		// CREI_A04
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6CREIA04");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99006", tmp);
		
		
		// JDI_A04
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6aJDIA04");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99007", tmp);
		
		// JDI_A08
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6aJDIA08");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99008", tmp);
		
		// JDI_A11
		tmp = new CFResource();
		tmp.setOID("c1bd660c-0912-416b-928b-a146f2JDIA09");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99009", tmp);
		
		
		// PSDI_A04
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6a422A04");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99010", tmp);
		
		// PSDI_A08
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6a422A08");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99011", tmp);
		
		// PSDI_A11
		tmp = new CFResource();
		tmp.setOID("c1bd660c-0912-416b-928b-a146f2e1eA09");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99012", tmp);
		
	
		// RVC_11
		tmp = new CFResource();
		tmp.setOID("c1bd660c-0912-416b-928b-a146f2RVCA11");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99013", tmp);
		
		// RVC_A04
		tmp = new CFResource();
		tmp.setOID("0f44cb25-4bb0-4fe7-8244-155c6aRVCA04");
		tmp.setProfile("/files/newvalidator/vr/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/vr/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/vr/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.4.99014", tmp);
		

		
		// IHE Bed Management (profile 1)
		tmp = new CFResource();
		tmp.setOID("582df12684ae86e779067f81");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99001", tmp);
		
		// IHE Bed Management (profile 2)
		tmp = new CFResource();
		tmp.setOID("583304a184ae86e7f9a1c781");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99002", tmp);
		
		// IHE Bed Management (profile 3)
		tmp = new CFResource();
		tmp.setOID("5833077684ae86e7f9a32e41");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99003", tmp);
		
		// IHE Bed Management (profile 4)
		tmp = new CFResource();
		tmp.setOID("583309f284ae86e7f9a55b05");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99004", tmp);
		
		// IHE Bed Management (profile 5)
		tmp = new CFResource();
		tmp.setOID("583306a984ae86e7f9a27ba6");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99005", tmp);
			
		// IHE Bed Management (profile 6)
		tmp = new CFResource();
		tmp.setOID("5833082584ae86e7f9a491f1");
		tmp.setProfile("/files/newvalidator/ihe_bed/Global/Profile.xml");
		tmp.setConstraints("/files/newvalidator/ihe_bed/Global/Constraints.xml");
		tmp.setValueSets("/files/newvalidator/ihe_bed/Global/ValueSets.xml");
		newValidationResources.addResource("2.16.840.1.113883.3.72.2.5.99006", tmp);
		
		//
		// -------SS Profiles---------
		//
		
		// ---- ACK Profiles  ----
		
		// ADT-A01-PH_SS-Ack  2.16.840.1.114222.4.10.3.1
		//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b92";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b92");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.1", tmp);
		
		// ACK-A01-PH_SS-Ack  2.16.840.1.114222.4.10.3.2
		//		messageID="0d741773-619a-4ec1-b972-2589faa75b92";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b972-2589faa75b92");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.2", tmp);
		
		
		// ADT-A03-PH_SS-Ack  2.16.840.1.114222.4.10.3.3
		//		messageID="52f1dab8-d538-4c7e-b71b-39619b94c3bf";
		tmp = new CFResource();
		tmp.setOID("52f1dab8-d538-4c7e-b71b-39619b94c3bf");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.3", tmp);
		
		// ACK-A03-PH_SS-Ack  2.16.840.1.114222.4.10.3.4
		//		messageID="0d741773-619a-4ec1-b975-2589faa75b92";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b975-2589faa75b92");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.4", tmp);
		
		
		// ADT-A04-PH_SS-Ack  2.16.840.1.114222.4.10.3.5
		//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b93";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b93");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.5", tmp);
		
		// ACK-A04-PH_SS-Ack  2.16.840.1.114222.4.10.3.6
		//		messageID="0d741773-619a-4ec1-b973-2589faa75b92";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b973-2589faa75b92");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.6", tmp);
		
		// ADT-A08-PH_SS-Ack  2.16.840.1.114222.4.10.3.7
		//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b94";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b94");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.7", tmp);
		
		// ACK-A08-PH_SS-Ack  2.16.840.1.114222.4.10.3.8
		//		messageID="0d741773-619a-4ec1-b974-2589faa75b92";
		tmp = new CFResource();
		tmp.setOID("0d741773-619a-4ec1-b974-2589faa75b92");
		tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
		tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
		tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
		newValidationResources.addResource("2.16.840.1.114222.4.10.3.8", tmp);
		
		
		// ---- No ACK Profiles  ----
		
				// ADT-A01-PH_SS-NoAck  2.16.840.1.114222.4.10.3.9
				//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b92-2";
				tmp = new CFResource();
				tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b92-2");
				tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
				tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
				tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
				newValidationResources.addResource("2.16.840.1.114222.4.10.3.9", tmp);
				
				// ADT-A03-PH_SS-NoAck  2.16.840.1.114222.4.10.3.10
				//		messageID="52f1dab8-d538-4c7e-b71b-39619b94c3bf-2";
				tmp = new CFResource();
				tmp.setOID("52f1dab8-d538-4c7e-b71b-39619b94c3bf-2");
				tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
				tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
				tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
				newValidationResources.addResource("2.16.840.1.114222.4.10.3.10", tmp);
				
				// ADT-A04-PH_SS-NoAck  2.16.840.1.114222.4.10.3.11
				//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b93-2";
				tmp = new CFResource();
				tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b93-2");
				tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
				tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
				tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
				newValidationResources.addResource("2.16.840.1.114222.4.10.3.11", tmp);
				
				// ADT-A08-PH_SS-NoAck  2.16.840.1.114222.4.10.3.12
				//		messageID="0d741773-619a-4ec1-b6c2-2589faa75b94-2";
				tmp = new CFResource();
				tmp.setOID("0d741773-619a-4ec1-b6c2-2589faa75b94-2");
				tmp.setProfile    ("/files/newvalidator/ss/Global/ALL/ALL_Profile.xml");
				tmp.setConstraints("/files/newvalidator/ss/Global/ALL/ALL_Constraints.xml");
				tmp.setValueSets  ("/files/newvalidator/ss/Global/ALL/ALL_CF-ValueSetLibrary.xml");
				newValidationResources.addResource("2.16.840.1.114222.4.10.3.12", tmp);
				
		
		
		return newValidationResources;
	}
	
	public static CBResource getCBResource(String oid){
		CBResource resource = new CBResource();
		String testSel = oid;
		String domainPath= "";
		String valueSetsFile = "";
		String profilePath= "";
		String profileFile= "";
		String constraintsFile = "";
		String constraintsFile2 = "";
		String messageID="";
		String tdomain=testSel .substring(0,1);
		String tprof=testSel.substring(1,3);            	            
        String tgroup=Integer.toString(Integer.parseInt(testSel.substring(3,5)));
        String tcase=Integer.toString(Integer.parseInt(testSel.substring(5,7)));
        String tstep=Integer.toString(Integer.parseInt(testSel.substring(7,9)));
        String testPlan="";
        String testGroup="";
        String testCase="";
        String testStep="";
        
        System.out.println("Context-based: Domain = " + tdomain + " Profile " + tprof + " Group = " + tgroup + " Case = " + tcase + " Step = " + tstep);
        
        if (tdomain.equals("0")) {  // if domain is IZ
        	domainPath="iz";
        	testPlan="ONC 2015 Test Plan";
        	
        	if (tgroup.equals("1")) {
        		testGroup="Administration Group";
            	if (tcase.equals("1")) {
            		testCase="IZ-AD-1_Admin_Child";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-1.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-1.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("2")) {
            		testCase="IZ-AD-2_Admin_Adult";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-2.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-2.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("3")) {
            		testCase="IZ-AD-3_No_Consent";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-3.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-3.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("4")) {
            		testCase="IZ-AD-4_Delete_Record";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-4.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-4.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("5")) {
            		testCase="IZ-AD-5_Refusal";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-5.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-5.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("6")) {
            		testCase="IZ-AD-6_Update_Record";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-6.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-6.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("7")) {
            		testCase="IZ-AD-7_Historical_IIS-Error";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-7.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-7.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("8")) {
            		testCase="IZ-AD-8_Admin_IIS-Warning";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-8.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-8.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("9")) {
            		testCase="IZ-AD-9_Admin_IIS-2Warings";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-9.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-AD-9.2_Receive_ACK_Z23";
	            	}
            	}
            	if (tcase.equals("10")) {
            		testCase="IZ-AD-10_Historical_IIS-SysError";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-AD-10.1_Send_V04_Z22";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ_AD-10.2_Receive_ACK_Z23";
	            	}
            	}
        	}
        	
        	if (tgroup.equals("2")) {
        		testGroup="Evaluated History and Forecast Group";
            	if (tcase.equals("1")) {
            		testCase="IZ-QR-1_Query_Child";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-QR-1.1_Query_Q11_Z44";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-QR-1.2_Response_K11_Z42";
	            	}
            	}
            	if (tcase.equals("2")) {
            		testCase="IZ-QR-2_Query_Adult";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-QR-2.1_Query_Q11_Z44";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-QR-2.2-Response_K11_Z42";
	            	}
            	}
            	if (tcase.equals("3")) {
            		testCase="IZ-QR-3_Query_No_Patients";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-QR-3.1_Query_Q11_Z44";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-QR-3.2_Response_NF_K11_Z33";
	            	}
            	}
            	if (tcase.equals("4")) {
            		testCase="IZ-QR-4_Query_Too_Many";
	            	if (tstep.equals("1")) {
	            		testStep="IZ-QR-4.1_Query_Q11_Z44";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="IZ-QR-4.2_Response_TM_K11_Z33";
	            	}
            	}
        	}
        	
            if (tprof.equals("00")) {
            	profilePath="VXU-Z22";
				messageID="aa72383a-7b48-46e5-a74a-82e019591fe7";
            }
            
            if (tprof.equals("01")) {
            	profilePath="ACK-Z23";
            	messageID="8da46a56-9f9a-4897-b7f1-08862c73d66a";
            }
            
            if (tprof.equals("02")) {
            	profilePath="RSP-Z31";
            	messageID="2c1dc85c-4d4f-48ea-948a-835c1dc6436a";
            }
            
            if (tprof.equals("03")) {
            	profilePath="RSP-Z32";
            	messageID="dc3368ee-35e9-4724-bc27-37f774867090";
            }
            
            if (tprof.equals("04")) {
            	profilePath="RSP-Z33";
            	messageID="6a54b5a1-3d50-423e-9f84-8a721ad76342";
            }
            
            if (tprof.equals("05")) {
            	profilePath="QBP-Z34";
            	messageID="89df2062-96c4-4cbc-9ef2-817e4b4bc4f1";
            }
            
            if (tprof.equals("06")) {
            	profilePath="RSP-Z42";
            	messageID="e13dfbab-8832-4c3f-bfa5-0e9c18e644c4";
            	
            }
            
            if (tprof.equals("07")) {
            	profilePath="QBP-Z44";
            	messageID="b760d322-9afd-439e-96f5-43db66937c4e";
            }
        }
        
        
        if (tdomain.equals("1")) { // if domain is SS
        	domainPath="ss";
        	profilePath="ALL";
        	testPlan="ONC 2015 Certification";
        	if (tgroup.equals("1")) {
        		testGroup="Urgent Care Visit";
            	if (tcase.equals("1")) {
            		testCase="SS-UC-1_UC_Visit_Influenza_Child";
	            	if (tstep.equals("1")) {
	            		testStep="SS-UC-1.1_Registration_A04";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="SS-UC-1.2_Discharge_A03";
	            	}
            	}
        	}
        	if (tgroup.equals("2")) {
        		testGroup="ED Visit with Mortality";
            	if (tcase.equals("1")) {
            		testCase="SS-ED-2_ED_Visit_Patient_Dies";
	            	if (tstep.equals("1")) {
	            		testStep="SS-ED-2.1_Registration_A04";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="SS-ED-2.2_Update_A08";
	            	}
	            	if (tstep.equals("3")) {
	            		testStep="SS-ED-2.3_Discharge_A03";
	            	}
            	}
        	}
        	if (tgroup.equals("3")) {
        		testGroup="ED Visit with Inpatient Admission";
            	if (tcase.equals("1")) {
            		testCase="SS-ED-3_ED_Visit_Patient_Admitted";
	            	if (tstep.equals("1")) {
	            		testStep="SS-ED-3.1_Registration_A04";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="SS-ED-3.2_Update_A08";
	            	}
	            	if (tstep.equals("3")) {
	            		testStep="SS-ED-3.3_Discharge_A03";
	            	}
	            	if (tstep.equals("4")) {
	             		testStep="SS-ED-3.4_Admission_A01";
	            	}
            	}
        	}
        	if (tgroup.equals("4")) {
        		testGroup="Inpatient Visit";
            	if (tcase.equals("1")) {
            		testCase="SS-IP-4_Inpatient_Visit_Surgery";
	            	if (tstep.equals("1")) {
	            		testStep="SS-IP-4.1_Admission_A01";
	            	}
	            	if (tstep.equals("2")) {
	            		testStep="SS-IP-4.2_Discharge_A03";
	            	}
            	}
        	}
        		
            if (tprof.equals("00")) { //ACK_A01
				messageID="0d741773-619a-4ec1-b972-2589faa75b92";
            }
            if (tprof.equals("01")) { //ACK_A03
				messageID="0d741773-619a-4ec1-b975-2589faa75b92";
            }
            if (tprof.equals("02")) { //ACK_A04
				messageID="0d741773-619a-4ec1-b973-2589faa75b92";
            }
            if (tprof.equals("03")) { //ACK_A08
				messageID="0d741773-619a-4ec1-b974-2589faa75b92";
            }
            if (tprof.equals("04")) { //ADT_A01
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b92";
            }
            if (tprof.equals("05")) { //ADT_A03
				messageID="52f1dab8-d538-4c7e-b71b-39619b94c3bf";
            }
            if (tprof.equals("06")) { //ADT_A04
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b93";
            }
            if (tprof.equals("07")) { //ADT_A08
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b94";
            }
            if (tprof.equals("08")) { //ADT_A01 NoAck
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b92-2";
            }
            if (tprof.equals("09")) { //ADT_A03 NoAck
				messageID="52f1dab8-d538-4c7e-b71b-39619b94c3bf-2";
            }
            if (tprof.equals("10")) { //ADT_A04 NoAck
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b93-2";
            }
            if (tprof.equals("11")) { //ADT_A08 NoAck
				messageID="0d741773-619a-4ec1-b6c2-2589faa75b94-2";
            }	
        }
        
		System.out.println("MDI: THE OID ============ " + oid);
		profileFile=      "/files/newvalidator/" + domainPath + "/Global/" + profilePath + "/" + profilePath + "_Profile.xml";
		constraintsFile = "/files/newvalidator/" + domainPath + "/Global/" + profilePath + "/" + profilePath + "_Constraints.xml";

		constraintsFile2= "/files/newvalidator/" + domainPath + "/ContextBased/TestGroup_" + Integer.parseInt(tgroup) + "/TestCase_" + Integer.parseInt(tcase) + "/TestStep_" + Integer.parseInt(tstep) + "/Constraints.xml" ;

		if (domainPath.equals("iz")) 
		  valueSetsFile=    "/files/newvalidator/" + domainPath + "/Global/" + profilePath + "/" + profilePath + "_ValueSetLibrary.xml";
		else if (domainPath.equals("ss"))
	      valueSetsFile=    "/files/newvalidator/" + domainPath + "/Global/" + profilePath + "/" + profilePath + "_CB-ValueSetLibrary.xml";
		

		CFResource cfr = new CFResource();
		cfr.setProfile(profileFile);
		cfr.setConstraints(constraintsFile);
		cfr.setValueSets(valueSetsFile);
		cfr.setOID(messageID);
		
		resource.settPlan(testPlan);
		resource.settGroup(testGroup);
		resource.settCase(testCase);
		resource.settStep(testStep);
		resource.setContraints(constraintsFile2);
		resource.setBase(cfr);
		
		
		return resource;
	}
	
	

}
