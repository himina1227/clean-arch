package com.refactoring.cleanarchitecture.account.adapter.out.persistence.address;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class AddressJpaEntity {

    private String address;

    private String ji;

    private String bun;
}
