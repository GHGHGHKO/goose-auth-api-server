package me.synology.gooseauthapiserver.repository;

import java.util.Optional;
import me.synology.gooseauthapiserver.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

  Optional<UserMaster> findByUserEmail(String userEmail);
}
