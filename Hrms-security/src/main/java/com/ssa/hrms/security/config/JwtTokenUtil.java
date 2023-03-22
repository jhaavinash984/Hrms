package com.ssa.hrms.security.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ssa.hrms.common.ApplicationProperty;
import com.ssa.hrms.security.domain.LoggedInUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.lettuce.core.dynamic.annotation.Value;


@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;


	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//get Source From Token
	public String getSourceFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuer);
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(ApplicationProperty.secretHrms).parseClaimsJws(token).getBody();
	}

	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//generate token for user
	public String generateToken(LoggedInUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername(),userDetails);
	}

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claimsObj, String subject, LoggedInUser userDetails) {
		claimsObj.put("id", userDetails.getId());
		claimsObj.put("source", userDetails.getSource());
		claimsObj.put("userName", userDetails.getUsername());
		claimsObj.put("firstName", userDetails.getFirstName());
		claimsObj.put("lastName", userDetails.getLastName());
		claimsObj.put("groupId", userDetails.getGroupId());
		claimsObj.put("email", userDetails.getEmail());
		claimsObj.put("mobileNumber", userDetails.getMobileNumber());
		claimsObj.put("gender", userDetails.getGender());
		claimsObj.put("isActive", userDetails.getIsActive());
		claimsObj.put("group", userDetails.getGroup() != null ? userDetails.getGroup().getGroupName() : null);
		claimsObj.put("systemRoleId", userDetails.getRole().get(0).getId());
		claimsObj.put("systemRoleName", userDetails.getRole().get(0).getRoleName());
		claimsObj.put("systemRoleTypeId", userDetails.getRole().get(0).getRoleTypeId());
		claimsObj.put("organisationRoleId", userDetails.getOrganisationRoleId());
		claimsObj.put("authorities", userDetails.getAuthorities().get(0).getAuthority());
		return Jwts.builder().setClaims(claimsObj).setSubject(subject).setIssuer(userDetails.getSource()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ApplicationProperty.JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, ApplicationProperty.secretHrms).compact();
	}

	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}