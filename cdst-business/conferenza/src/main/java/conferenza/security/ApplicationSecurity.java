package conferenza.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Value("${security.enable}")
	private String securityEnable=new String("N");

	public String getSecurityEnable() {
		return securityEnable;
	}

	public void setSecurityEnable(String securityEnable) {
		this.securityEnable = securityEnable;
	}

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		if("S".equals(securityEnable)) {	
			web.ignoring().antMatchers("/*.css");
			web.ignoring().antMatchers("/*.js");
			web.ignoring().antMatchers("/img/**");
			web.ignoring().antMatchers("/docucondivisa/**");
			web.ignoring().antMatchers("/oo/**");
			web.ignoring().antMatchers("/report/**");
			web.ignoring().antMatchers("/api/**");
			    
		}else {
			web.ignoring().antMatchers("/**");
		}
			
	}
	
	/**
	 * Se securityEnable="S" 
	 * allora SOLO il contesto docucondivisa è abilitato
	 * altrimenti per tutto ci pensa WSO2
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if("S".equals(securityEnable)) {	
	        http
	        .authorizeRequests().antMatchers("/docucondivisa/**").permitAll()
	        .and()
	        .authorizeRequests().antMatchers("/downloadFile/**").permitAll()
	        .and()
	        .authorizeRequests().antMatchers("/oo/**").permitAll()	        
	        .and()
	        .authorizeRequests().antMatchers("/**").denyAll()
	        ;
			
			http.headers().frameOptions().disable();
			
			http
			  .sessionManagement()
			    .enableSessionUrlRewriting(true);
			
			
			
		}else {
			http.authorizeRequests().antMatchers("/**").permitAll();						
		}
	}
	
	
}
