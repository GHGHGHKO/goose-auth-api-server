package me.synology.gooseauthapiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;
import me.synology.gooseauthapiserver.configuration.PasswordEncoderConfiguration;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Import(PasswordEncoderConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class SignControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserMasterRepository userMasterRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  @Qualifier("passwordEncoder")
  private PasswordEncoder passwordEncoder;

  @Value("${info.api.id}")
  private String apiUser;

  @BeforeEach
  void setUp() {
    String userEmail = "goose-duck@gmail.com";
    String userPassword = "Honkhonk1122!";
    String userNickname = "goose";

    userMasterRepository.save(
        UserMaster.builder()
            .userEmail(userEmail)
            .userPassword(passwordEncoder.encode(userPassword))
            .userNickname(userNickname)
            .createUser(apiUser)
            .updateUser(apiUser)
            .roles(Collections.singletonList("ROLE_USER"))
            .build());
  }

  @Test
  void signIn() throws Exception {
    Map<String, String> params = new HashMap<>();

    params.put("userEmail", "goose-duck@gmail.com");
    params.put("userPassword", "Honkhonk1122!");

    mockMvc.perform(post("/v1/signIn")
            .content(objectMapper.writeValueAsString(params))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void signUp() throws Exception {
    String signUpTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

    Map<String, String> params = new HashMap<>();

    params.put("userEmail", "goose" + signUpTime + "@gmail.com");
    params.put("userPassword", "Honkhonk1233!");
    params.put("userNickname", "I-AM-NOT-DUCK");

    mockMvc.perform(post("/v1/signUp")
            .content(objectMapper.writeValueAsString(params))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists());
  }
}
