package com.schoolcatalog.app.utils;

import com.schoolcatalog.app.models.Person;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Session
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionInfo {

  private boolean loggedIn;
  private Person user;

  public SessionInfo() {
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public Person getUser() {
    return user;
  }

  public void setUser(Person user) {
    this.user = user;
  }
}