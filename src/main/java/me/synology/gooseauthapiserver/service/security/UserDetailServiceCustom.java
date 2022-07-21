package me.synology.gooseauthapiserver.service.security;

import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.UserNotFoundExceptionCustom;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceCustom implements UserDetailsService {

  private final UserMasterRepository userMasterRepository;

  public UserDetails loadUserByUsername(String userPrimaryKey) {
    return userMasterRepository.findById(Long.valueOf(userPrimaryKey)).orElseThrow(
        UserNotFoundExceptionCustom::new);
  }
}
