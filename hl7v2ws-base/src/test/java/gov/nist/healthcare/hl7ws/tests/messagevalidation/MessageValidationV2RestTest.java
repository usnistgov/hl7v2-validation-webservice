package gov.nist.healthcare.hl7ws.tests.messagevalidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.cxf.helpers.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class MessageValidationV2RestTest {

	private final String VALID_OID_TEST = "1.3.6.1.4.12559.11.1.1.1";
	private static Server server;
	private HttpClient httpClient;
	private final String VALIDATE_URI = "validate/";
	private final String LOAD_RESOURCE_URI = "loadResource/";
	private final String GET_SERVICE_STATUS_URI = "getServiceStatus/";
	private static String BASE_URL;

	@BeforeClass
	public static void startServer() throws Throwable {
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
		int actualPort = server.getConnectors()[0].getLocalPort();
		BASE_URL = "http://localhost:" + actualPort
				+ "/services/rest/MessageValidationV2/";
	}

	@Before
	public void init() {
		httpClient = new HttpClient();
	}

	@Test
	public void testValidate() throws IOException {
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
		// Generate PostMethod
		PostMethod httpMethod = getValidatePostMethod(message, VALID_OID_TEST,
				xmlConfig, validationContext);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
	}

	@Test
	public void testValidateWithoutMessage() throws IOException {
		// get a valid xml config
		InputStream configStream = MessageValidationV2RestTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2RestTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String validationContext = IOUtils.toString(contextStream);
		PostMethod httpMethod = getValidatePostMethod(null, VALID_OID_TEST,
				xmlConfig, validationContext);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		String response = httpMethod.getResponseBodyAsString();
		assertEquals(HttpStatus.SC_OK, statusCode);
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
		// Generate PostMethod
		PostMethod httpMethod = getValidatePostMethod(message, null, xmlConfig,
				validationContext);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response
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

		// Generate PostMethod
		PostMethod httpMethod = getValidatePostMethod(message, VALID_OID_TEST,
				null, validationContext);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response
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

		// Generate PostMethod
		PostMethod httpMethod = getValidatePostMethod(message, VALID_OID_TEST,
				xmlConfig, null);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response
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
		// Generate PostMethod
		PostMethod httpMethod = getValidatePostMethod(message, VALID_OID_TEST,
				xmlConfig, validationContext);
		// validate
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response
				.contains("<mes:ValidationStatus>Incomplete</mes:ValidationStatus>")
				&& response.contains("Invalid message: Cannot be parsed"));

	}

	@Test
	public void testGetServerStatus() throws IOException {
		GetMethod getMethod = new GetMethod(BASE_URL + GET_SERVICE_STATUS_URI);
		// call getServiceStatus
		int statusCode = httpClient.executeMethod(getMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		assertNotNull(getMethod.getResponseBodyAsString());
	}

	@Test
	public void testLoadResourceWithoutOID() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		PostMethod httpMethod = getLoadResourcePostMethod(resource, null,
				"PROFILE");
		// http call to loadResource
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		assertTrue(httpMethod.getResponseBodyAsString().contains(
				"<status>true</status>"));

	}

	@Test
	public void testLoadResourceWithUnknownType() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		PostMethod httpMethod = getLoadResourcePostMethod(resource,
				"1.3.6.1.4.12559.11.1.1.1000", "UNKNOWN_TYPE");
		// http call to loadResource
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response.contains("<status>false</status>")
				&& response.contains("Invalid type (can be \"PROFILE\" "
						+ "or \"TABLE\"." + " )"));

	}

	@Test
	public void testLoadEmptyResource() throws IOException {
		// create Post method
		PostMethod httpMethod = getLoadResourcePostMethod(null,
				"1.3.6.1.4.12559.11.1.1.1000", "PROFILE");
		// Http call to loadResource
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response.contains("<status>false</status>")
				&& response.contains("Invalid Profile."));
	}

	@Test
	public void testLoadExistingResource() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		// create Post method
		PostMethod httpMethod = getLoadResourcePostMethod(resource,
				VALID_OID_TEST, "PROFILE");
		// Http call to loadResource
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		assertTrue(response.contains("<status>false</status>")
				&& response.contains("Resource already in the registry."));
	}

	/**
	 * create a PostMethod for the server validate method
	 * 
	 * @param messageStr
	 * @param oid
	 * @param xmlConfig
	 * @param xmlValidationContext
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private PostMethod getValidatePostMethod(String messageStr, String oid,
			String xmlConfig, String xmlValidationContext)
			throws HttpException, IOException {
		// validate
		PostMethod postMethod = new PostMethod(BASE_URL + VALIDATE_URI);
		NameValuePair[] params = getValidationQueryParameters(messageStr, oid,
				xmlConfig, xmlValidationContext);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	private PostMethod getLoadResourcePostMethod(String resource, String oid,
			String type) throws HttpException, IOException {
		// validate
		PostMethod postMethod = new PostMethod(BASE_URL + LOAD_RESOURCE_URI);
		NameValuePair[] params = getLoadResourceQueryParameters(resource, oid,
				type);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	@AfterClass
	public static void stopWebapp() throws Exception {
		server.stop();
	}

	/**
	 * @param messageStr
	 * @param oid
	 * @param xmlConfig
	 * @param xmlValidationContext
	 * @return an array of paramters name/value
	 */
	private NameValuePair[] getValidationQueryParameters(String messageStr,
			String oid, String xmlConfig, String xmlValidationContext) {
		return new NameValuePair[] { new NameValuePair("message", messageStr),
				new NameValuePair("oid", oid),
				new NameValuePair("xmlConfig", xmlConfig),
				new NameValuePair("xmlValidationContext", xmlValidationContext) };
	}

	private NameValuePair[] getLoadResourceQueryParameters(String resource,
			String oid, String type) {
		return new NameValuePair[] { new NameValuePair("resource", resource),
				new NameValuePair("oid", oid), new NameValuePair("type", type) };
	}
}
