package me.synology.gooseauthapiserver.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import me.synology.gooseauthapiserver.configuration.PasswordEncoderConfiguration;
import me.synology.gooseauthapiserver.entity.UserMaster;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@Import(PasswordEncoderConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserMasterRepositoryTest {

  @Autowired
  private UserMasterRepository userMasterRepository;

  @Autowired
  @Qualifier("passwordEncoder")
  private PasswordEncoder passwordEncoder;

  @Value("${info.api.id}")
  private String apiUser;

  @Test
  void findByUserEmail() {

    String userEmail = "duck@gmail.com";
    String userPassword = "HONKHONK!";
    String userNickname = "goose";

    // given
    userMasterRepository.save(
        UserMaster.builder()
            .userEmail(userEmail)
            .userPassword(passwordEncoder.encode(userPassword))
            .userNickname(userNickname)
            .createUser(apiUser)
            .updateUser(apiUser)
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

    // when
    Optional<UserMaster> optionalUserMaster = userMasterRepository.findByUserEmail(userEmail);

    // then
    assertNotNull(optionalUserMaster);
    assertTrue(optionalUserMaster.isPresent());
    assertEquals(optionalUserMaster.get().getUserEmail(), userEmail);
  }
}