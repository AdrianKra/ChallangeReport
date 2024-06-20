package blossom.reports_service.inbound.DTOs;

import blossom.reports_service.model.Entities.User;

public class UserDTO {

  private Long id;
  private String email;
  private int version;

  /**
   * Default Constructor for UserDTO
   */
  public UserDTO() {
  }

  /**
   * Parameterized Constructor for UserDTO
   */
  public UserDTO(Long id, String email, int version) {
    this.id = id;
    this.email = email;
    this.version = version;
  }

  /**
   * Constructor for UserDTO from User
   */
  public UserDTO(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.version = user.getVersion();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "UserDTO{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", version=" + version +
        '}';
  }
}
