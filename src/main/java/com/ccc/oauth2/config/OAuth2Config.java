/**
 * 
 */
package com.ccc.oauth2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author Administrator
 *
 */

@Configuration
@Lazy
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
   @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
	   System.out.println("5.Request arrived");
	   TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
	   tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        configurer.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(this.authenticationManager)
                .approvalStoreDisabled();
     }
  /* @Override
   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
       security
               .tokenKeyAccess("permitAll()")
               .checkTokenAccess("isAuthenticated()")
               .allowFormAuthenticationForClients();
   }*/
   
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	System.out.println("6.Request arrived");
        clients.jdbc(jdbcTemplate.getDataSource());
    }
   
   @Bean
   public JwtAccessTokenConverter accessTokenConverter() {
	   System.out.println("7.Request arrived");
	   JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //converter.setSigningKey("123");
       final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("idsp.jks"), "indigoidsp".toCharArray());
       converter.setKeyPair(keyStoreKeyFactory.getKeyPair("idsp"));
       System.out.println("key : "+converter.getKey());
       return converter;
   }
   
   @Bean
   @Primary
   public DefaultTokenServices tokenServices() {
	   System.out.println("8.Request arrived");
       DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
       defaultTokenServices.setTokenStore(tokenStore());
       defaultTokenServices.setSupportRefreshToken(true);
       return defaultTokenServices;
   }
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
    	System.out.println("9.Request arrived");
        return new CustomTokenEnhancer();
    }
    
    @Bean
    public JwtTokenStore tokenStore(){
    	System.out.println("10.Request arrived");
 	   return new JwtTokenStore(accessTokenConverter());
    }

}
