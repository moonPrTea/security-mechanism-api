package dev.moon.security.api_security.manager;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@Component
public class HashManager {
  private final ObjectMapper objectMapper;

  public HashManager(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public String getDtoHash(Object requestDto) {
    String json = objectMapper.writeValueAsString(requestDto);

    return DigestUtils.sha256Hex(json.getBytes(StandardCharsets.UTF_8));
  }
}
