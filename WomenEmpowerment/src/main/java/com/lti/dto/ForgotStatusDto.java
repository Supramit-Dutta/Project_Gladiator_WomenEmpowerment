package com.lti.dto;

public class ForgotStatusDto extends StatusDto {
	private boolean verified;

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}
