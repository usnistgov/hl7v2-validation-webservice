package gov.nist.healthcare.hl7ws.client;

import gov.nist.healthcare.hl7ws.messagevalidation.MessageValidationV2Interface;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class MessageValidationV2RestClient implements
		MessageValidationV2Interface {

	private final HttpClient httpClient;
	private final String serviceUrl;

	/*
	 * serviceUrl = "http://localhost:8080/services/rest/MessageValidationV2/";
	 */

	public MessageValidationV2RestClient(String serviceUrl) {
		httpClient = new HttpClient();
		this.serviceUrl = serviceUrl;
	}

	public String validate(String message, String oid, String xmlConfig,
			String xmlValidationContext) {
		String result = null;
		try {
			// Generate PostMethod
			PostMethod httpMethod = getValidatePostMethod(message, oid,
					xmlConfig, xmlValidationContext);
			// execute validate method
			httpClient.executeMethod(httpMethod);
			// get the http response body
			result = httpMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// validate
		return result;
	}

	public String getServiceStatus() {
		String result = null;
		try {
			// create getServiceStatus http method
			GetMethod getMethod = new GetMethod(serviceUrl
					+ "getServiceStatus/");
			// execute the method
			httpClient.executeMethod(getMethod);
			// get the http response body
			result = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String loadResource(String resource, String oid, String type) {
		String result = null;
		try {
			// create the post method
			PostMethod httpMethod = getLoadResourcePostMethod(resource, oid,
					type);
			// execute the loadResource method
			httpClient.executeMethod(httpMethod);
			// get the http response body
			result = httpMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * get the http parameter for the validate method
	 * 
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

	/**
	 * get the http parameters for the LoadResource method
	 * 
	 * @param resource
	 * @param oid
	 * @param type
	 * @return
	 */
	private NameValuePair[] getLoadResourceQueryParameters(String resource,
			String oid, String type) {
		return new NameValuePair[] { new NameValuePair("resource", resource),
				new NameValuePair("oid", oid), new NameValuePair("type", type) };
	}

	/**
	 * create a http post method for validate method
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
		PostMethod postMethod = new PostMethod(serviceUrl + "validate/");
		NameValuePair[] params = getValidationQueryParameters(messageStr, oid,
				xmlConfig, xmlValidationContext);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	/**
	 * create the http post method for the loadResource method
	 * 
	 * @param resource
	 * @param oid
	 * @param type
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	private PostMethod getLoadResourcePostMethod(String resource, String oid,
			String type) throws HttpException, IOException {
		// validate
		PostMethod postMethod = new PostMethod(serviceUrl + "loadResource/");
		NameValuePair[] params = getLoadResourceQueryParameters(resource, oid,
				type);
		postMethod.addParameters(params);
		// postMethod.setQueryString(params);
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		// postMethod.setRequestBody(params);
		return postMethod;
	}

	@Override
	public String validateDQA(String message, String profileOID,
			String tableOIDList, String xmlValidationContext, String DQAOptions)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateWithResources(String message, String profile,
			String constraints, String valueSetLibrary, String message_id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
