package me.synology.gooseauthapiserver.controller;

import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.service.UserMasterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserMasterController {

    private final UserMasterService userMasterService;

    @GetMapping(value = "/users")
    public List<UserMaster> findAllUsers() {
        return userMasterService.findAllUsers();
    }

    @PostMapping(value = "/users")
    public UserMaster saveUsers() {
        return userMasterService.saveUser();
    }
}
