package com.oauth.resourceServer.security;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    private static final String REALM = "realm_access";

    private static final String ROLES = "roles";

    /**
     * Configuration For oauth
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(source -> new CustomJwtConfigure().convert(source));
        return http.build();
    }

    public static class CustomJwtConfigure implements Converter<Jwt, JwtAuthenticationToken> {

        @Override
        public JwtAuthenticationToken convert(Jwt jwt) {
            var tokenAttributes = jwt.getClaims();
            var jsonObject = (JSONObject) tokenAttributes.get(REALM);
            var roles = (JSONArray) jsonObject.get(ROLES);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            roles.forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
            return new JwtAuthenticationToken(jwt, grantedAuthorities);
        }
    }
}
