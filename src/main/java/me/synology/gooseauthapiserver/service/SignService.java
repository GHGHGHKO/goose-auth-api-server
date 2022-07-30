package me.synology.gooseauthapiserver.service;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.configuration.security.JwtTokenProvider;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

  private final UserMasterRepository userMasterRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider jwtTokenProvider;

  @Value("${info.api.id}")
  private String apiUser;

  public String signIn(String userEmail, String userPassword) {
    UserMaster userMaster = userMasterRepository.findByUserEmail(userEmail).orElseThrow(
        EmailSignInFailedExceptionCustom::new);

    if (!passwordEncoder.matches(userPassword, userMaster.getPassword())) {
      throw new EmailSignInFailedExceptionCustom();
    }

    return jwtTokenProvider.createToken(String.valueOf(userMaster.getUserIdentity()),
        userMaster.getRoles());
  }

  public void signUp(String userEmail, String userPassword, String userNickname) {
    userMasterRepository.save(
        UserMaster.builder()
            .userEmail(userEmail)
            .userPassword(passwordEncoder.encode(userPassword))
            .userNickname(userNickname)
            .createUser(apiUser)
            .createDate(LocalDateTime.now())
            .updateUser(apiUser)
            .updateDate(LocalDateTime.now())
            .roles(Collections.singletonList("ROLE_USER"))
            .build()
    );
  }

}
