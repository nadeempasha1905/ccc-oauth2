/**
 * 
 */
package com.ccc.oauth2.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.ccc.oauth2.controller.UserRepository;

/**
 * @author Administrator
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenEnhancer#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub

		System.out.println("Request arrived"+accessToken+"__________"+authentication);
		
		 User user = (User) authentication.getPrincipal();
	        String username = user.getUsername();
	    	//User user =  new User(); 
	        final Map<String, Object> additionalInfo = new HashMap<>();
	        
	        com.ccc.oauth2.dto.User user2 = userRepository.findOneByUsername(username);
	        //additionalInfo.put("statecode", user2.getName());
	        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
	        return accessToken;
	}

}
