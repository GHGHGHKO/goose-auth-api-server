package me.synology.gooseauthapiserver.repository;

import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<GooseAuthItems, Long> {

}
