package me.synology.gooseauthapiserver.service.gooseauth;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.AddedItemsRepository;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemsService {

  private final AddedItemsRepository addedItemsRepository;

  private final UserMasterRepository userMasterRepository;

  @Value("${info.api.id}")
  private String apiUser;

  public void gooseAuthAddItem(AddItemRequestDto addItemRequestDto) {

    UserMaster userIdentify = userMasterRepository.findByUserEmail(
        addItemRequestDto.getUserEmail()).orElseThrow(EmailSignInFailedExceptionCustom::new);

    addedItemsRepository.save(
        GooseAuthItems.builder()
            .userMaster(userIdentify)
            .name(addItemRequestDto.getName())
            .userName(addItemRequestDto.getUserName())
            .userPassword(addItemRequestDto.getUserPassword())
            .folder(addItemRequestDto.getFolder())
            .notes(addItemRequestDto.getNotes())
            .uri(addItemRequestDto.getUri())
            .createUser(apiUser)
            .createDate(LocalDateTime.now())
            .updateUser(apiUser)
            .updateDate(LocalDateTime.now())
            .build()
    );
  }
}
