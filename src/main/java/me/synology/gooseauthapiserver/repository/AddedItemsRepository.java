package me.synology.gooseauthapiserver.repository;

import me.synology.gooseauthapiserver.entity.AddedItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddedItemsRepository extends JpaRepository<AddedItems, Long> {

}
