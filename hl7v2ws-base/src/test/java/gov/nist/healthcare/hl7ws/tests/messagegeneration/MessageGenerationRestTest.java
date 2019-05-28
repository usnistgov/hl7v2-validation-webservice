package gov.nist.healthcare.hl7ws.tests.messagegeneration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nist.healthcare.core.MalformedMessageException;
import gov.nist.healthcare.hl7ws.tests.messagevalidation.MessageValidationV2ServerTest;
import gov.nist.healthcare.hl7ws.utils.HL7MessageUtils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.cxf.helpers.IOUtils;
import org.apache.xmlbeans.XmlException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context-test-server.xml")
public class MessageGenerationRestTest {

	private final String VALID_OID_TEST = "1.3.6.1.4.12559.11.1.1.1";
	private static Server server;
	private HttpClient httpClient;
	private final String GENERATE_URI = "generate/";
	private final String GENERATE_FROM_TEMPLATE_URI = "generateFromTemplate/";
	private final String LOAD_RESOURCE_URI = "loadResource/";

	private static String BASE_URL;

	
	
	
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
	
	private NameValuePair[] getLoadResourceQueryParameters(String resource,
			String oid, String type) {
		return new NameValuePair[] { new NameValuePair("resource", resource),
				new NameValuePair("oid", oid), new NameValuePair("type", type) };
	}
	
	@Before
	public void init() {
		httpClient = new HttpClient();
	}

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
				+ "/services/rest/MessageGenerationV2/";
	}

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
System.out.println(response);
	}
	
	@Test
	public void testGenerate() throws IOException {
		
		
		testLoadExistingResource();
		// get a valid xml config
		InputStream configStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid generation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/generationcontext-test-er7-2.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String generationContext = IOUtils.toString(contextStream);
		// Generate PostMethod
		PostMethod httpMethod = getGeneratePostMethod(VALID_OID_TEST,xmlConfig,
				generationContext);
		// execute the generate method
		int statusCode = httpClient.executeMethod(httpMethod);
		assertEquals(HttpStatus.SC_OK, statusCode);
		String response = httpMethod.getResponseBodyAsString();
		System.out.println(response);
 	}
	
//	@Test
//	public void testGenerateWithoutOid() throws IOException {
//		// get a valid xml config
//		InputStream configStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/xmlconfig-test-valid.xml");
//		// get a valid generation context
//		InputStream contextStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/generationcontext-test-er7.xml");
//		String xmlConfig = IOUtils.toString(configStream);
//		String generationContext = IOUtils.toString(contextStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGeneratePostMethod(null, xmlConfig,
//				generationContext);
//		// execute the generate method
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("Invalid OID"));
//	}
//
//	@Test
//	public void testGenerateWithoutXmlConfig() throws IOException {
//		InputStream contextStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/generationcontext-test-er7.xml");
//		String generationContext = IOUtils.toString(contextStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGeneratePostMethod(VALID_OID_TEST, null,
//				generationContext);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("MSH"));
//	}
//
//	@Test
//	public void testGenerateWithoutGenerationContext() throws IOException {
//		// Generate PostMethod
//		PostMethod httpMethod = getGeneratePostMethod(VALID_OID_TEST, null,
//				null);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("The generation context is null"));
//	}
//
//	/**
//	 * generate from a null template. An exception should be thrown
//	 * 
//	 * @throws IOException
//	 */
//	@Test
//	public void testGenerateFromTemplateWithoutTemplate() throws IOException {
//		InputStream contextStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/generationcontext-test-er7.xml");
//		String generationContext = IOUtils.toString(contextStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGenerateFromTemplatePostMethod(null, null,
//				generationContext);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("The template is required"));
//
//	}
//
//	/**
//	 * generate from a template without a generation context. An exception
//	 * should be thrown
//	 * 
//	 * @throws IOException
//	 */
//
//	@Test
//	public void testGenerateFromTemplateWithoutGenerationContext()
//			throws IOException {
//		// get the template
//		InputStream messageStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/message-test-valid.er7");
//		String template = IOUtils.toString(messageStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGenerateFromTemplatePostMethod(template,
//				null, null);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("The generation context is required"));
//
//	}
//
//	/**
//	 * this method update existing values in the template
//	 * 
//	 * @throws XmlException
//	 * @throws MalformedMessageException
//	 */
//	@Test
//	public void testGenerateFromTemplateWithExistingValueInGenerationContext()
//			throws IOException, MalformedMessageException, XmlException {
//		// get the template
//		InputStream messageStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/template-valid.er7");
//		String template = IOUtils.toString(messageStream);
//		InputStream contextStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/generationcontext-test-er7.xml");
//		String generationContext = IOUtils.toString(contextStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGenerateFromTemplatePostMethod(template,
//				null, generationContext);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(response.contains("</MessageGenerated>"));
//	}
//
//	/**
//	 * generate from a template with an empty generation context. The message
//	 * should be generated successfully and the content should be the same as
//	 * the initial message. The format (XML, ER7) may be different depending on
//	 * the encoding the Generation Context
//	 * 
//	 * @throws IOException
//	 * @throws MalformedMessageException
//	 * @throws XmlException
//	 */
//	@Test
//	public void testGenerateFromTemplateWithEmptyGenerationContext()
//			throws IOException, MalformedMessageException, XmlException {
//		// get the template
//		InputStream messageStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/template-valid.er7");
//		String template = IOUtils.toString(messageStream);
//		InputStream contextStream = MessageValidationV2ServerTest.class
//				.getResourceAsStream("/generationcontext-test-empty-er7.xml");
//		String generationContext = IOUtils.toString(contextStream);
//		// Generate PostMethod
//		PostMethod httpMethod = getGenerateFromTemplatePostMethod(template,
//				null, generationContext);
//		// generate
//		int statusCode = httpClient.executeMethod(httpMethod);
//		assertEquals(HttpStatus.SC_OK, statusCode);
//		String response = httpMethod.getResponseBodyAsString();
//		assertTrue(HL7MessageUtils.checkUpdatedMessage(response,
//				generationContext));
//
//	}

	/**
	 * create a PostMethod for the server generate method
	 * 
	 * @param messageStr
	 * @param oid
	 * @param xmlConfig
	 * @param xmlValidationContext
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private PostMethod getGeneratePostMethod(String oid, String xmlConfig,
			String xmlGenerationContext) throws HttpException, IOException {
		// validate
		PostMethod postMethod = new PostMethod(BASE_URL + GENERATE_URI);
		NameValuePair[] params = getGenerationQueryParameters(oid, xmlConfig,
				xmlGenerationContext);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	/**
	 * 
	 * @param oid
	 * @param xmlConfig
	 * @param xmlGenerationContext
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private PostMethod getGenerateFromTemplatePostMethod(String template,
			String oid, String xmlGenerationContext) throws HttpException,
			IOException {
		// validate
		PostMethod postMethod = new PostMethod(BASE_URL
				+ GENERATE_FROM_TEMPLATE_URI);
		NameValuePair[] params = getGenerateFromTemplateQueryParameters(
				template, oid, xmlGenerationContext);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	/**
	 * @param messageStr
	 * @param oid
	 * @param xmlConfig
	 * @param xmlValidationContext
	 * @return an array of paramters name/value
	 */
	private NameValuePair[] getGenerationQueryParameters(String oid,
			String xmlConfig, String xmlGenerationContext) {
		return new NameValuePair[] { new NameValuePair("oid", oid),
				new NameValuePair("xmlConfig", xmlConfig),
				new NameValuePair("xmlGenerationContext", xmlGenerationContext) };
	}

	private NameValuePair[] getGenerateFromTemplateQueryParameters(
			String template, String oid, String xmlGenerationContext) {
		return new NameValuePair[] {
				new NameValuePair("template", template),
				new NameValuePair("xmlGenerationContext", xmlGenerationContext),
				new NameValuePair("oid", oid) };
	}

	@After
	public void destroy() {

	}

	@AfterClass
	public static void stopWebapp() throws Exception {
		server.stop();
	}
}
