package com.pg.springb.security.jwt.provider;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pg.springb.security.jwt.model.JwtUser;
import com.pg.springb.security.jwt.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component

public class JwtProvider extends AbstractUserDetailsAuthenticationProvider {
	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expires}")
	private long expiresInSecs;

	@Autowired
	UserService userService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authToken)
			throws AuthenticationException {

		return getUserDetails(userName);
	}

	public String generateToken(JwtUser signUser) {

		System.out.println(" generateToken " + signUser.toString());
		System.out.println(String.format("secretKey : %s, expire: %d", secretKey, expiresInSecs));

		Claims cl = Jwts.claims().setSubject(signUser.getUserName());
		cl.put("role", String.valueOf(signUser.getRoles().toArray()));

		Date validTillDateTime = getExpireDatetime();
		System.out.println(" validTillDateTime " + validTillDateTime);
		String newToken = Jwts.builder().setClaims(cl).signWith(SignatureAlgorithm.HS256, secretKey)
				.setIssuedAt(new Date()).setExpiration(validTillDateTime).compact();

		System.out.println("Token : " + newToken);
		return newToken;
	}

	private Date getExpireDatetime() {
		LocalDateTime ldt = LocalDateTime.now().plusSeconds(expiresInSecs);
        // convert LocalDateTime to date
        Date validTillDateTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		return validTillDateTime;
	}

	public String getToken(HttpServletRequest req) {
		String authHeader = req.getHeader("Authorization");
		if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7, authHeader.length());
		}
		return null;
	}

	public Authentication getAuthentication(HttpServletRequest req) {
		String token = getToken(req);
		if(!StringUtils.isEmpty(token)) {
			return getAuthentication(token);
		}
		return null;
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = getUserDetails(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		/*PreAuthenticatedAuthenticationToken preAuth = new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities());
		return preAuth;*/
	}

	public UserDetails getUserDetails(String userName) {
		return userService.loadUserByUsername(userName);
	}
	
	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}
