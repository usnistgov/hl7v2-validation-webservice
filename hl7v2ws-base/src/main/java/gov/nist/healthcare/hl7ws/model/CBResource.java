package gov.nist.healthcare.hl7ws.model;

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.filter.Filter;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.model.Section;
import gov.nist.healthcare.unified.proxy.ValidationProxy;

import java.util.ArrayList;

import org.openimmunizationsoftware.dqa.nist.CompactReportModel;
import org.openimmunizationsoftware.dqa.nist.ProcessMessageHL7;

public class CBResource {
	private CFResource base;
	private String contraints;
	private String tPlan;
	private String tCase;
	private String tGroup;
	private String tStep;
	
	public String validate(ValidationProxy validator, String message, boolean dqa, ArrayList<String> dqaFilter) throws Exception{
		ArrayList<String> cstr = new ArrayList<String>();
		cstr.add(contraints);
		cstr.add(base.getConstraints());
		EnhancedReport er = validator.validate(message, base.getProfile(), cstr, base.getValueSets(), base.getOID(),Context.Free);                                                   
		er.setTestCase(tPlan, tGroup, tCase, tStep);
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
				}
			}
			return er.to("xml").toString();

	}
	
	
	public CBResource() {
		super();
	}
	
	public CBResource(CFResource base, String contraints, String tPlan,
			String tCase, String tGroup, String tStep) {
		super();
		this.base = base;
		this.contraints = contraints;
		this.tPlan = tPlan;
		this.tCase = tCase;
		this.tGroup = tGroup;
		this.tStep = tStep;
	}
	
	public CFResource getBase() {
		return base;
	}
	public void setBase(CFResource base) {
		this.base = base;
	}
	public String getContraints() {
		return contraints;
	}
	public void setContraints(String contraints) {
		this.contraints = contraints;
	}
	public String gettPlan() {
		return tPlan;
	}
	public void settPlan(String tPlan) {
		this.tPlan = tPlan;
	}
	public String gettCase() {
		return tCase;
	}
	public void settCase(String tCase) {
		this.tCase = tCase;
	}
	public String gettGroup() {
		return tGroup;
	}
	public void settGroup(String tGroup) {
		this.tGroup = tGroup;
	}
	public String gettStep() {
		return tStep;
	}
	public void settStep(String tStep) {
		this.tStep = tStep;
	}
	
	
	
}
