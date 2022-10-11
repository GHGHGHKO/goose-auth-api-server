package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.GooseAuthItemsUri;

@Getter
@Builder
public class ItemsUriDto {

  private Long itemIdentity;

  private GooseAuthItems gooseAuthItems;

  private List<GooseAuthItemsUri> gooseAuthItemsUriList;
}
