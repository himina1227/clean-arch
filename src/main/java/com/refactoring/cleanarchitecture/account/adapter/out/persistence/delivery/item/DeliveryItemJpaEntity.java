package com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery.item;

import com.refactoring.cleanarchitecture.account.domain.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class DeliveryItemJpaEntity {

    private String name;

    private Long quantity;

    private BigDecimal amount;
}
