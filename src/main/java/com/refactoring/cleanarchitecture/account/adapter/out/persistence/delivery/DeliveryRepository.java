package com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryJpaEntity, Long> {
}
