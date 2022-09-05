package me.synology.gooseauthapiserver.repository;

import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddedItemsRepository extends JpaRepository<GooseAuthItems, Long> {

}
