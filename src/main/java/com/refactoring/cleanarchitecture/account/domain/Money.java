package com.refactoring.cleanarchitecture.account.domain;

import lombok.Value;

import java.math.BigInteger;

@Value // 불변객체로 만들어 주는 어노테이션
public class Money {

    public static Money ZERO = Money.of(0L);

    private final BigInteger amount;

    public static Money of(Long value) {
        return new Money(BigInteger.valueOf(value));
    }
}
