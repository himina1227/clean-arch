package com.refactoring.cleanarchitecture.account.adapter.out.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, Long> {
}
