package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "operation_type")
public class OperationType {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "operation")
  private String operation;

  @OneToMany(mappedBy = "operationType")
  private List<FinancialOperation> financialOperations;


  public OperationType() {
  }

  public UUID getId() {
    return id;
  }

  public OperationType(String operationType) {
    this.operation = operationType;
  }

  public String getOperationType() {
    return operation;
  }
}
