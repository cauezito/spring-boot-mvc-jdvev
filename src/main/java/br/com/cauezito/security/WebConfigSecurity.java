package br.com.cauezito.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetails;
	
	@Override
	//Configura as solicitações de acesso HTTP
	protected void configure(HttpSecurity http) throws Exception {
		//desabilita as configuração padrão de memória
		http.csrf().disable()
		//permite a restrição de acessos
		.authorizeRequests()
		//qualquer usuário acessa a página inicial
		.antMatchers(HttpMethod.GET, "/").permitAll()
		//apenas administradores acessam o cadastro
		.antMatchers(HttpMethod.GET, "/cadastroUsuario").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().permitAll() //permite qualquer usuário
		//.loginPage("/login")//define a página de login criada por mim
		//.defaultSuccessUrl("/teste")
	//	.failureUrl("/login?opa=sim")
		.and().logout() //mapeia url de logout e invalida usuário 
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		}
	
	//Cria autenticação do usuário (banco de dados ou memória)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override //ignora url's específicas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
	}
}
