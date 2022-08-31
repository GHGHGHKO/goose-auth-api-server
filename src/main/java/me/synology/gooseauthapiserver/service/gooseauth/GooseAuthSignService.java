package me.synology.gooseauthapiserver.service.gooseauth;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.advice.UserExistExceptionCustom;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.GooseAuthSignInRequestDto;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.GooseAuthSignInResponseDto;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.GooseAuthSignUpRequestDto;
import me.synology.gooseauthapiserver.entity.GooseAuthUserMaster;
import me.synology.gooseauthapiserver.repository.GooseAuthUserMasterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GooseAuthSignService {
  private final PasswordEncoder passwordEncoder;

  private final GooseAuthUserMasterRepository gooseAuthUserMasterRepository;

  @Value("${info.api.id}")
  private String apiUser;

  public void gooseAuthSignUp(GooseAuthSignUpRequestDto gooseAuthSignUpRequestDto) {
    Optional<GooseAuthUserMaster> gooseAuthUserMaster = gooseAuthUserMasterRepository.findByUserEmail(
        gooseAuthSignUpRequestDto.getUserEmail());

    if (gooseAuthUserMaster.isPresent()) {
      throw new UserExistExceptionCustom();
    }

    gooseAuthUserMasterRepository.save(
        GooseAuthUserMaster.builder()
            .userEmail(gooseAuthSignUpRequestDto.getUserEmail())
            .userPassword(passwordEncoder.encode(gooseAuthSignUpRequestDto.getUserPassword()))
            .userNickname(gooseAuthSignUpRequestDto.getUserNickname())
            .userNickname(gooseAuthSignUpRequestDto.getUserNickname())
            .passwordHint(gooseAuthSignUpRequestDto.getPasswordHint())
            .createUser(apiUser)
            .createDate(LocalDateTime.now())
            .updateUser(apiUser)
            .updateDate(LocalDateTime.now())
            .build()
    );
  }

  public GooseAuthSignInResponseDto gooseAuthSignIn(
      GooseAuthSignInRequestDto gooseAuthSignInRequestDto) {
    GooseAuthUserMaster gooseAuthUserMaster = gooseAuthUserMasterRepository.findByUserEmail(
            gooseAuthSignInRequestDto.getUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    if (!passwordEncoder.matches(gooseAuthSignInRequestDto.getUserPassword(),
        gooseAuthUserMaster.getUserPassword())) {
      throw new EmailSignInFailedExceptionCustom();
    }

    return GooseAuthSignInResponseDto.builder()
        .userEmail(gooseAuthUserMaster.getUserEmail())
        .passwordHint(gooseAuthUserMaster.getPasswordHint())
        .build();
  }
}
