package com.tecnocampus.configuration;

import org.apache.tomcat.jdbc.pool.DataSource;
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


    /*
     ********************   This is the configuration for SECURITY ***************************************************
     */
    @Configuration
    @Profile("memory")
    public class WebSecurityConfMemory extends WebSecurityConfigurerAdapter {

        //Configure Spring security's filter chain
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**");
        }

        //Configure how requests are secured by interceptors
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    //order matters. First the most specific. Last anyRequest
                    // pattrn "/users/**" match users/ and users/whatevere while pattern "users/*" only matches /users/whatever
                    .antMatchers("/users/*").hasRole("USER")  //hasAnyRole()
                    .antMatchers("static/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/enterNotesFlow").authenticated()
                    .anyRequest().authenticated()
            .and()
                .formLogin(); //a login form is showed when no authenticated request

            //Required to allow h2-console work
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        //Configure user-details sevices
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("user").password("user").roles("USER")
                    .and().withUser("aaaaa").password("aaaaa").roles("USER")
                    .and().withUser("admin").password("admin").roles("ADMIN")
                    .and().withUser("both").password("both").roles("USER,ADMIN");
        }

    }

    @Configuration
    @Profile("jdbc_auth")
    public class WebSecurityConfH2 extends WebSecurityConfigurerAdapter {

        @Autowired
        DataSource dataSource;

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
                    //Order of matchers matters. First the most specific. Last anyRequest
                    // pattrn "/users/**" match users/ and users/whatever while pattern "users/*" only matches /users/whatever
                    .antMatchers("/users/*").hasRole("USER")  //hasAnyRole()
                    .antMatchers("/static/**", "/createuser/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/enterNotesFlow").authenticated()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin(); //a login form is showed when no authenticated request

            //Required to allow h2-console work
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        //Configure user-details services
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery(
                            "select username,password, enabled from users where username=?")
                    .authoritiesByUsernameQuery(
                            "select username, role from user_roles where username=?")
                    .passwordEncoder(passEncoder());
        }
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
                    //.antMatchers("users/").hasRole("USER")
                    //.antMatchers("/users/{userId}/**").access("@webSecurity.checkUserId(authentication,#userId)")
                    //.antMatchers("/static/**", "/createuser/**", "/loggedInUser", "/enterNotesFlow/**").permitAll()   //, "/enterNotesFlow/**"
                    .antMatchers("/").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    //.antMatchers("/enterNotesFlow").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        //.loginPage("/myLogin"); //a login form is showed when no authenticated request
                .and()
                    .rememberMe()
                        .tokenValiditySeconds(2419200)
                        .key("notes")
                .and()
                    .logout()
                        .logoutSuccessUrl("/byebye"); //where to go when logout is successful
                        //.logoutUrl("logoutpage") // logout page

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