package blossom.reports_service;

import org.junit.jupiter.api.Test;

import blossom.reports_service.model.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

  @Test
  public void testGetAuthority_Admin() {
    // Arrange
    Role role = Role.ADMIN;

    // Act
    String authority = role.getAuthority();

    // Assert
    assertEquals("ADMIN", authority);
  }

  @Test
  public void testGetAuthority_User() {
    // Arrange
    Role role = Role.USER;

    // Act
    String authority = role.getAuthority();

    // Assert
    assertEquals("USER", authority);
  }
}
