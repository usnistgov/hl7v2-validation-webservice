/*
 * NIST HL7 WS
 * Registry.java Oct 1, 2008
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.registry;

import gov.nist.healthcare.core.MalformedProfileException;
import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.hl7Ws.resourcelist.ResourceType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType.Resource;
import gov.nist.ncsl.sst116.axis.services.HCRRQueryService.HCRRQueryServiceSoapBindingStub;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Element;

/**
 * Local Registry hosted on xreg2.
 * 
 * @author Roch Bertucat (NIST)
 */
public class Registry {

	private static final String PROFILE_URL = "http://xreg2.nist.gov:8080/robistry/registry/http?interface=QueryManager&method=getRepositoryItem&param-id=urn:oid:";
	private static final String OMAR_URL = "http://xreg2.nist.gov:8080/robistry/";
	private static final String HCRRWS_URL = "http://xreg2.nist.gov:8080/axis/services/HCRRQueryService";
	private final HCRRQueryServiceSoapBindingStub stub;

	public Registry() throws AxisFault, MalformedURLException {
		stub = new HCRRQueryServiceSoapBindingStub(new URL(HCRRWS_URL), null);
	}

	/**
	 * Add the resources from the registry to the XmlResourceList.
	 * 
	 * @param xmlResourceListType
	 *            the xmlResourceList
	 * @throws MalformedURLException
	 * @throws RemoteException
	 */
	public void addResourceListTo(XmlResourceListType xmlResourceListType)
			throws RemoteException {

		List<Element> profiles = Arrays.asList(stub.getAllProfiles());
		for (Element curElement : profiles) {
			Resource resource = xmlResourceListType.addNewResource();
			resource.setResourceID(getChildValue(curElement, "Oid"));
			resource.setName(getChildValue(curElement, "Name"));
			resource.setVersion(getChildValue(curElement, "Version"));
			resource.setOrganization(getChildValue(curElement, "Organization"));
			resource.setDescription(getChildValue(curElement, "Description"));
			resource.setHL7Version(getChildValue(curElement, "Hl7Version"));
			resource.setResourceType(ResourceType.PROFILE);
		}

	}

	/**
	 * Get the value of the field from the curElement children.
	 * 
	 * @param curElement
	 *            the curElement
	 * @param field
	 *            the field
	 * @return the value of the field
	 */
	private String getChildValue(Element curElement, String field) {
		return curElement.getElementsByTagName(field).item(0).getFirstChild()
				.getNodeValue();
	}

	/**
	 * Get the profile associated with the OID.
	 * 
	 * @param OID
	 *            the OID
	 * @return the profile
	 * @throws MalformedProfileException
	 *             if the profile cannot be instantiated
	 * @throws FileNotFoundException
	 *             if the file is not found
	 * @throws MalformedURLException
	 */
	public Profile getProfile(String oid) throws MalformedProfileException,
			FileNotFoundException, MalformedURLException {
		String file = getFile(oid);
		return new Profile(oid, file);
	}

	/**
	 * Get the table associated with the OID.
	 * 
	 * @param oid
	 *            the OID
	 * @return the table
	 * @throws FileNotFoundException
	 *             if the file cannot be found
	 * @throws XmlException
	 *             if errors occur while parsing
	 * @throws MalformedURLException
	 *             if an error on the url occurs
	 */
	public TableLibraryDocument getTable(String oid)
			throws FileNotFoundException, XmlException, MalformedURLException {
		String file = getFile(oid);

		XmlOptions options = new XmlOptions();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("", "http://www.nist.gov/healthcare/data");
		options.setLoadSubstituteNamespaces(map);
		return TableLibraryDocument.Factory.parse(file, options);

	}

	/**
	 * Get the file associated with the OID.
	 * 
	 * @param oid
	 *            the OID
	 * @return the string representation of the file
	 * @throws FileNotFoundException
	 *             if the file is not found
	 * @throws MalformedURLException
	 */
	public String getFile(String oid) throws FileNotFoundException,
			MalformedURLException {
		InputStream in = null;
		ByteArrayOutputStream bos = null;
		OutputStream out = null;

		try {
			URL url = new URL(PROFILE_URL + oid);
			checkURL(new URL(OMAR_URL));
			in = url.openStream();

			bos = new ByteArrayOutputStream();
			out = new BufferedOutputStream(bos);
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				throw new FileNotFoundException("Invalid OID: \"" + oid + "\"");
			} else if (e instanceof MalformedURLException) {
				throw new MalformedURLException(
						"Internal error: MalformedURLException in Registry.");
			} else if (e instanceof IOException) {
				throw new FileNotFoundException("Invalid OID: \"" + oid + "\"");
			}
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return bos.toString();
	}

	private void checkURL(URL url) throws MalformedURLException, IOException {
		HttpURLConnection.setFollowRedirects(false);
		// note : you may also need
		// HttpURLConnection.setInstanceFollowRedirects(false)
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("HEAD");
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new MalformedURLException();
		}
	}
}
