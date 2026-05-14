package dev.moon.security.api_security.model;

public enum PaymentRequestStatus {
  PROCESSED("Operation was created and performed successfully"),
  ACCEPTED("Replay, response with current idempotency key exists"),
  PROCESSING("Operation is processing in other request"),
  INVALID_CURRENCY("Currency doesn't exist. Change currency and try again"),
  INVALID_KEY("Idempotency key reuse with different payload"),
  CONFLICT("Not valid data for payment"),
  BLOCKED("Operation is blocked by permission");

  private final String statusDescription;

  PaymentRequestStatus(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public String getStatusDescription() {
    return statusDescription;
  }
}
