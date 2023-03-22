package com.refactoring.cleanarchitecture.account.application.in;

import com.refactoring.cleanarchitecture.common.SelfValidating;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
}
