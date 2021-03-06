package com.police_resource_manager.prms.security;

public class AuthenticationResponse {
	
	private final String accessToken;
	private final String refreshToken;
	
	public AuthenticationResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
		
}
