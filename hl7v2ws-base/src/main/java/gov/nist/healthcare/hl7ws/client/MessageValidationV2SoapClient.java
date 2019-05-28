package gov.nist.healthcare.hl7ws.client;

import gov.nist.healthcare.hl7ws.messagevalidation.MessageValidationV2Interface;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class MessageValidationV2SoapClient implements
		MessageValidationV2Interface {
	private final MessageValidationV2Interface client;

	public MessageValidationV2SoapClient(String endpoint) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(MessageValidationV2Interface.class);
		factory.setAddress(endpoint);
		client = (MessageValidationV2Interface) factory.create();
	}

	public String validate(String message, String oid, String xmlConfig,
			String xmlValidationContext) {
		return client.validate(message, oid, xmlConfig, xmlValidationContext);
	}

	public String getServiceStatus() {
		return client.getServiceStatus();
	}

	public String loadResource(String resource, String oid, String type) {
		return client.loadResource(resource, oid, type);
	}

	@Override
	public String validateDQA(String message, String profileOID,
			String tableOIDList, String xmlValidationContext, String DQAOptions)
			throws Exception {
		// TODO Auto-generated method stub
		return client.validateDQA(message, profileOID, tableOIDList, xmlValidationContext, DQAOptions);
	}

	@Override
	public String validateWithResources(String message, String profile,
			String constraints, String valueSetLibrary, String message_id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
