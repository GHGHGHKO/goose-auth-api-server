package me.synology.gooseauthapiserver.service.gooseauth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.advice.ItemNotExistException;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthAddUriRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthAddUriResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemResponseDto.Uris;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemsResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemResponseDto;
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
  public GooseAuthAddUriResponseDto gooseAuthAddItemUris(Long itemIdentity,
      GooseAuthAddUriRequestDto gooseAuthAddUriRequestDto) {
    GooseAuthItems gooseAuthItems = getGooseAuthItems(itemIdentity);
    List<GooseAuthItemsUri> gooseAuthItemsUriList = getGooseAuthItemsUriList(itemIdentity);

    List<String> uris = new ArrayList<>();
    gooseAuthItemsUriList.forEach(gooseAuthItemsUri -> uris.add(gooseAuthItemsUri.getUri()));
    gooseAuthAddUriRequestDto.getUri().forEach(uri -> {
      uris.add(uri);
      gooseAuthItemsUriRepository.save(GooseAuthItemsUri.builder()
          .gooseAuthItems(gooseAuthItems)
          .uri(uri)
          .createUser(apiUser)
          .updateUser(apiUser)
          .build());
    });
    return new GooseAuthAddUriResponseDto(gooseAuthItems.getName(), gooseAuthItems.getUserName(),
        gooseAuthItems.getUserPassword(), gooseAuthItems.getNotes(), gooseAuthItems.getFolder(),
        uris);
  }

  @Transactional
  public void gooseAuthDeleteItem(Long itemIdentity) {
    GooseAuthItems gooseAuthItems = getGooseAuthItems(itemIdentity);
    gooseAuthItemsUriRepository.deleteAll(getGooseAuthItemsUriList(itemIdentity));
    itemsRepository.delete(gooseAuthItems);
  }

  @Transactional
  public UpdateItemResponseDto gooseAuthUpdateItem(Long itemIdentity,
      UpdateItemRequestDto updateItemRequestDto) {
    GooseAuthItems gooseAuthItems = getGooseAuthItems(itemIdentity);

    gooseAuthItems.updateItem(updateItemRequestDto);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = getGooseAuthItemsUriList(itemIdentity);

    List<String> uris = new ArrayList<>();
    for (int index = 0; index < gooseAuthItemsUriList.size(); index++) {
      uris.add(updateItemRequestDto.getUri().get(index));
      gooseAuthItemsUriList.get(index).updateItemUri(updateItemRequestDto.getUri().get(index));
      gooseAuthItemsUriRepository.save(gooseAuthItemsUriList.get(index));
    }
    itemsRepository.save(gooseAuthItems);

    return new UpdateItemResponseDto(gooseAuthItems.getName(), gooseAuthItems.getUserName(),
        gooseAuthItems.getUserPassword(), gooseAuthItems.getNotes(), gooseAuthItems.getFolder(),
        uris);
  }

  @Transactional
  public GooseAuthGetItemResponseDto gooseAuthGetItem(Long itemIdentity) {
    GooseAuthItems gooseAuthItems = getGooseAuthItems(itemIdentity);

    List<Uris> uris = new ArrayList<>();
    getGooseAuthItemsUriList(itemIdentity).forEach(
        uri -> uris.add(new Uris(uri.getUriIdentity(), uri.getUri())));

    return new GooseAuthGetItemResponseDto(gooseAuthItems.getName(), gooseAuthItems.getUserName(),
        gooseAuthItems.getUserPassword(), gooseAuthItems.getNotes(), gooseAuthItems.getFolder(),
        uris);
  }

  public List<GooseAuthGetItemsResponseDto> gooseAuthGetItems(String folder) {
    List<GooseAuthItems> gooseAuthItemsList;

    UserMaster userIdentify = getUserIdentify();

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
    GooseAuthItems gooseAuthItems = itemsRepository.save(
        GooseAuthItems.builder()
            .userMaster(getUserIdentify())
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

  private UserMaster getUserIdentify() {
    return userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);
  }

  private GooseAuthItems getGooseAuthItems(Long itemIdentity) {
    return itemsRepository.findAllByUserMasterAndItemIdentity(getUserIdentify(),
        itemIdentity).orElseThrow(
        ItemNotExistException::new);
  }

  private List<GooseAuthItemsUri> getGooseAuthItemsUriList(Long itemIdentity) {
    return gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        getGooseAuthItems(itemIdentity));
  }
}
