package blossom.reports_service.DTOs;

import blossom.reports_service.inbound.DTOs.UserDTO;
import blossom.reports_service.model.Entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOUnitTests {

  private UserDTO userDTO;

  @BeforeEach
  public void setUp() {
    userDTO = new UserDTO(1L, "test@example.com", 1);
  }

  @Test
  public void testDefaultConstructor() {
    UserDTO dto = new UserDTO();
    assertNotNull(dto);
  }

  @Test
  public void testParameterizedConstructor() {
    assertEquals(1L, userDTO.getId());
    assertEquals("test@example.com", userDTO.getEmail());
    assertEquals(1, userDTO.getVersion());
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
}
