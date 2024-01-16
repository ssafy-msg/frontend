package com.ssafy.msg.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.msg.user.model.dto.Oauth2Dto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KakaoUtil implements Oauth2Util {
	
	@Value("${oauth2.client.provider.kakao.user-info-uri}")
	private String userInfoUri;
	
	@Value("${oauth2.client.provider.kakao.token-uri}")
	private String tokenUri;
	
	@Value("${oauth2.client.registration.kakao.client-id}")
	private String clientId;
	
	@Value("${oauth2.client.registration.kakao.redirect-uri}")
	private String redirectUri;
	
	@Value("${oauth2.client.registration.kakao.client-secret}")
	private String clientSecret;
	
	public String getAccessToken(String code) throws Exception {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("code", code);
			params.add("client_id", clientId);
			params.add("client_secret", clientSecret);
			params.add("redirect_uri", redirectUri);
			HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);
			ResponseEntity<String> response = restTemplate.exchange(tokenUri, HttpMethod.POST, kakaoTokenRequest, String.class);
			JsonElement jsonElement = JsonParser.parseString(response.getBody());
			return jsonElement.getAsJsonObject().get("access_token").getAsString();
		} catch (Exception e) {
			log.error("getAccessToken() -> Exception : {}", e);
			return null;
		} finally {
			log.info("getAccessToken() -> End");
		}
	}
	
	public Oauth2Dto getUserInfo(String kakaoAccessToken) throws Exception {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Authorization", "Bearer " + kakaoAccessToken);
			httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(httpHeaders);
			ResponseEntity<String> response = restTemplate.exchange(userInfoUri, HttpMethod.POST, kakaoUserInfoRequest,
					String.class);
			JsonElement jsonElement = JsonParser.parseString(response.getBody());
			JsonObject properties = jsonElement.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = jsonElement.getAsJsonObject().get("kakao_account").getAsJsonObject();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String emailId = kakaoAccount.getAsJsonObject().get("email").getAsString();
			return Oauth2Dto.builder().nickname(nickname).emailId(emailId).provider("kakao").build();
		} catch (Exception e) {
			log.error("getUserInfo() -> Exception : {}", e);
			return null;
		} finally {
			log.info("getUserInfo() -> End");
		}
	}
}
