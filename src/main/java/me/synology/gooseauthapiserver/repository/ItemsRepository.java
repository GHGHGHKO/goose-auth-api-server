package me.synology.gooseauthapiserver.repository;

import java.util.List;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import me.synology.gooseauthapiserver.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<GooseAuthItems, Long> {

  List<GooseAuthItems> findAllByUserMaster(UserMaster userMaster);

  List<GooseAuthItems> findAllByUserMasterAndFolder(UserMaster userMaster, String folder);
}