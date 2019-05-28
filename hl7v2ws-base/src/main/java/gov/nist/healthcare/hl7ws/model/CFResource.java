package gov.nist.healthcare.hl7ws.model;

import java.util.ArrayList;

import org.openimmunizationsoftware.dqa.nist.CompactReportModel;
import org.openimmunizationsoftware.dqa.nist.ProcessMessageHL7;

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.filter.Filter;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.proxy.ValidationProxy;

public class CFResource {
	private String OID;
	private String profile;
	private String constraints;
	private String valueSets;

	public String validate(ValidationProxy validator, String message, boolean dqa, ArrayList<String> dqaFilter) throws Exception{
		EnhancedReport er = validator.validateContent(message, profile, constraints, valueSets, OID,Context.Free);  
		Filter.removeClassification(er, "Affirmative");
        if(dqa){
        	CompactReportModel cc= ProcessMessageHL7.getInstance().process(message, "1223");
			if(dqaFilter.isEmpty()){
				ArrayList<Section> sects = cc.toSections();
				if(sects.size() != 0)
					er.put(cc.toSections());
			}
			else {
				ArrayList<Section> sects = cc.toSections(dqaFilter);
				if(sects.size() != 0)
					er.put(cc.toSections(dqaFilter));
				System.out.println("CNT");
				System.out.println(er.to("json").toString());
			}
        }
		return er.to("xml").toString();
	}
	
	public CFResource(String oID, String profile, String primary_constraints,
			String secondary_constraints, String valueSets) {
		super();
		OID = oID;
		this.profile = profile;
		this.constraints = primary_constraints;
		this.valueSets = valueSets;
	}

	public CFResource() {
	}

	public String getOID() {
		return OID;
	}
	public void setOID(String oID) {
		OID = oID;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getConstraints() {
		return constraints;
	}
	public void setConstraints(String primary_constraints) {
		this.constraints = primary_constraints;
	}
	public String getValueSets() {
		return valueSets;
	}
	public void setValueSets(String valueSets) {
		this.valueSets = valueSets;
	}
	
}
