package me.synology.gooseauthapiserver.repository;

import java.util.Optional;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;

public interface ItemsRepositoryCustom {

  Optional<GooseAuthItems> findByUserEmailAndItemIdentity(String userEmail,
      Long itemIdentity);
}
