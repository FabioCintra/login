package io.github.fabiocintra.login.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.fabiocintra.login.security.custom.authentication.JwtAuthenticationCustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer oAuth2ASConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.apply(oAuth2ASConfigurer);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

        return http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/oauth2/callback").permitAll();
                    authorize.requestMatchers("/oauth2").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .exceptionHandling(
                        exception ->
                                exception.authenticationEntryPoint(
                                        new LoginUrlAuthenticationEntryPoint("http://localhost:5173/login")
                                )
                )
                .securityMatcher(oAuth2ASConfigurer.getEndpointsMatcher())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorizationServerSettings  authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .tokenEndpoint("/oauth2/token") // pega o token
                .authorizationEndpoint("/oauth2/authorize") // pega o code
                .oidcLogoutEndpoint("/oauth2/logout") // desloga
                .oidcUserInfoEndpoint("/oauth2/userinfo") // pega as informacoes do usuario via OIDC
                .tokenRevocationEndpoint("/oauth2/revoke") // revoga o token antes de acabar o tempo
                .tokenIntrospectionEndpoint("/oauth2/introspection") // passa as informacoes do ususario vai header
                .jwkSetEndpoint("/oauth2/jwks") // pega a chave publica
                .build();
    }

    @Bean
    public ClientSettings  clientSettings() {
        return ClientSettings.builder()
                .requireProofKey(false)
                .requireAuthorizationConsent(false)
                .build();
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .refreshTokenTimeToLive(Duration.ofMinutes(90))
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .build();
    }

    @Bean
    public JWKSource<SecurityContext>  jwkSource() throws Exception{
        RSAKey rsaKey = rsaKeyCreate();
        JWKSet jwkSet = new JWKSet(rsaKey);

        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext>  jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private RSAKey rsaKeyCreate() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        return new RSAKey
                .Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

}
