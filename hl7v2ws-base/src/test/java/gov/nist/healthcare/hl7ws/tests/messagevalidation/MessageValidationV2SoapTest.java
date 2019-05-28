package gov.nist.healthcare.hl7ws.tests.messagevalidation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nist.healthcare.hl7ws.messagevalidation.MessageValidationV2Interface;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class MessageValidationV2SoapTest {

	private final String VALID_OID_TEST = "1.3.6.1.4.12559.11.1.1.1";
	private static Server server;
	private static MessageValidationV2Interface client;

	@BeforeClass
	public static void startServer() throws Throwable {
		// create the jetty server
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.addConnector(connector);
		WebAppContext root = new WebAppContext();
		root.setContextPath("/");
		root.setWar("src/main/webapp");
		server.setHandler(root);
		server.start();
		server.setStopAtShutdown(true);
		server.setSendServerVersion(true);
		// create the service client
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		factory.setServiceClass(MessageValidationV2Interface.class);
		factory.setAddress("http://localhost:8080/services/soap/MessageValidationV2");
		client = (MessageValidationV2Interface) factory.create();
	}

	@Test
	public void testValidateWithoutMessage() throws IOException {
		// get a valid xml config
		InputStream configStream = MessageValidationV2SoapTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2SoapTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String validationContext = IOUtils.toString(contextStream);
		// validate
		String response = client.validate(null, VALID_OID_TEST, xmlConfig,
				validationContext);
		assertTrue(response.contains("Message is required")
				&& response
						.contains("<mes:ResultOfTest>Failed</mes:ResultOfTest>"));
	}

	@Test
	public void testValidateWithoutOID() throws IOException {
		// get the message
		InputStream messageStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/message-test-valid.er7");
		// get a valid xml config
		InputStream configStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String validationContext = IOUtils.toString(contextStream);
		String message = IOUtils.toString(messageStream);
		// validate
		String report = client.validate(message, null, xmlConfig,
				validationContext);
		assertTrue(report
				.contains("<mes:ResultOfTest>Passed</mes:ResultOfTest>"));
	}

	@Test
	public void testValidateWitoutXmlConfig() throws IOException {
		// get the message
		InputStream messageStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/message-test-valid.er7");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String validationContext = IOUtils.toString(contextStream);
		String message = IOUtils.toString(messageStream);
		// validate
		String report = client.validate(message, VALID_OID_TEST, null,
				validationContext);
		assertTrue(report
				.contains("<mes:ValidationStatus>Complete</mes:ValidationStatus>"));
	}

	@Test
	public void testValidateWitoutXmlValidationContext() throws IOException {
		// get the message
		InputStream messageStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/message-test-valid.er7");
		String message = IOUtils.toString(messageStream);
		// get a valid xml config
		InputStream configStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		// validate
		String report = client.validate(message, VALID_OID_TEST, xmlConfig,
				null);
		assertTrue(report
				.contains("<mes:ValidationStatus>Complete</mes:ValidationStatus>"));
	}

	@Test
	public void testValidateMalformedEr7Message() throws IOException {
		// get the message
		InputStream messageStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/message-test-malformed.er7");
		String message = IOUtils.toString(messageStream);
		// get a valid xml config
		InputStream configStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String validationContext = IOUtils.toString(contextStream);
		// validate
		String report = client.validate(message, VALID_OID_TEST, xmlConfig,
				validationContext);
		assertTrue(report
				.contains("<mes:ValidationStatus>Incomplete</mes:ValidationStatus>")
				&& report.contains("Invalid message: Cannot be parsed"));
	}

	@Test
	public void testGetServerStatus() throws IOException {
		// call getServiceStatus
		String xml = client.getServiceStatus();
		assertNotNull(xml);
	}

	@Test
	public void testLoadResourceWithoutOID() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		// this resource oid does not exist in the registry
		String result = client.loadResource(resource, null, "PROFILE");
		assertTrue(result.contains("<status>true</status>"));
	}

	@Test
	public void testLoadResourceWithUnknownType() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		String result = client.loadResource(resource,
				"1.3.6.1.4.12559.11.1.1.1000", "UNKNOWN_TYPE");
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Invalid type (can be \"PROFILE\" "
						+ "or \"TABLE\"." + " )"));
	}

	@Test
	public void testLoadEmptyResource() throws IOException {
		String type = "PROFILE";
		String result = client.loadResource(null,
				"1.3.6.1.4.12559.11.1.1.1000", type);
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Invalid Profile."));
	}

	@Test
	public void testLoadExistingResource() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		String result = client
				.loadResource(resource, VALID_OID_TEST, "PROFILE");
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Resource already in the registry."));
	}

	@After
	public void destroy() {
	}

	@AfterClass
	public static void stopWebapp() throws Exception {
		server.stop();
	}
}
