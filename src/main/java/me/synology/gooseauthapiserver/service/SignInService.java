package me.synology.gooseauthapiserver.service;

import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.configuration.security.JwtTokenProvider;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {

  private final UserMasterRepository userMasterRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider jwtTokenProvider;

  public String signIn(String userEmail, String password) {
    UserMaster userMaster = userMasterRepository.findByUserEmail(userEmail).orElseThrow(
        EmailSignInFailedExceptionCustom::new);

    if (!passwordEncoder.matches(password, userMaster.getPassword())) {
      throw new EmailSignInFailedExceptionCustom();
    }

    return jwtTokenProvider.createToken(String.valueOf(userMaster.getUserIdentity()),
        userMaster.getRoles());
  }

}
