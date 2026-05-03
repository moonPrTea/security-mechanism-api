package dev.moon.security.api_security.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKeys {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "idempotency_key", unique = true, nullable = false)
  private String idempotencyKey;

  @Column(name = "request_hash")
  private String requestHash;

  @Column(name = "response_body")
  private String responseBody;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public IdempotencyKeys() {
  }

  public IdempotencyKeys(UUID id, String idempotencyKey, String requestHash, String responseBody) {
    this.id = id;
    this.idempotencyKey = idempotencyKey;
    this.requestHash = requestHash;
    this.responseBody = responseBody;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = null;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public String getRequestHash() {
    return requestHash;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public void setRequestHash(String requestHash) {
    this.requestHash = requestHash;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
