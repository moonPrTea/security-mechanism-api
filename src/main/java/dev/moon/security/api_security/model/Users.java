package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "second_name")
  private String secondName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(unique = true)
  private String username;

  private String password;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "user")
  private List<UserCard> userCards = new ArrayList<>();

  public Users() {
  }

  public Users(String firstName, String secondName, String middleName, String username, String password) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.middleName = middleName;
    this.username = username;
    this.password = password;
    this.createdAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
