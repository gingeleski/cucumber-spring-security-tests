package roombook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import roombook.security.JwtAccessDeniedHandler;
import roombook.security.JwtAuthenticationEntryPoint;
import roombook.security.jwt.JWTConfigurer;
import roombook.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public WebSecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter,
                             JwtAuthenticationEntryPoint authenticationErrorHandler,
                             JwtAccessDeniedHandler jwtAccessDeniedHandler)
    {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // Configure BCrypt password encoder =====================================================================

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // Configure paths and requests that should be ignored by Spring Security ================================

    @Override
    public void configure(WebSecurity web)
    {
        // Going to specify paths to ignore
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**") // OPTIONS requests have no authZ restrictions
                .antMatchers("/", // Static resources have no authZ restrictions
                             "/*.html",
                             "/favicon.ico",
                             "/**/*.html",
                             "/**/*.css",
                             "/**/*.js",
                             "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        // Security settings
        httpSecurity
                // CSRF is inappropriate given we're RESTful API only
                .csrf().disable()
                // Do CORS before checking that user is authN'd
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // The following enable H2 console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                // No traditional sessions - RESTful API is stateless
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // Currently authZ'ing here - @PermitAll locations below
                .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                ////.antMatchers("/api/register").permitAll()
                ////.antMatchers("/api/activate").permitAll()
                ////.antMatchers("/api/account/reset-password/init").permitAll()
                ////.antMatchers("/api/account/reset-password/finish").permitAll()
                // Continue authZ'ing here - check roles as specified below
                .antMatchers("/api/person").hasAuthority("ROLE_USER")
                .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")
                // Otherwise blanket that users should be authN'd
                .anyRequest().authenticated()
                .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter()
    {
        return new JWTConfigurer(tokenProvider);
    }
}