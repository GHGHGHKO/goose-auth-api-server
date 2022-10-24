package me.synology.gooseauthapiserver.controller.gooseauth;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import me.synology.gooseauthapiserver.advice.EmailSignInFailedExceptionCustom;
import me.synology.gooseauthapiserver.advice.ItemNotExistException;
import me.synology.gooseauthapiserver.dto.gooseauth.AddItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.GooseAuthAddUriRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemRequestDto;
import me.synology.gooseauthapiserver.dto.gooseauth.UrisRequestDto;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.GooseAuthItemsUri;
import me.synology.gooseauthapiserver.entity.UserMaster;
import me.synology.gooseauthapiserver.repository.GooseAuthItemsUriRepository;
import me.synology.gooseauthapiserver.repository.ItemsRepository;
import me.synology.gooseauthapiserver.repository.UserMasterRepository;
import me.synology.gooseauthapiserver.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ItemsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private SignService signService;

  @Autowired
  private UserMasterRepository userMasterRepository;

  @Autowired
  private ItemsRepository itemsRepository;

  @Autowired
  private GooseAuthItemsUriRepository gooseAuthItemsUriRepository;

  private static final String USER_EMAIL = "goose-duck@gmail.com";
  private static final String USER_PASSWORD = "Honkhonk1122!";
  private static final String USER_NICKNAME = "duck";

  private String token;
  private GooseAuthItems gooseAuthItems;

  @BeforeEach
  void signAndAddItem() {
    signService.signUp(USER_EMAIL, USER_PASSWORD, USER_NICKNAME);
    token = signService.signIn(USER_EMAIL, USER_PASSWORD);

    List<String> uris = new ArrayList<>();
    uris.add("https://www.youtube.com/watch?v=ZZ5LpwO-An4&list=PLV2ewAgCPCq0DVamOw2sQSAVdFVjA6x78");
    uris.add(
        "https://www.youtube.com/watch?v=J9FImc2LOr8&list=PLV2ewAgCPCq0DVamOw2sQSAVdFVjA6x78&index=67");

    AddItemRequestDto addItemRequestDto = new AddItemRequestDto();
    addItemRequestDto.setName("goose");
    addItemRequestDto.setUserName("goose@gmail.com");
    addItemRequestDto.setUserPassword("Honkhonk12!");
    addItemRequestDto.setFolder("goosegoose-auth");
    addItemRequestDto.setNotes("I hate duck!!!!!");
    addItemRequestDto.setUri(uris);

    UserMaster userMaster = userMasterRepository.findByUserEmail(USER_EMAIL)
        .orElseThrow(EmailSignInFailedExceptionCustom::new);

    gooseAuthItems = itemsRepository.save(
        GooseAuthItems.builder()
            .userMaster(userMaster)
            .name(addItemRequestDto.getName())
            .userName(addItemRequestDto.getUserName())
            .userPassword(addItemRequestDto.getUserPassword())
            .folder(addItemRequestDto.getFolder())
            .notes(addItemRequestDto.getNotes())
            .createUser(USER_NICKNAME)
            .updateUser(USER_NICKNAME)
            .build());

    addItemRequestDto.getUri().forEach(uri -> gooseAuthItemsUriRepository.save(
        GooseAuthItemsUri.builder()
            .gooseAuthItems(gooseAuthItems)
            .uri(uri)
            .createUser(USER_NICKNAME)
            .updateUser(USER_NICKNAME)
            .build()));
  }

  @Test
  void gooseAuthAddItem() throws Exception {
    List<String> uris = new ArrayList<>();
    uris.add("https://www.youtube.com/watch?v=1P5yyeeYF9o");
    uris.add("https://www.youtube.com/watch?v=dQw4w9WgXcQ");

    AddItemRequestDto addItemRequestDto = new AddItemRequestDto();
    addItemRequestDto.setName("duck");
    addItemRequestDto.setUserName("duck@goose.com");
    addItemRequestDto.setUserPassword("Quarkquark12!");
    addItemRequestDto.setFolder("goose");
    addItemRequestDto.setNotes("I hate goose");
    addItemRequestDto.setUri(uris);

    mockMvc.perform(post("/v1/gooseAuth/addItems")
            .content(objectMapper.writeValueAsString(addItemRequestDto))
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void gooseAuthItems() throws Exception {
    mockMvc.perform(get("/v1/gooseAuth/items")
        .contentType(MediaType.APPLICATION_JSON)
        .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.list").isNotEmpty());
  }

  @Test
  void gooseAuthItem() throws Exception {
    Long itemIdentity = gooseAuthItems.getItemIdentity();
    mockMvc.perform(get("/v1/gooseAuth/items/" + itemIdentity)
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data").exists());
  }

  @Test
  void gooseAuthUpdateItem() throws Exception {
    Long itemIdentity = gooseAuthItems.getItemIdentity();
    GooseAuthItems gooseAuthItems = itemsRepository.findByUserEmailAndItemIdentity(
            USER_EMAIL, itemIdentity)
        .orElseThrow(ItemNotExistException::new);

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    List<UrisRequestDto> urisRequestDtoList = new ArrayList<>();
    gooseAuthItemsUriList.forEach(gooseAuthItemsUri ->
        urisRequestDtoList.add(
            UrisRequestDto.builder()
            .uriIdentity(gooseAuthItemsUri.getUriIdentity())
            .uri(gooseAuthItemsUri.getUri())
            .build()));

    UpdateItemRequestDto updateItemRequestDto = UpdateItemRequestDto.builder()
        .name(gooseAuthItems.getName() + "modified test!")
        .userName(gooseAuthItems.getUserName())
        .userPassword(gooseAuthItems.getUserPassword())
        .folder(gooseAuthItems.getFolder())
        .notes(gooseAuthItems.getNotes() + "modified test!")
        .uris(urisRequestDtoList)
        .build();

    mockMvc.perform(put("/v1/gooseAuth/items/{itemIdentity}", itemIdentity)
            .content(objectMapper.writeValueAsString(updateItemRequestDto))
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data").exists())
        .andExpect(jsonPath("$.data.name").value(equalTo(updateItemRequestDto.getName())))
        .andExpect(jsonPath("$.data.notes").value(equalTo(updateItemRequestDto.getNotes())));
  }

  @Test
  void gooseAuthAddItemUris() throws Exception {
    Long itemIdentity = gooseAuthItems.getItemIdentity();

    List<String> uris = new ArrayList<>();
    uris.add("https://pepega.tistory.com/");
    uris.add("https://github.com/");

    GooseAuthAddUriRequestDto gooseAuthAddUriRequestDto = new GooseAuthAddUriRequestDto();
    gooseAuthAddUriRequestDto.setUri(uris);

    mockMvc.perform(post("/v1/gooseAuth/items/{itemIdentity}", itemIdentity)
            .content(objectMapper.writeValueAsString(gooseAuthAddUriRequestDto))
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data.uris", hasSize(4)));
  }

  @Test
  void gooseAuthDeleteItemUris() throws Exception {
    Long itemIdentity = gooseAuthItems.getItemIdentity();

    List<GooseAuthItemsUri> gooseAuthItemsUriList = gooseAuthItemsUriRepository.findAllByGooseAuthItems(
        gooseAuthItems);

    Long firstUriIdentity = gooseAuthItemsUriList.get(0).getUriIdentity();
    Long secondUriIdentity = gooseAuthItemsUriList.get(1).getUriIdentity();

    mockMvc.perform(delete("/v1/gooseAuth/items/{itemIdentity}", itemIdentity)
            .param("uriIdentity", String.valueOf(firstUriIdentity))
            .param("uriIdentity", String.valueOf(secondUriIdentity))
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.data.uris", hasSize(0)));
  }

  @Test
  void gooseAuthDeleteItem() throws Exception {
    Long itemIdentity = gooseAuthItems.getItemIdentity();

    mockMvc.perform(delete("/v1/gooseAuth/items")
            .param("itemIdentity", String.valueOf(itemIdentity))
            .contentType(MediaType.APPLICATION_JSON)
            .header("X-AUTH-TOKEN", token))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists());
  }
}
