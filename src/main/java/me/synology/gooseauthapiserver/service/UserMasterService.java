package me.synology.gooseauthapiserver.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.UserNotFoundExceptionCustom;
import me.synology.gooseauthapiserver.dto.sign.UserMasterResponseDto;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMasterService {

  private final UserMasterRepository userMasterRepository;

  @Value("${info.api.id}")
  private String apiUser;

  public List<UserMasterResponseDto> findAllUsers() {
    List<UserMaster> userMasterList = userMasterRepository.findAll();

    return userMasterList.stream()
        .map(userMaster -> new UserMasterResponseDto(
            userMaster.getUserEmail(), userMaster.getUserNickname(),
            userMaster.getRoles(), userMaster.getUpdateDate()
        ))
        .collect(Collectors.toList());
  }

  public UserMasterResponseDto findUserByIdentity(Long userIdentity) {
    UserMaster userMaster = userMasterRepository.findById(userIdentity)
        .orElseThrow(UserNotFoundExceptionCustom::new);

    return new UserMasterResponseDto(
        userMaster.getUserEmail(), userMaster.getUserNickname(),
        userMaster.getRoles(), userMaster.getUpdateDate());
  }

  public UserMaster saveUser(String userEmail, String userPassword, String userNickname) {
    UserMaster userMaster = UserMaster.builder()
        .userEmail(userEmail)
        .userPassword(userPassword)
        .userNickname(userNickname)
        .createUser(apiUser)
        .createDate(LocalDateTime.now())
        .updateUser(apiUser)
        .updateDate(LocalDateTime.now())
        .build();

    return userMasterRepository.save(userMaster);
  }
}
