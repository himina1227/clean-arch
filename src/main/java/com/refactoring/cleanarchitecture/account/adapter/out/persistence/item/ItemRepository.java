package com.refactoring.cleanarchitecture.account.adapter.out.persistence.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemJpaEntity, Long> {
}
