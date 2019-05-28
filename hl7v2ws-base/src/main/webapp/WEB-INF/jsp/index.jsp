<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>HL7 Web Services @ NIST</title>
		<meta name="Author" content="NIST" />
 		<link rel="stylesheet" type="text/css" href="css/main.css" media="screen" /> 
    	<link rel="stylesheet" type="text/css" href="css/custom-theme/jquery-ui-1.8.11.custom.css" media="screen" /> 
   		<style type="text/css">
  			ul.ui-tabs-nav { font-size: 0.8em; }
  		</style>	
   	<!--	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
  		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script> -->   
  		
  		<script src="js/jquery-1.5.1.min.js"></script>
  	    <script src="js/jquery-ui-1.8.11.custom.min.js"></script>
  	    <script src="js/hl7ws.js"></script>
  	    <script language="JavaScript" type="text/javascript">
  	    
  	  
  	  
 	   $(function(){ 
 		  $('div#navTab').tabs({			  
 		  }); 
 		  
  			// refind the last value 
		//	var value = location.hash.substring(1);
		//	if (value == "") {
	   	//		value = "home.htm";
       	//	} else {
       	//		value = unescape(value);
        //		setActive(value);
        //	} 
		    // var appState = new ApplicationState(value, "output", "dataOutput", value);
			//dojo.back.setInitialState(appState);
			//dijit.byId("content").setHref(value);  
			// $("#content").attr("href", value);  
			//$("#content").load(value);	 
		    //	$.get(
		    //			value,
 		//			function(response) {
		//				$('#content').html(response);
 		//			}
		//	); 
		//	loadContent(value);
			
 	  });  
 	   
 	  function goNav(value) {
			//setActive(value);
			//loadContent(value);
 	  }
 	  
 	  function setActive(id) {
 			$("a[id='documentation.htm']").addClass("jslink");
 			$("a[id='clients.htm']").addClass("jslink");
 			$("a[id='contact.htm']").addClass("jslink");
 			$("a#services").addClass("jslink"); 
 			if (id == "documentation.htm" || id == "clients.htm" || id == "contact.htm" || id == "services") {		
 				$("a").removeClass("current jslink");
 				$("a[id='"+ id + "']").addClass("current jslink");
 			}    			
		}
		</script>
	</head>

	<body>
		<script type="text/javascript"></script>
		<div id="wrapper" class="clearfix">
			<div class="logo">
				<a class="logolink" href="." title="Home">
					<div id="hl7top">
						<span id="hl7">HL7</span><span id="hl7_spec">WS</span>
					</div>
				</a>
				<div id="hl7bot">
					<span>NIST HL7 V2 Web Services</span>
				</div>
			</div>
            <ul id="subNav">
				
			</ul>
			<div id="navTab">
 			<ul>
 				<li><a title="home" href="home.htm">Home</a>
				<li><a title="services" href="services">Services</a>
				</li>
				<li><a title="documentation
				" href="documentation.htm">Documentation</a></li>
			<!-- 	<li><a href="clients.htm">Example Clients</a></li>  -->
        		<li><a title="contact" href="contact.htm">Contact</a></li>
        		<li><a title="disclaimer" href="disclaimer.htm">Disclaimer</a></li>
				<li style="margin-left:320px; font-weight:bold"><a onclick="window.open('http://hl7v2tools.nist.gov')">HL7 V2 TOOLS HOME</a></li>
			</ul>
			<div id="footer">
					<a href="http://www.nist.gov" target="_blank"><img src="images/logo_nist.gif" alt="NIST Logo" /></a>
					<a href="http://www.itl.nist.gov/" target="_blank"><img src="images/itl-logo.jpg" alt="NIST ITL Logo" /></a>
				<div style="text-align: right;">
					<p>Date Created: 9-24-13 | Date Updated: 
					</p>
					<p>
						<a href="http://www.nist.gov/public_affairs/disclaim.cfm" target="_blank" title="View Disclaimer">Disclaimer</a> | 
						<a href="mailto:rochb[at]nist[dot]gov" title="Email Website Administrator: rochb">Email Website Administrator</a> | 
						<a href="http://www.nist.gov/public_affairs/privacy.htm" target="_blank" title="View Privacy Policy">Privacy/Policy</a>
					</p>
			</div> 
			
 			<br/> 
			</div>

</div>
			 
	</body>
</html>
