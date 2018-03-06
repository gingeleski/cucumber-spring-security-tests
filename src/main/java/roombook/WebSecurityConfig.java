package roombook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import roombook.filters.UrlSessionFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new UrlSessionFilter(), ChannelProcessingFilter.class)
            .authorizeRequests()
            .antMatchers("/")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .successForwardUrl("/")
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("rjohnson").password("Banana3").roles("EMPLOYEE")
                .and()
                .withUser("tsmith").password("Grapefruit4").roles("ASSISTANT")
                .and()
                .withUser("jmcdonald").password("Cranberry5").roles("HR_MGR")
                .and()
                .withUser("abrown").password("Watermelon6").roles("ADMIN");
    }
}