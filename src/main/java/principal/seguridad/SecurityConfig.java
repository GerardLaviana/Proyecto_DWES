package principal.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import principal.servicios.impl.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.authorizeRequests()
         .antMatchers("/","/ingredientes").permitAll()
         .antMatchers("/usuarios").hasRole("ADMIN")
         //.regexMatchers("/bocadillos/.*").hasRole("ADMIN")              
         .and()
         .formLogin()
             .loginPage("/login")
             .permitAll()
             .defaultSuccessUrl("/")
             .failureUrl("/login?error=true")
             .usernameParameter("username")
             .passwordParameter("password")
             .and()
         .logout()
             .permitAll()
             .logoutUrl("/logout")
             .logoutSuccessUrl("/login/logout")
         .invalidateHttpSession(true)
         .clearAuthentication(true)
             .and()
        
         .csrf().disable();
	}
}
