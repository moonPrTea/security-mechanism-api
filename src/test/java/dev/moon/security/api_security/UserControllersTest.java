package dev.moon.security.api_security;

import dev.moon.security.api_security.controller.UserController;

import dev.moon.security.api_security.model.User;
import dev.moon.security.api_security.service.UserService;
import dto.BaseResponse;
import dto.CreateUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureRestTestClient
class UserControllersTest {
  @Autowired
  private RestTestClient restTestClient;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  UserService userService;

  @Test
  void testStatusRouteSuccessfully() {
    restTestClient.get()
            .uri("/user/status")
            .exchange()
            .expectStatus().isOk()
            .expectBody(BaseResponse.class)
            .isEqualTo(new BaseResponse("success", "Route is working"));
  }

  @Test
  void getUsersShouldReturnNotFoundStatus() {
    restTestClient.get()
            .uri("/user")
            .exchange()
            .expectStatus().isNotFound();
  }

  @Test
  void createUserSuccessfully() throws Exception {
    CreateUserDto userDto = new CreateUserDto("Ryan", "Gosling", null);
    User expectedUser = new User(1, "Ryan", "Gosling", null, Instant.now());

    when(userService.createUser(any(CreateUserDto.class))).thenReturn(expectedUser);

    mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk());

    when(userService.getUserWithId(1)).thenReturn(expectedUser);

    mockMvc.perform(get("/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Ryan"))
            .andExpect(jsonPath("$.secondName").value("Gosling"));

  }

}
