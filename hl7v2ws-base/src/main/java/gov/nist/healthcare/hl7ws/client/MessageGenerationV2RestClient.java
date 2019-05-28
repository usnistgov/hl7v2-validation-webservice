package gov.nist.healthcare.hl7ws.client;

import gov.nist.healthcare.hl7ws.messagegeneration.MessageGenerationV2Interface;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class MessageGenerationV2RestClient implements
		MessageGenerationV2Interface {
	private final String serviceUrl;

	/*
	 * serviceUrl = "http://localhost:8080/services/rest/MessageValidationV2/";
	 */

	public MessageGenerationV2RestClient(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String generate(String oid, String xmlConfig,
			String xmlGenerationContext) {
		String result = null;
		try {

			HttpClient httpClient = new HttpClient();
			// Generate PostMethod
			PostMethod httpMethod = getGeneratePostMethod(oid, xmlConfig,
					xmlGenerationContext);
			// execute the generate method
			httpClient.executeMethod(httpMethod);
			// get the http response body content
			result = httpMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getServiceStatus() {
		String result = null;
		try {
			HttpClient httpClient = new HttpClient();
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
			HttpClient httpClient = new HttpClient();
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
		PostMethod postMethod = new PostMethod(serviceUrl + "generate/");
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
			String oid, String xmlConfig, String xmlGenerationContext)
			throws HttpException, IOException {
		// validate
		PostMethod postMethod = new PostMethod(serviceUrl
				+ "generateFromTemplate/");
		NameValuePair[] params = getGenerateFromTemplateQueryParameters(
				template, oid, xmlConfig, xmlGenerationContext);
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

	/**
	 * 
	 * @param template
	 * @param xmlGenerationContext
	 * @return
	 */
	private NameValuePair[] getGenerateFromTemplateQueryParameters(
			String template, String oid, String xmlConfig,
			String xmlGenerationContext) {
		return new NameValuePair[] { new NameValuePair("template", template),
				new NameValuePair("oid", oid),
				new NameValuePair("xmlConfig", xmlConfig),
				new NameValuePair("xmlGenerationContext", xmlGenerationContext) };
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

	public String generateFromTemplate(String message, String oid,
			String xmlConfig, String xmlGenerationContext) {
		String result = null;

		try {
			HttpClient httpClient = new HttpClient();
			// Generate PostMethod
			PostMethod httpMethod = getGenerateFromTemplatePostMethod(message,
					oid, xmlConfig, xmlGenerationContext);
			// execute the generateFromTemplate method
			httpClient.executeMethod(httpMethod);
			// get the http response body content
			result = httpMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
