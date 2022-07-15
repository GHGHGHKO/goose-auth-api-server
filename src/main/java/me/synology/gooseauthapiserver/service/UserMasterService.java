package me.synology.gooseauthapiserver.service;

import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMasterService {

    private final UserMasterRepository userMasterRepository;

    public List<UserMaster> findAllUsers() {
        return userMasterRepository.findAll();
    }

    public UserMaster saveUser() {
        UserMaster userMaster = UserMaster.builder()
                .userEmail("goose@sample.com")
                .userPassword("q45gw456yh!")
                .userNickname("꽉!")
                .build();

        return userMasterRepository.save(userMaster);
    }
}
