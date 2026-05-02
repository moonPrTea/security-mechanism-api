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

  @Column(name = "operation_type")
  private String operationType;

  @OneToMany(mappedBy = "operationType")
  private List<FinancialOperation> financialOperations;


  public OperationType() {
  }

  public OperationType(String operationType) {
    this.operationType = operationType;
  }

  public String getOperationType() {
    return operationType;
  }
}
