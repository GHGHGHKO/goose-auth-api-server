package me.synology.gooseauthapiserver.repository;

import java.util.Optional;
import me.synology.gooseauthapiserver.entity.GooseAuthUserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GooseAuthUserMasterRepository extends JpaRepository<GooseAuthUserMaster, Long> {

  Optional<GooseAuthUserMaster> findByUserEmail(String userEmail);
}
