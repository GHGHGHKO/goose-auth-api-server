package me.synology.gooseauthapiserver.service.gooseauth;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemsResponseDto;
import me.synology.gooseauthapiserver.dto.sign.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.ItemsRepository;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import me.synology.gooseauthapiserver.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemsService {

  private final ItemsRepository itemsRepository;

  private final UserMasterRepository userMasterRepository;

  @Value("${info.api.id}")
  private String apiUser;

  public List<GooseAuthGetItemsResponseDto> gooseAuthGetItems(String folder) {
    UserMaster userIdentify = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);
    List<GooseAuthItems> gooseAuthItemsList;

    if (folder == null) {
      gooseAuthItemsList = itemsRepository.findAllByUserMaster(userIdentify);
    } else {
      gooseAuthItemsList = itemsRepository.findAllByUserMasterAndFolder(userIdentify, folder);
    }

    return gooseAuthItemsList.stream()
        .map(gooseAuthItems -> new GooseAuthGetItemsResponseDto(gooseAuthItems.getName(),
            gooseAuthItems.getUserName(),
            gooseAuthItems.getUserPassword(), gooseAuthItems.getUri(), gooseAuthItems.getFolder(),
            gooseAuthItems.getNotes(), gooseAuthItems.getUpdateDate()))
        .collect(Collectors.toList());
  }

  public void gooseAuthAddItem(AddItemRequestDto addItemRequestDto) {

    UserMaster userIdentify = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    itemsRepository.save(
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
