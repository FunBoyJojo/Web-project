package com.testplus.testplus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.sql.DataSource;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



 @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/**").hasRole("ADMIN")
//                .antMatchers("/blog/create-test").hasRole("TEACHER")
//
//                .antMatchers("/blog/passing-tests","/blog/passing-tests/**").hasRole("USER")

                .antMatchers("/**").permitAll()
                .antMatchers("/blog/create-test").permitAll()
                .antMatchers("/blog/create-test/**").permitAll()

                .and()
                .formLogin().loginPage("/blog/login").defaultSuccessUrl("/").permitAll();
                //.and()
                //.logout().logoutUrl("/blog/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select login, password, active from user where login=?")
                .authoritiesByUsernameQuery("select u.login, ur.roles from user u inner join user_role ur on u.id = ur.user_id where u.login=?");

    }
//
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();


        return new InMemoryUserDetailsManager(user);
    }

}
