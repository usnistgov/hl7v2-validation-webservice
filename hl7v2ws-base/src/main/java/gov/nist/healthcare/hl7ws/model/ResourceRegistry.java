package gov.nist.healthcare.hl7ws.model;

import java.util.HashMap;

public class ResourceRegistry {

	private HashMap<String,CFResource> resources;
	
	public ResourceRegistry(){
		resources = new HashMap<String,CFResource>();
	}
	
	public void addResource(String OID, CFResource r){
		resources.put(OID, r);
	}
	
	public CFResource getResource(String OID){
		if(resources.containsKey(OID))
			return resources.get(OID);
		else
			return null;
	}
}
