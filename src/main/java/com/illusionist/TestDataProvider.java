package com.illusionist;

import com.illusionist.auth.persistence.UserEntity;
import com.illusionist.auth.persistence.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class TestDataProvider {

  private static final Logger LOG = LoggerFactory.getLogger(TestDataProvider.class);
  private final UserRepository users;

  public TestDataProvider(final UserRepository users) {
    this.users = users;
  }

  @EventListener
  public void init(StartupEvent event) {
    LOG.debug("Loading data at startup");

    final String email = "nikhil@gmail.com";
    if (users.findByEmail(email).isEmpty()) {
      final UserEntity alice = new UserEntity();
      alice.setEmail(email);
      alice.setPassword("123456");
      users.save(alice);
      LOG.debug("Insert user {}", email);
    }
  }
}