package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "financial_operation")
public class FinancialOperation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user_card", nullable = false)
  private UserCard card;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_destination_card", nullable = false)
  private UserCard destinationCard;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_operation_type", nullable = false)
  private OperationType operationType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_currency", nullable = false)
  private Currency currency;

  @Column(nullable = false, scale = 5)
  private BigDecimal amount;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public FinancialOperation() {
  }

  public FinancialOperation(UserCard card, UserCard destinationCard,
                            OperationType operationType,
                            Currency currency, BigDecimal amount) {
    this.card = card;
    this.destinationCard = destinationCard;
    this.operationType = operationType;
    this.currency = currency;
    this.amount = amount;
    this.createdAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public UserCard getCard() {
    return card;
  }

  public UserCard getDestinationCard() {
    return destinationCard;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
