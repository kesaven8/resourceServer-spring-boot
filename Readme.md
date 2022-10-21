## Running keycloack Image

```docker
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:19.0.3 start-dev
```

## Configuration for oauth + add Simple authorities

```java
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
            http.authorizeHttpRequests((authorize)->authorize
            .anyRequest().authenticated()
            )
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(source->new CustomJwtConfigure().convert(source));
            return http.build();
            }
```

### Used a Custom JWT Converter to add Roles so as Spring Security can understand properly

```java
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
```

## Creating Custom Security Endpoint

### This helps to minimize typo errors as these anotations will be used in lots of places

```java

@Secured({"ROLE_ADMIN", "ROLE_SIMPLE_USER"})
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {
}

@Secured({"ROLE_SIMPLE_USER"})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {
}

```

## Using Custom Anotations in Controller

```java
    @Admin
@GetMapping("/admin")
public String helloWorld(){
        return"I am admin";
        }

@User
@GetMapping("/simple-user")
public String simpleUserController(){
        return"I am a simple user";
        }
```

 Attached realm in resources
 (realm-export.json)

