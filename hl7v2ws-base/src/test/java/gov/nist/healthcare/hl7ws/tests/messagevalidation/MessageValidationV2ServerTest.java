package gov.nist.healthcare.hl7ws.tests.messagevalidation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nist.healthcare.hl7ws.messagevalidation.MessageValidationV2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context-test-server.xml")
public class MessageValidationV2ServerTest {

	private final String VALID_OID_TEST = "1.3.6.1.4.12559.11.1.1.1";
	@Autowired
	@Qualifier("messageValidationV2")
	private MessageValidationV2 mv;

	@Before
	public void init() {
	}

	@Test
	public void testValidateWithoutMessage() throws IOException {
		// get a valid xml config
		InputStream configStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid validation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/xmlvalidationcontext-test-valid.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String validationContext = IOUtils.toString(contextStream);
		// validate
		String report = mv.validate(null, VALID_OID_TEST, xmlConfig,
				validationContext);
		assertTrue(report.contains("Message is required")
				&& report
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
		String report = mv
				.validate(message, null, xmlConfig, validationContext);
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
		String report = mv.validate(message, VALID_OID_TEST, null,
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
		String report = mv.validate(message, VALID_OID_TEST, xmlConfig, null);
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
		String report = mv.validate(message, VALID_OID_TEST, xmlConfig,
				validationContext);
		assertTrue(report
				.contains("<mes:ValidationStatus>Incomplete</mes:ValidationStatus>")
				&& report.contains("Invalid message: Cannot be parsed"));
	}

	@Test
	public void testGetServerStatus() throws IOException {
		// call getServiceStatus
		String xml = mv.getServiceStatus();
		assertNotNull(xml);
	}

	@Test
	public void testLoadResourceWithoutOID() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		// this resource oid does not exist in the registry
		String result = mv.loadResource(resource, null, "PROFILE");
		assertTrue(result.contains("<status>true</status>"));
	}

	@Test
	public void testLoadResourceWithUnknownType() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		String result = mv.loadResource(resource,
				"1.3.6.1.4.12559.11.1.1.1000", "UNKNOWN_TYPE");
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Invalid type (can be \"PROFILE\" "
						+ "or \"TABLE\"." + " )"));
	}

	@Test
	public void testLoadEmptyResource() throws IOException {
		String type = "PROFILE";
		String result = mv.loadResource(null, "1.3.6.1.4.12559.11.1.1.1000",
				type);
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Invalid Profile."));
	}

	@Test
	public void testLoadExistingResource() throws IOException {
		// get the resource: profile
		InputStream profileStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/profile-test-valid.xml");
		String resource = IOUtils.toString(profileStream);
		String result = mv.loadResource(resource, VALID_OID_TEST, "PROFILE");
		assertTrue(result.contains("<status>false</status>")
				&& result.contains("Resource already in the registry."));
	}

	@After
	public void destroy() {

	}
}
