package gov.nist.healthcare.hl7ws.tests.messagegeneration;

import static org.junit.Assert.assertTrue;
import gov.nist.healthcare.core.MalformedMessageException;
import gov.nist.healthcare.hl7ws.messagegeneration.MessageGenerationV2;
import gov.nist.healthcare.hl7ws.tests.messagevalidation.MessageValidationV2ServerTest;
import gov.nist.healthcare.hl7ws.utils.HL7MessageUtils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.xmlbeans.XmlException;
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
public class MessageGenerationServerTest {

	private final String VALID_OID_TEST = "1.3.6.1.4.12559.11.1.1.1";
	@Autowired
	@Qualifier("messageGenerationV2")
	private MessageGenerationV2 mg;

	@Before
	public void init() {
	}

	/**
	 * generate without an oid for the profile. an exception should be thrown
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGenerateWithoutOid() throws IOException {
		// get a valid xml config
		InputStream configStream = MessageGenerationServerTest.class
				.getResourceAsStream("/xmlconfig-test-valid.xml");
		// get a valid generation context
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/generationcontext-test-er7.xml");
		String xmlConfig = IOUtils.toString(configStream);
		String generationContext = IOUtils.toString(contextStream);
		String result = mg.generate(null, xmlConfig, generationContext);
		assertTrue(result.contains("Invalid OID"));
	}

	/**
	 * generate without the xml config. No exception should be thrown. The
	 * message will succesfully be generated
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGenerateWithoutXmlConfig() throws IOException {
		// get a valid generation context
		InputStream contextStream = MessageGenerationServerTest.class
				.getResourceAsStream("/generationcontext-test-er7.xml");
		String generationContext = IOUtils.toString(contextStream);
		String result = mg.generate(VALID_OID_TEST, null, generationContext);
		assertTrue(result.contains("MSH"));
	}

	/**
	 * generate without a generation context. an exception should be thrown
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGenerateWithoutGenerationContext() throws IOException {
		String result = mg.generate(VALID_OID_TEST, null, null);
		assertTrue(result.contains("The generation context is null"));
	}

	/**
	 * generate from a null template. An exception should be thrown
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGenerateFromTemplateWithoutTemplate() throws IOException {
		InputStream contextStream = MessageGenerationServerTest.class
				.getResourceAsStream("/generationcontext-test-er7.xml");
		String generationContext = IOUtils.toString(contextStream);
		String result = mg.generateFromTemplate(null, null, null,
				generationContext);
		assertTrue(result.contains("The template is required"));
	}

	/**
	 * generate from a template without a generation context. An exception
	 * should be thrown
	 * 
	 * @throws IOException
	 */

	@Test
	public void testGenerateFromTemplateWithoutGenerationContext()
			throws IOException {
		// get the template
		InputStream messageStream = MessageGenerationServerTest.class
				.getResourceAsStream("/message-test-valid.er7");
		String template = IOUtils.toString(messageStream);
		String result = mg.generateFromTemplate(template, null, null, null);
		assertTrue(result.contains("The generation context is required"));
	}

	/**
	 * this method update existing values in the template
	 * 
	 * @throws XmlException
	 * @throws MalformedMessageException
	 */
	@Test
	public void testGenerateFromTemplateWithExistingValueInGenerationContext()
			throws IOException, MalformedMessageException, XmlException {
		// get the template
		InputStream messageStream = MessageGenerationServerTest.class
				.getResourceAsStream("/template-valid.er7");
		String template = IOUtils.toString(messageStream);
		InputStream contextStream = MessageValidationV2ServerTest.class
				.getResourceAsStream("/generationcontext-test-er7.xml");
		String generationContext = IOUtils.toString(contextStream);
		String result = mg.generateFromTemplate(template, null, null,
				generationContext);
		assertTrue(result.contains("MSH"));
	}

	/**
	 * generate from a template with an empty generation context. The message
	 * should be generated successfully and the content should be the same as
	 * the initial message. The format (XML, ER7) may be different depending on
	 * the encoding the Generation Context
	 * 
	 * @throws IOException
	 * @throws MalformedMessageException
	 * @throws XmlException
	 */
	@Test
	public void testGenerateFromTemplateWithEmptyGenerationContext()
			throws IOException, MalformedMessageException, XmlException {
		// get the template
		InputStream messageStream = MessageGenerationServerTest.class
				.getResourceAsStream("/template-valid.er7");
		String template = IOUtils.toString(messageStream);
		InputStream contextStream = MessageGenerationServerTest.class
				.getResourceAsStream("/generationcontext-test-empty-er7.xml");
		String generationContext = IOUtils.toString(contextStream);
		String result = mg.generateFromTemplate(template, null, null,
				generationContext);
		assertTrue(HL7MessageUtils.checkUpdatedMessage(result,
				generationContext));
	}

	@After
	public void destroy() {

	}

}
