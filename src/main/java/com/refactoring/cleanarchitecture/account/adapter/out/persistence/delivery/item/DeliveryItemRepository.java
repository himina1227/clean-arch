package com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery.item;

import com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery.DeliveryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryItemRepository extends JpaRepository<DeliveryJpaEntity, Long> {
}
