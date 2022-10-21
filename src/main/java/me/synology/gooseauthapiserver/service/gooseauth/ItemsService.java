package me.synology.gooseauthapiserver.service.gooseauth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.advice.ItemNotExistException;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.DeleteItemUrisResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthAddUriRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthAddUriResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthGetItemsResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.ItemsUriDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemResponseDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UrisResponseDto;
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
  public DeleteItemUrisResponseDto gooseAuthDeleteItemUris(Long itemIdentity,
      List<Long> uriIdentity) {

    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            CommonUtils.getAuthenticationUserEmail(), itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    uriIdentity.forEach(
        uri -> gooseAuthItemsUriRepository.deleteByGooseAuthItemsAndUriIdentity(gooseAuthItems,
            uri));

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    List<UrisResponseDto> uris = new ArrayList<>();
    gooseAuthItemsUriList.forEach(gooseAuthItemsUri -> uris.add(
        UrisResponseDto.builder()
            .uriIdentity(gooseAuthItemsUri.getUriIdentity())
            .uri(gooseAuthItemsUri.getUri())
            .build()));

    return DeleteItemUrisResponseDto.builder()
        .itemIdentity(gooseAuthItems.getItemIdentity())
        .name(gooseAuthItems.getName())
        .userName(gooseAuthItems.getUserName())
        .userPassword(gooseAuthItems.getUserPassword())
        .notes(gooseAuthItems.getNotes())
        .folder(gooseAuthItems.getFolder())
        .uris(uris)
        .build();
  }

  @Transactional
  public GooseAuthAddUriResponseDto gooseAuthAddItemUris(Long itemIdentity,
      GooseAuthAddUriRequestDto gooseAuthAddUriRequestDto) {
    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            CommonUtils.getAuthenticationUserEmail(), itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    List<UrisResponseDto> uris = new ArrayList<>();
    gooseAuthItemsUriList.forEach(
        uri -> uris.add(UrisResponseDto.builder()
            .uriIdentity(uri.getUriIdentity())
            .uri(uri.getUri())
            .build()));

    gooseAuthAddUriRequestDto.getUri().forEach(uri -> {
      GooseAuthItemsUri gooseAuthItemsUri = gooseAuthItemsUriRepository.save(GooseAuthItemsUri.builder()
          .gooseAuthItems(gooseAuthItems)
          .uri(uri)
          .createUser(apiUser)
          .updateUser(apiUser)
          .build());
      uris.add(UrisResponseDto.builder()
          .uriIdentity(gooseAuthItemsUri.getUriIdentity())
          .uri(gooseAuthItemsUri.getUri())
          .build());
    });

    return GooseAuthAddUriResponseDto.builder()
        .itemIdentity(gooseAuthItems.getItemIdentity())
        .name(gooseAuthItems.getName())
        .userName(gooseAuthItems.getUserName())
        .userPassword(gooseAuthItems.getUserPassword())
        .notes(gooseAuthItems.getNotes())
        .folder(gooseAuthItems.getFolder())
        .uris(uris)
        .build();
  }

  @Transactional
  public void gooseAuthDeleteItem(Long itemIdentity) {
    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            CommonUtils.getAuthenticationUserEmail(), itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    gooseAuthItemsUriRepository.deleteAll(gooseAuthItemsUriList);
    itemsRepository.delete(gooseAuthItems);
  }

  @Transactional
  public UpdateItemResponseDto gooseAuthUpdateItem(Long itemIdentity,
      UpdateItemRequestDto updateItemRequestDto) {
    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            CommonUtils.getAuthenticationUserEmail(), itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    gooseAuthItems.updateItem(updateItemRequestDto);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    List<UrisResponseDto> uris = new ArrayList<>();
    updateItemRequestDto.getUris().forEach(uri ->
        gooseAuthItemsUriList.forEach(gooseAuthItemsUri -> {
          if (uri.getUriIdentity().equals(gooseAuthItemsUri.getUriIdentity())) {
            uris.add(UrisResponseDto.builder()
                .uriIdentity(uri.getUriIdentity())
                .uri(uri.getUri())
                .build());
            gooseAuthItemsUri.updateItemUri(uri.getUri());
            gooseAuthItemsUriRepository.save(gooseAuthItemsUri);
          }
        }));
    itemsRepository.save(gooseAuthItems);

    return UpdateItemResponseDto.builder()
        .itemIdentity(gooseAuthItems.getItemIdentity())
        .name(gooseAuthItems.getName())
        .userName(gooseAuthItems.getUserName())
        .userPassword(gooseAuthItems.getUserPassword())
        .notes(gooseAuthItems.getNotes())
        .folder(gooseAuthItems.getFolder())
        .uris(uris)
        .build();
  }

  @Transactional
  public GooseAuthGetItemResponseDto gooseAuthGetItem(Long itemIdentity) {
    ItemsUriDto itemsUriDto = getItemsUriDto(itemIdentity);

    List<UrisResponseDto> uris = new ArrayList<>();
    itemsUriDto.getGooseAuthItemsUriList().forEach(
        uri -> uris.add(UrisResponseDto.builder()
            .uriIdentity(uri.getUriIdentity())
            .uri(uri.getUri())
            .build()));

    return GooseAuthGetItemResponseDto.builder()
        .itemIdentity(itemIdentity)
        .name(itemsUriDto.getGooseAuthItems().getName())
        .userName(itemsUriDto.getGooseAuthItems().getUserName())
        .userPassword(itemsUriDto.getGooseAuthItems().getUserPassword())
        .notes(itemsUriDto.getGooseAuthItems().getNotes())
        .folder(itemsUriDto.getGooseAuthItems().getFolder())
        .uris(uris)
        .build();
  }

  public List<GooseAuthGetItemsResponseDto> gooseAuthGetItems(String folder) {
    List<GooseAuthItems> gooseAuthItemsList;

    UserMaster userIdentify = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    if (folder == null) {
      gooseAuthItemsList = itemsRepository.findAllByUserMaster(userIdentify);
    } else {
      gooseAuthItemsList = itemsRepository.findAllByUserMasterAndFolder(userIdentify, folder);
    }

    return gooseAuthItemsList.stream()
        .map(gooseAuthItems -> GooseAuthGetItemsResponseDto.builder()
            .itemIdentity(gooseAuthItems.getItemIdentity())
            .name(gooseAuthItems.getName())
            .userName(gooseAuthItems.getUserName())
            .build())
        .collect(Collectors.toList());
  }

  @Transactional
  public AddItemResponseDto gooseAuthAddItem(AddItemRequestDto addItemRequestDto) {
    UserMaster userMaster = userMasterRepository.findByUserEmail(
            CommonUtils.getAuthenticationUserEmail())
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    GooseAuthItems gooseAuthItems = itemsRepository.save(
        GooseAuthItems.builder()
            .userMaster(userMaster)
            .name(addItemRequestDto.getName())
            .userName(addItemRequestDto.getUserName())
            .userPassword(addItemRequestDto.getUserPassword())
            .folder(addItemRequestDto.getFolder())
            .notes(addItemRequestDto.getNotes())
            .createUser(apiUser)
            .updateUser(apiUser)
            .build()
    );

    List<UrisResponseDto> uris = new ArrayList<>();
    addItemRequestDto.getUri().forEach(uri -> {
      GooseAuthItemsUri gooseAuthItemsUri = gooseAuthItemsUriRepository.save(
          GooseAuthItemsUri.builder()
              .gooseAuthItems(gooseAuthItems)
              .uri(uri)
              .createUser(apiUser)
              .updateUser(apiUser)
              .build());
      uris.add(
          UrisResponseDto.builder()
              .uriIdentity(gooseAuthItemsUri.getUriIdentity())
              .uri(gooseAuthItemsUri.getUri())
              .build());
    });

    return AddItemResponseDto.builder()
        .itemIdentity(gooseAuthItems.getItemIdentity())
        .name(gooseAuthItems.getName())
        .userName(gooseAuthItems.getUserName())
        .userPassword(gooseAuthItems.getUserPassword())
        .folder(gooseAuthItems.getFolder())
        .notes(gooseAuthItems.getNotes())
        .uri(uris)
        .build();
  }

  private ItemsUriDto getItemsUriDto(Long itemIdentity) {
    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            CommonUtils.getAuthenticationUserEmail(), itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    return ItemsUriDto.builder()
        .itemIdentity(itemIdentity)
        .gooseAuthItems(gooseAuthItems)
        .gooseAuthItemsUriList(gooseAuthItemsUriList)
        .build();
  }
}
