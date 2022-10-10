package me.synology.gooseauthapiserver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.entity.GooseAuthItems;
import org.springframework.stereotype.Repository;

import static me.synology.gooseauthapiserver.entity.QUserMaster.userMaster;
import static me.synology.gooseauthapiserver.entity.QGooseAuthItems.gooseAuthItems;


@Repository
@RequiredArgsConstructor
public class ItemsRepositoryImpl implements ItemsRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<GooseAuthItems> findByUserEmailAndItemIdentity(String userEmail,
      Long itemIdentity) {
    return Optional.ofNullable(queryFactory.selectFrom(gooseAuthItems)
        .join(userMaster).on(gooseAuthItems.userMaster.userIdentity.eq(userMaster.userIdentity))
        .where(userMaster.userEmail.eq(userEmail)
            .and(gooseAuthItems.itemIdentity.eq(itemIdentity)))
        .fetchOne());
  }
}
