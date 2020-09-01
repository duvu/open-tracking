package me.duvu.tracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author beou on 8/1/17 06:02
 * @version 1.0
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setCacheable(true);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //.anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/oauth/token").permitAll()
                .antMatchers("/local/**").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/internal/**").permitAll()
                .antMatchers(HttpMethod.GET,"/telegram/**").permitAll()
                .antMatchers(HttpMethod.POST,"/telegram/**").permitAll()
                .antMatchers("/tracker").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
