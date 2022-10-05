package me.synology.gooseauthapiserver.repository;

import java.util.List;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.GooseAuthItemsUri;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GooseAuthItemsUriRepository extends JpaRepository<GooseAuthItemsUri, Long> {

  List<GooseAuthItemsUri> findAllByGooseAuthItems(GooseAuthItems gooseAuthItems);

  void deleteByGooseAuthItemsAndUriIdentity(GooseAuthItems gooseAuthItems, Long uriIdentity);
}
