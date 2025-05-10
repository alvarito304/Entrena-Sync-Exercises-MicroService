package entrenasync.entrenasyncexercises.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Al estar detrás de un API‑Gateway que ya gestiona auth, podemos
            // desactivar CSRF y permitir este endpoint sin seguridad.
            .csrf { it.disable() }

            .authorizeHttpRequests { auth ->
                auth
                    // 1) Permitimos subir videos sin pasar por Spring Security
                    .requestMatchers("**").permitAll()

                    // 2) (Opcional) podrías también abrir cualquier otra ruta:
                    // .anyRequest().permitAll()

                    // 3) O si quieres que lo demás siga protegido:

            }

            // Si en tu microservicio no vas a usar login ni OAuth2Client, puedes
            // incluso desactivar los mecanismos de login:
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .oauth2Login { it.disable() }

        return http.build()
    }
}
