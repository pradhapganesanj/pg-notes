package com.pg.springb.security.jwtsecurity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pg.springb.security.service.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProcessor {

	@Value("${security.jwt.token.secret-key}")
	private String SECRET;

	@Value("${security.jwt.token.expires}")
	private long expiresInSecs;
	
	private final String HEADER_AUTH = "Authorization";
	private final String AUTH_TOKEN_PREFIX = "Bearer "; 

	public String generateToken(User signUser) {

		Claims cl = Jwts.claims().setSubject(signUser.getUserName());
		cl.put("roles", signUser.getRoles().toArray());

		Date validTillDateTime = getExpireDatetime();
		System.out.println(" validTillDateTime " + validTillDateTime);
		String newToken = Jwts.builder().setClaims(cl).signWith(SignatureAlgorithm.HS256, SECRET)
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
		String authHeader = req.getHeader(HEADER_AUTH);
		if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(AUTH_TOKEN_PREFIX)) {
			String token = authHeader.substring(7, authHeader.length()); 
			return token;
		}
		return null;
	}
	
	public Authentication getAuthentication(String token) {
		
		User user = claimUser(token);
				
		if(null != user) {
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	        	if(null != user.getRoles()) {
	        		grantedAuthorities = AuthorityUtils
	        				.createAuthorityList(user.getRoles().stream().toArray(String[]::new));
	        	}
	        
			return new UsernamePasswordAuthenticationToken(user.getUserName(), null, grantedAuthorities);
			
		}else {
			return null;
		}
	}
	
	public Authentication getAuthentication(HttpServletRequest req) {
		String token = getToken(req);
		if(!StringUtils.isEmpty(token)) {
			return getAuthentication(token);
		}
		return null;
	}
	
	private String claimSubject(String token) {
        String userName = Jwts.parser()
        					.setSigningKey(SECRET)
        					.parseClaimsJws(token)
        					.getBody()
        					.getSubject();
		return userName;
	}
	private User claimUser(String token) {
		User user = new User();
		
		Claims claims = Jwts.parser()
								.setSigningKey(SECRET)
								.parseClaimsJws(token)
								.getBody();
		
		user.setUserName(claims.getSubject());
		
        List<String> roles = (ArrayList<String>)claims.get("roles");
        
        System.out.println(" claim roles " + roles);
        if(roles!=null) {
        	roles.forEach(each -> System.out.println(each));
        }
        
        user.setRoles(roles);
        return user;
	}
}
