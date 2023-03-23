package com.refactoring.cleanarchitecture.account.adapter.out.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountRepository extends JpaRepository<AccountJpaEntity, Long> {
}
