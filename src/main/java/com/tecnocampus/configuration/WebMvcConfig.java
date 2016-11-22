package com.tecnocampus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring4.view.FlowAjaxThymeleafView;

/**
 * Created by roure on 19/10/2016.
 *
 * Taken from: https://github.com/spring-projects/spring-boot/issues/502#issuecomment-73303232
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private WebFlowConfig webFlowConfig;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
        return handlerMapping;
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public AjaxThymeleafViewResolver ajaxThymeleafViewResolver() {
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setTemplateEngine(springTemplateEngine);
        return viewResolver;
    }



    @Configuration
    @Profile("custom_jdbc_auth")
    public class WebSecurityConfOwnAuth extends WebSecurityConfigurerAdapter {

        @Autowired
        UserDetailsService userDetailsService;

        @Bean
        public PasswordEncoder passEncoder() {
            return new BCryptPasswordEncoder();
        }


        //Configure Spring security's filter chain
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**");
        }

        //Configure how requests are secured by interceptors
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/forecast").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/static/**", "/registre/**").permitAll()
                    .mvcMatchers("/profile/").hasRole("USER")
                    .mvcMatchers("/usuaris/**").hasRole("ADMIN")
                    .mvcMatchers("/usuaris/{userId}/**").access("@webSecurity.checkUserId(authentication,#userId)")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login").permitAll() //a login form is showed when no authenticated request
                .and()
                    .rememberMe()
                        .tokenValiditySeconds(2419200)
                        .key("quizcampus")
                .and()
                    .logout().logoutSuccessUrl("/");

            //Required to allow h2-console work
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        //Configure user-details services
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passEncoder());
        }
        //TODO: modify the webFlow to enter once logged in
    }

}