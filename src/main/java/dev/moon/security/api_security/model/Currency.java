package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  private String code;

  @OneToMany(mappedBy = "currency")
  private List<FinancialOperation> financialOperations;

  public Currency() {
  }

  public Currency(String currencyCode) {
    this.code = currencyCode;
  }

  public List<FinancialOperation> getFinancialOperations() {
    return financialOperations;
  }

  public String getCode() {
    return code;
  }
}
