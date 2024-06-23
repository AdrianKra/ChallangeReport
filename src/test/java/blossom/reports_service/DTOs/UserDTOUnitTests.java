package blossom.reports_service.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blossom.reports_service.inbound.DTOs.UserDTO;
import blossom.reports_service.model.Entities.User;

public class UserDTOUnitTests {

  private UserDTO userDTO;

  private final Long id = 1L;
  private final String email = "test@example.com";
  private final int version = 1;

  @BeforeEach
  public void setUp() {
    userDTO = new UserDTO(id, email, version);
  }

  @Test
  public void testDefaultConstructor() {
    UserDTO dto = new UserDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(id, userDTO.getId());
    assertEquals(email, userDTO.getEmail());
    assertEquals(version, userDTO.getVersion());
  }

  @Test
  public void testSettersAndGetters() {
    userDTO.setId(2L);
    userDTO.setEmail("new@example.com");
    userDTO.setVersion(2);

    assertEquals(2L, userDTO.getId());
    assertEquals("new@example.com", userDTO.getEmail());
    assertEquals(2, userDTO.getVersion());
  }

  @Test
  public void testConstructorFromUser() {
    User user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
    user.setVersion(1);

    UserDTO dto = new UserDTO(user);

    assertEquals(1L, dto.getId());
    assertEquals("test@example.com", dto.getEmail());
    assertEquals(1, dto.getVersion());
  }

  @Test
  public void testToString() {
    String expected = "UserDTO{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", version=" + version +
        '}';
    assertEquals(expected, userDTO.toString());
  }
}
