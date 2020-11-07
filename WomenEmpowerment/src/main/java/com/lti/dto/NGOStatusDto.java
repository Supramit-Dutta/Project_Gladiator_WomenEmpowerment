package com.lti.dto;

public class NGOStatusDto extends StatusDto {
	String ngoemail;
	String ngoName;
	int ngoId;
	
	
	public int getNgoId() {
		return ngoId;
	}
	public void setNgoId(int ngoId) {
		this.ngoId = ngoId;
	}
	public String getNgoemail() {
		return ngoemail;
	}
	public void setNgoemail(String ngoemail) {
		this.ngoemail = ngoemail;
	}
	public String getNgoName() {
		return ngoName;
	}
	public void setNgoName(String ngoName) {
		this.ngoName = ngoName;
	}
	
}
