package entrenasync.entrenasyncexercises.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository


@Configuration
class OAuth2ClientConfig {

    /**
     * Bean responsable de obtener y refrescar tokens OAuth2 automáticamente.
     */
    @Bean
    fun authorizedClientManager(
        clientRegistrations: ClientRegistrationRepository,
        authorizedClients: OAuth2AuthorizedClientRepository
    ): OAuth2AuthorizedClientManager {
        // Definimos los flujos que soportaremos: authorization_code y refresh_token
        val provider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .refreshToken()
            .build()

        return DefaultOAuth2AuthorizedClientManager(clientRegistrations, authorizedClients)
            .also { it.setAuthorizedClientProvider(provider) }
    }
}
