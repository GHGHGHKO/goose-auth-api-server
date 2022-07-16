package me.synology.gooseauthapiserver.service;

import lombok.RequiredArgsConstructor;
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

    public List<UserMaster> findAllUsers() {
        return userMasterRepository.findAll();
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
