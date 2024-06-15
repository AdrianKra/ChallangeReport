package blossom.reports_service.inbound.DTOs;

import blossom.reports_service.model.Entities.User;

public class UserDTO {

  private Long id;
  private String email;
  private int version;

  public UserDTO() {
  }

  public UserDTO(Long id, String email, int version) {
    this.id = id;
    this.email = email;
    this.version = version;
  }

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
}
