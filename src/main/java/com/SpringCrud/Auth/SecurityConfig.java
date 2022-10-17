package com.SpringCrud.Auth;

import com.SpringCrud.Auth.filters.JWTAuthorizationFilter;
import com.SpringCrud.Auth.filters.JWTauthenticationFilter;
import com.SpringCrud.Auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected  void configure(AuthenticationManagerBuilder auth)throws Exception {

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //on va specifier les droits d'acces
        //dans les droits je dois autoriser les fonctionnalites
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        //http.formLogin();//formLogin() je demande a spring secu quand il a un utilisateur qui va accesde
        //dans il n'a pas le droit achiffe le formulaire d'auth.

        http.authorizeRequests().antMatchers("/login/**","/Students","/role/**","/register/**")
                .permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**")
                .hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/**")
              .hasAnyAuthority("USER");


        http.authorizeRequests().anyRequest().authenticated();//auth() cad il faut passer par un systeme
        //auth pour faire acces a chaque user.

        http.addFilter(new JWTauthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        //la raison que lorsque que j'ajoute plusieur filtre cet methode peut donner
        //pour specifier un filtre que je vais
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
