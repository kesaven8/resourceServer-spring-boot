package com.oauth.resourceServer.security.anotation;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Secured({"ROLE_SIMPLE_USER"})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface User {
}
