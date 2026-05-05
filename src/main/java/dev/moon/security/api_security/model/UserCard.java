package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "user_card")
public class UserCard {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column
  private UUID id;

  @Column(nullable = false, scale = 5)
  private BigDecimal balance;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "card")
  private List<FinancialOperation> financialOperations;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  private Users user;

  public UserCard() {
  }

  public UserCard(BigDecimal balance, Users user) {
    this.balance = balance;
    this.createdAt = LocalDateTime.now();
    this.user = user;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<FinancialOperation> getFinancialOperations() {
    return financialOperations;
  }

  public void setFinancialOperations(List<FinancialOperation> financialOperations) {
    this.financialOperations = financialOperations;
  }

  public Users getUser() {
    return user;
  }
}
