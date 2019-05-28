/*
 * NIST HL7 WS
 * TempRegistry.java Feb 13, 2007
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.registry;

import gov.nist.healthcare.core.MalformedProfileException;
import gov.nist.healthcare.core.profile.Profile;
import gov.nist.healthcare.data.TableLibraryDocument;
import gov.nist.healthcare.hl7Ws.resource.XmlLoadResourceDocument;
import gov.nist.healthcare.hl7Ws.resource.XmlLoadResourceType;
import gov.nist.healthcare.hl7Ws.resourcelist.ResourceType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType;
import gov.nist.healthcare.hl7Ws.resourcelist.XmlResourceListType.Resource;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.axis.AxisFault;
import org.apache.xmlbeans.XmlException;

/**
 * @author Roch Bertucat (NIST)
 */
public class TempRegistry {
	private final Map<String, TableLibraryDocument> tempTable = new HashMap<String, TableLibraryDocument>();
	private final Map<String, Profile> tempProfile = new HashMap<String, Profile>();
	private String profileOid;
	private String tableOid;
	private Map<String, String> profileMapping;

	public void setProfileOid(String profileOid) {
		this.profileOid = profileOid;
	}

	public void setTableOid(String tableOid) {
		this.tableOid = tableOid;
	}

	public void setProfileMapping(Map<String, String> profileMapping) {
		this.profileMapping = profileMapping;
	}

	public TempRegistry() {
	}

	public void put(String oid, TableLibraryDocument table) {
		tempTable.put(oid, table);
	}

	public TableLibraryDocument getTable(String oid) {
		return tempTable.get(oid);
	}

	public void put(String oid, Profile profile) {
		tempProfile.put(oid, profile);
	}

	public Profile getProfile(String oid) {
		return tempProfile.get(oid);
	}

	public void addResourceListTo(XmlResourceListType xmlResourceListType) throws java.rmi.RemoteException {
		for (Iterator<String> it = tempProfile.keySet().iterator(); it
				.hasNext();) {
			String oid = it.next();
			Profile curProfile = tempProfile.get(oid);
			Resource resource = xmlResourceListType.addNewResource();
			resource.setResourceID(oid);
			resource.setName(curProfile.getName());
			resource.setOrganization(curProfile.getOrganization());
			resource.setVersion(curProfile.getVersion());
			resource.setHL7Version(curProfile.getHl7VersionAsString());
			resource.setResourceType(ResourceType.PROFILE);
		}

		for (Iterator<String> it = tempTable.keySet().iterator(); it.hasNext();) {
			Resource resource = xmlResourceListType.addNewResource();
			resource.setResourceID(it.next());
			resource.setResourceType(ResourceType.TABLE);
		}
	}

	public XmlLoadResourceDocument loadResource(String resource, String oid,
			String type, Registry registry) {
		
		XmlLoadResourceDocument xmlLoadResourceDoc = XmlLoadResourceDocument.Factory
				.newInstance();
		XmlLoadResourceType xmlLoadResourceType = xmlLoadResourceDoc
				.addNewXmlLoadResource();
		xmlLoadResourceType.setStatus(false);
		

		try {
			// check the type
			if (!("PROFILE".equals(type) || "TABLE".equals(type))) {
				throw new InvalidParameterException();
			}

			// check the oid
			if (("".equals(oid) || oid == null)) {
				oid = generateOid(type);
			}
			xmlLoadResourceType.setOid(oid);

			// check if we already have something with this oid
			if (getProfile(oid) == null && getTable(oid) == null) {
				//registry.getFile(oid);
				throw new FileNotFoundException();
			}
			xmlLoadResourceType
					.setErrorDescription("Resource already in the registry.");
		} catch (FileNotFoundException e) {
			// OK: not in the registry
			try {
				if ("PROFILE".equals(type)) {
					Profile tempProfile = new Profile(oid, resource);
					this.put(oid, tempProfile);
				} else if ("TABLE".equals(type)) {
					TableLibraryDocument tempTable = TableLibraryDocument.Factory
							.parse(resource);
					this.put(oid, tempTable);
				}
				// OK: consistent resource
				xmlLoadResourceType.setStatus(true);

			} catch (MalformedProfileException mpe) {
				xmlLoadResourceType.setErrorDescription("Invalid Profile.");
			} catch (XmlException xe) {
				xmlLoadResourceType.setErrorDescription("Invalid Table.");
			}
		} catch (InvalidParameterException ipe) {
			xmlLoadResourceType
					.setErrorDescription("Invalid type (can be \"PROFILE\" "
							+ "or \"TABLE\"." + " )");
		} /*catch (MalformedURLException e) {
			xmlLoadResourceType.setErrorDescription(e.getMessage());
		}*/ catch (RuntimeException e) {
			xmlLoadResourceType.setErrorDescription(e.getMessage());
		} catch (Exception e) {
			xmlLoadResourceType.setErrorDescription(e.getMessage());
		}
		return xmlLoadResourceDoc;
	}

	private String generateOid(String type) {
		String oid = null;
		if ("PROFILE".equals(type)) {
			Set<String> oids = tempProfile.keySet();
			SortedSet<String> sortedKeys = new TreeSet<String>();
			for (String curOid : oids) {
				if (curOid.startsWith(profileOid)) {
					sortedKeys.add(curOid);
				}
			}
			if (sortedKeys.isEmpty()) {
				oid = profileOid + "99001";
			} else {
				// MDI: fixed a small bug here that kept appending 1's to the end of the profile OID...
				int suffix = Integer.valueOf(sortedKeys.last().substring(profileOid.length())) + 1;
				oid = profileOid + suffix;
			}
		} else if ("TABLE".equals(type)) {
			Set<String> oids = tempTable.keySet();
			SortedSet<String> sortedKeys = new TreeSet<String>();
			for (String curOid : oids) {
				if (curOid.startsWith(tableOid)) {
					sortedKeys.add(curOid);
				}
			}
			if (sortedKeys.isEmpty()) {
				oid = tableOid + "99001";
			} else {
				int suffix = Integer.valueOf(sortedKeys.last().substring(tableOid.length())) + 1;
				oid = tableOid + suffix;
			}
		}
		return oid;
	}

	public String getProfileName(String messageStructure) {
		return profileMapping.get(messageStructure);
	}

}
