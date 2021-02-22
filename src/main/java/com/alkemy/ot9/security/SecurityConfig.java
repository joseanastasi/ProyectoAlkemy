package com.alkemy.ot9.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.alkemy.ot9.security.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/css/**", "/resources/**", "/js/**", "/img/**").permitAll();

        http.authorizeRequests().antMatchers("/bootstrap/**", "/fontawesome/**", "/img/**").permitAll()
                .antMatchers("/login", "/", "/users/register", "/programa_paas", "/beneficiaries/**", "/news/**",
                        "/contacts/**", "/fragments", "/error", "/forbidden", "/subscribe/**")
                .permitAll();

        http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll().antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll().and().csrf().disable().formLogin().loginPage("/login")
                .failureUrl("/login?error=true").defaultSuccessUrl("/login").usernameParameter("email")
                .passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/access-denied");

        http.formLogin().loginProcessingUrl("/signin").loginPage("/loginprogramapaas").permitAll()
                .defaultSuccessUrl("/index").usernameParameter("email").passwordParameter("password").and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/loginprogramapaas?logout").permitAll().deleteCookies("JSESSIONID").and()
                .rememberMe().tokenValiditySeconds(2592000).key("secret").rememberMeParameter("checkRememberMe");

        http.formLogin().successHandler((AuthenticationSuccessHandler) new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

                System.out.println("Logged user: " + authentication.getName());

                response.sendRedirect("/admin/organizacion");
            }
        });
        http.formLogin().failureHandler(new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                System.out.println("Login failed");
                System.out.println(exception);

                response.sendRedirect("/loginprogramapaas");
            }

        });
    }

}