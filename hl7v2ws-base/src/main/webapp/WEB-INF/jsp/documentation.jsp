<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <style type="text/css">
  	.ui-button-text { font-size: 0.7em; }
  	.ui-accordion-header { font-size: 0.8em; }
  </style>	
  <style>
	table,th,td
	{
		border:1px solid black;
	}
  </style>
  <script language="JavaScript" type="text/javascript">
  	   $(function(){ 
   		  $('div#clients').accordion({});
   		  $(':button').button({});
   		  $('#java_doc').click(function(){
   				window.open('javadoc/index.html');   
   		  });
   		  $('#quick_start').click(function(){
 				window.open('extern_resources/supportFiles/HL7V2WS_doc_QUICK.pdf');   
 		  });
   		  $('#war_file').click(function(){
				window.open('http://sourceforge.net/p/hl7-nist/code/HEAD/tree/hl7v2ws-wars//hl7v2ws-base-1.0.0-SNAPSHOT.war');   
		  });
   		  $('#overview_install').click(function(){
			window.open('javadoc/index.html');   
		  });
   		   $('#overview_install').click(function(){
			window.open('extern_resources/NIST_webservices.pdf');   
		  }); 
   		   
   		 $('#clients_zip').click(function(){
 			window.open('extern_resources/NISTHL7WebService-Clients.zip');   
 		  }); 
    		
   		
   		
  	  });  
  </script> 
  </head>
  <body>
<br/>
<p>
<p>

<div class="document"> 

<button  id="quick_start">Quick Start Guide</button>

<button  id="java_doc">Java doc</button>

<button id="war_file">Download WAR file</button>

<br/>
You can't download the source code to an example client by clicking <button id="clients_zip">Download NIST Web Service Clients</button>
<br/>

<br/>

<!-- 
NOTE: The Message Conformance Profile for Immunization Meaningful Use VXU_V04 type messages and associated Tables, have been pre-loaded and are selectable with the OIDs show below. 
In addition a <a href="extern_resources/supportFiles/IZ_ContextFree_ValidationContext.xml">Validation Context</a> file must be specified in order to validate VXU_V04 messages using these pre-loaded resources.
<br>
<table>
<tr><td>IZ MU 2014 Profile v1.1 OID = 2.16.840.1.113883.3.72.2.2.99001</td></tr>
<tr><td>Table OID list = 2.16.840.1.113883.3.72.4.2.99001:2.16.840.1.113883.3.72.4.2.99002:2.16.840.1.113883.3.72.4.2.99003</td></tr>
</table> 

-->





<h2>Pre-Loaded Resources</h2>


<table style="font-family: helvetica, sans-serif; font-size:0.8em; font-weight: bold;">  
  <tr>
    <th>Domain</th>
    <th>Profile</th>
    <th>Version</th>
    <th>Profile OID</th>
    <th>Table Name</th>
    <th>Table OID</th>
  </tr>
  
    
    <tr>
    <td>IZ</td>
    <td>VXU</td>
    <td>HL7 v2.5.1 <br> IG 1.4<br> 8/2012</td>
    <td>2.16.840.1.113883.3.72.2.2.99001</td>
    <td>IG_PHINVADS_IZVocab <br> NoCheckTables_IZVocab <br> Reduced_HL7tablesV2.5.1</td>
    <td>2.16.840.1.113883.3.72.4.2.99001 2.16.840.1.113883.3.72.4.2.99002 2.16.840.1.113883.3.72.4.2.99003 </td>
  </tr>
 
   
  <tr>
    <td>IZ</td>
    <td>Z22 <br> Z23 <br> Z31 <br> Z32 <br> Z33 <br> Z34 <br> Z42 <br> Z44</td>
    <td>HL7 v2.5.1 <br> IG 1.5<br> 10/2014</td>
    <td>2.16.840.1.113883.3.72.2.3.99001 <br> 2.16.840.1.113883.3.72.2.3.99002 <br> 2.16.840.1.113883.3.72.2.3.99003 <br> 2.16.840.1.113883.3.72.2.3.99004 <br> 2.16.840.1.113883.3.72.2.3.99005 <br> 2.16.840.1.113883.3.72.2.3.99006 <br> 2.16.840.1.113883.3.72.2.3.99007 <br> 2.16.840.1.113883.3.72.2.3.99008</td>
    <td>(PRE-LOADED)</td>
    <td></td>
  </tr>
   
    <tr>    
  	<td>LRI</td>
    <td>GU_RU <br> GU_RN <br> NG_RU <br> NG_RN <br></td>
    <td>HL7 v2.5.1 <br> IG 1.0<br> 7/2012</td>
    <td>2.16.840.1.113883.9.17 <br> 2.16.840.1.113883.9.18 <br> 2.16.840.1.113883.9.19 <br> 2.16.840.1.113883.9.20</td>
    <td>LRI_VALUE-SETS <br> LRIVOCAB_NIST_COMPLIANT <br> LOINC_ALL_CODES <br> SNOMED_SCT_ALL_CODES <br> UCUM_ALL_CODES</td>
    <td> 2.16.840.1.113883.3.72.4.2.99004  2.16.840.1.113883.3.72.4.2.99005  2.16.840.1.113883.3.72.4.2.99006  2.16.840.1.113883.3.72.4.2.99007  2.16.840.1.113883.3.72.4.2.99008 </td>
    </tr>
  	
  	<tr>    
  	<td>SS</td>
    <td>ADT_A01 <br> ADT_A03 <br> ADT_A04 <br> ADT_A08 </td>
    <td>HL7 v2.5.1 <br> IG 1.1<br> 8/2012</td>
    <td>2.16.840.1.113883.3.72.2.2.99002 2.16.840.1.113883.3.72.2.2.99003 2.16.840.1.113883.3.72.2.2.99004 2.16.840.1.113883.3.72.2.2.99005</td>
    <td> IG_PHINVADS_SYNDROMICVocab <br> RestrictedTables_SYNDROMICVocab <br> MergedTables_SYNDROMICVocab <br> MergedAndRestrictedTables_SYNDROMICVocab.xml <br> NoCheckTables_SYNDROMICVocab.xml <br> Reduced_HL7tablesV2.5.1</td>
    <td> 2.16.840.1.113883.3.72.4.2.99009  2.16.840.1.113883.3.72.4.2.99010  2.16.840.1.113883.3.72.4.2.99011  2.16.840.1.113883.3.72.4.2.99012  2.16.840.1.113883.3.72.4.2.99013  2.16.840.1.113883.3.72.4.2.99014</td>
    </tr>
    
    <tr>
    <td>SS</td>
    <td>--ACK-- <br> ADT_A01 <br> ACK_A01 <br> ADT_A03 <br> ACK_A03 <br> ADT_A04 <br> ACK_A04 <br> ADT_A08 <br> ACK_A08 <br> -NoACK- <br> ADT_A01 <br> ADT_A03 <br> ADT_A04 <br> ADT_A08</td>
    <td>HL7 v2.5.1 <br> IG 2.0<br> 4/2015 <br> <br> Erratum for PHIN Guide for Syndromic Surveillance Messaging <br> Rel 2.0 <br> 9/2015</td>
    <td> <br> 2.16.840.1.114222.4.10.3.1  <br> 2.16.840.1.114222.4.10.3.2  <br> 2.16.840.1.114222.4.10.3.3  <br> 2.16.840.1.114222.4.10.3.4  <br> 2.16.840.1.114222.4.10.3.5  <br> 2.16.840.1.114222.4.10.3.6  <br> 2.16.840.1.114222.4.10.3.7  <br> 2.16.840.1.114222.4.10.3.8 <br> <br>  2.16.840.1.114222.4.10.3.9  <br> 2.16.840.1.114222.4.10.3.10 <br> 2.16.840.1.114222.4.10.3.11  <br> 2.16.840.1.114222.4.10.3.12 </td>
    <td>(PRE-LOADED)</td>
    <td></td>
  </tr>
    
  	<tr>
    <td>VR</td>
    <td>CCOD_A04 <br> CCOD_A08 <br> CCOD_A11 <br> CREI_A08 <br> CREI_A11 <br> CREA_A04 <br> JDI_A04 <br> JDI_A08 <br> JDI_A11 <br> PSDI_A04 <br> PSDI_A08 <br> PSDI_A11 <br> RVC_A11 <br> RVC_A04</td>
    <td>HL7 v2.6 <br> IG 1.0<br> 08/2016 <br> <br> Vital Record Death Reporting Clarification Addendum <br> Release 2 <br> Version 1.0 <br> 12/2016</td>
    <td>2.16.840.1.113883.3.72.2.4.99001  <br> 2.16.840.1.113883.3.72.2.4.99002 <br> 2.16.840.1.113883.3.72.2.4.99003 <br> 2.16.840.1.113883.3.72.2.4.99004 <br> 2.16.840.1.113883.3.72.2.4.99005 <br> 2.16.840.1.113883.3.72.2.4.99006  <br> 2.16.840.1.113883.3.72.2.4.99007 <br> 2.16.840.1.113883.3.72.2.4.99008 <br> 2.16.840.1.113883.3.72.2.4.99009 <br> 2.16.840.1.113883.3.72.2.4.99010  <br> 2.16.840.1.113883.3.72.2.4.99011 <br> 2.16.840.1.113883.3.72.2.4.99012  <br> 2.16.840.1.113883.3.72.2.4.99013 <br> 2.16.840.1.113883.3.72.2.4.99014  </td>
    <td>(PRE-LOADED)</td>
    <td></td>
  </tr>
  
   <tr>
    <td>IHE Bed</td>
    <td>23Admit <br> 24Messag <br> 25Arriv <br> 25Messag <br>25Depart <br> 25Cancel </td>
    <td>HL7 v2.5 <br> IG ?.?<br> ??/????</td>
    <td>2.16.840.1.113883.3.72.2.5.99001 <br> 2.16.840.1.113883.3.72.2.5.99002  <br> 2.16.840.1.113883.3.72.2.5.99003  <br> 22.16.840.1.113883.3.72.2.5.99004  <br> 22.16.840.1.113883.3.72.2.5.99005 <br> 2.16.840.1.113883.3.72.2.5.99006 </td>
    <td>(PRE-LOADED)</td>
    <td></td>
  </tr>
  	
  	<tr>    
  	<td>ELR</td>
    <td>Profile</td>
    <td>HL7 v2.5.1 <br> IG 1.0 <br> 2/2010</td>
    <td>2.16.840.1.113883.9.11</td>
    <td>IG_PHINVADS_ELRVocab <br> MergedTables_ELRVocab <br> RestrictedTables_ELRVocab <br> NoCheckTables_ELRVocab <br> Reduced_HL7tablesV2.5.1</td>
    <td> 2.16.840.1.113883.3.72.4.2.99015  2.16.840.1.113883.3.72.4.2.99016  2.16.840.1.113883.3.72.4.2.99017  2.16.840.1.113883.3.72.4.2.99018  2.16.840.1.113883.3.72.4.2.99019</td>
    </tr>

  	</table>
  
  </table>
  

<center>
<i>
 IZ = Immunization Information System Reporting &nbsp; LRI = Laboratory Results Interface &nbsp;SS = Syndromic Surveillance Reporting &nbsp; ELR = Electronic Laboratory Reporting &nbsp; VR = Vital Records
</i>
 </center>


<h2>Support Files</h2>

<table id="extern_resources/supportFiles">
  <tr>
    <th>Name</th>
    <th>Description</th>
    <th>Source</th>
  </tr>
  
  
  <tr>
    <td rowspan="8">HL7 V2 Conformance Profile</td>

  
    <tr>
    <td>a schema for HL7 Conformance Profile V2.NIST (proposed V2.9)</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileSchema_v29.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  
    <tr>
    <td>a schema for HL7 Conformance Profile V2.8</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileSchema_v28.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  
   <tr>
    <td>a schema for HL7 Conformance Profile V2.7.1</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileSchema_v271.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  
    <tr>
    <td>a schema for HL7 Conformance Profile V2.7</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileSchema_v27.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  
    <tr>
    <td>a schema for HL7 Conformance Profile V2.5</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileValidationSchema_HL7v2.5.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  
  <tr>
    <td>a schema for HL7 Conformance Profile V2.4</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileValidationSchema_HL7v2.4.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr>
  
      <td>a schema for HL7 Conformance Profile V2.3.1</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileValidationSchema_HL7v2.3.1.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr>
  
 
  
<!--   <tr>
    <td>a schema for HL7 Conformance Profile Structure Validation</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/ProfileStructureValidationBase.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr> -->
  
  
  
  <tr>
    <td>Table Library</td>
    <td>a schema for HL7 table definitions</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/Tables.xsd"><img src="extern_resources/images/get.png"/></a></td>      
  </tr>
  <tr>
    <td>Validation Context</td>
    <td>a schema describing how the validation context may be specified</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/HL7V2MessageValidationContext.xsd"><img src="extern_resources/images/get.png"/></a></td>   
  </tr>
  
  <tr>
    <td rowspan="4">xmlResults</td>
    <td>a schema reporting the result of the HL7 V2 message validation</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/hl7v2Report.xsd"><img src="extern_resources/images/get.png"/></a></td>   
  </tr>
  <tr>
    <td>a schema representing the HL7 V2 header implementation of the xmlResults</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/hl7v2ReportHeader.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr>
  <tr>
    <td>a schema representing the generic header of the xmlResults</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/reportHeaderGeneric.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr>
  <tr>
    <td>a schema representing the NIST part of the xmlResults</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/Hl7v2ValidationReport.xsd"><img src="extern_resources/images/get.png"/></a></td>
  </tr>
  <tr>
    <td>xmlResourceList</td>
    <td>a schema providing descriptive information about the available validation
    resources</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/xmlResourceList.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
  <tr>
    <td>xmlLoadResource</td>
    <td>a schema indicating the results from the loadResource() invocation</td>
    <td class="getLogo"><a href="extern_resources/supportFiles/xmlLoadResource.xsd"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
    <tr>
    <td>xmlV2ReportToHTML</td>
    <td>a style sheet for converting the validation results (xmlResults) from XML to HTML </td>
    <td class="getLogo"><a href="extern_resources/supportFiles/xmlv2reportTohtml.xsl"><img src="extern_resources/images/get.png"/></a></td>  
  </tr>
</table>


</div>
<br/>
<br/>
<br/> 


<br /><br />
</body>
