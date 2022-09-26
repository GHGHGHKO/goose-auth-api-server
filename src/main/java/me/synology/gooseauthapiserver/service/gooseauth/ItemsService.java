package me.synology.gooseauthapiserver.service.gooseauth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.advice.ItemNotExistException;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemsResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.GooseAuthItemsUri;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.GooseAuthItemsUriRepository;
import me.synology.gooseauthapiserver.repository.ItemsRepository;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import me.synology.gooseauthapiserver.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemsService {

  private final ItemsRepository itemsRepository;

  private final UserMasterRepository userMasterRepository;

  private final GooseAuthItemsUriRepository gooseAuthItemsUriRepository;

  @Value("${info.api.id}")
  private String apiUser;

  @Transactional
  public GooseAuthGetItemResponseDto gooseAuthGetItem(Long itemIdentity) {
    UserMaster userIdentify = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    GooseAuthItems gooseAuthItems = itemsRepository.findAllByUserMasterAndItemIdentity(userIdentify,
        itemIdentity).orElseThrow(
        ItemNotExistException::new);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    List<String> uris = new ArrayList<>();
    gooseAuthItemsUriList.forEach(uri -> uris.add(uri.getUri()));

    return new GooseAuthGetItemResponseDto(gooseAuthItems.getName(), gooseAuthItems.getUserName(),
        gooseAuthItems.getUserPassword(), gooseAuthItems.getNotes(), gooseAuthItems.getFolder(),
        uris);
  }
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
        .map(gooseAuthItems -> new GooseAuthGetItemsResponseDto(gooseAuthItems.getItemIdentity(),
            gooseAuthItems.getName(), gooseAuthItems.getUserName()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void gooseAuthAddItem(AddItemRequestDto addItemRequestDto) {

    UserMaster userIdentify = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    GooseAuthItems gooseAuthItems = itemsRepository.save(
        GooseAuthItems.builder()
            .userMaster(userIdentify)
            .name(addItemRequestDto.getName())
            .userName(addItemRequestDto.getUserName())
            .userPassword(addItemRequestDto.getUserPassword())
            .folder(addItemRequestDto.getFolder())
            .notes(addItemRequestDto.getNotes())
            .createUser(apiUser)
            .updateUser(apiUser)
            .build()
    );

    addItemRequestDto.getUri().forEach(uri -> gooseAuthItemsUriRepository.save(
        GooseAuthItemsUri.builder()
            .gooseAuthItems(gooseAuthItems)
            .uri(uri)
            .createUser(apiUser)
            .updateUser(apiUser)
            .build()
    ));
  }
}
