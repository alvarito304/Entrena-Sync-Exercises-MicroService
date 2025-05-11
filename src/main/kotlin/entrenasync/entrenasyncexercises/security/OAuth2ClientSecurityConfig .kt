package entrenasync.entrenasyncexercises.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
open class OAuth2ClientSecurityConfig  {
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.
            csrf { it.disable() }
            .oauth2Client { it.disable() }
            .formLogin { it.disable() }
            .oauth2Login { it.disable() }
            .authorizeHttpRequests {
            it.requestMatchers(
                "/oauth2/authorize/google",
                "/login",
                "/resources/**",
                "/Exercises",
                "/",
                "/index",
                "/error",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-resources/**"
            ).permitAll()
                .anyRequest().permitAll()
        }
            .oauth2Login { it.disable() }
        return http.build()
    }
}