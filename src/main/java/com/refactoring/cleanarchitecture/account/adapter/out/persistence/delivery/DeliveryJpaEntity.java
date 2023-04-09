package com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery;

import com.refactoring.cleanarchitecture.account.adapter.out.persistence.address.AddressJpaEntity;
import com.refactoring.cleanarchitecture.account.adapter.out.persistence.delivery.item.DeliveryItemJpaEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "delivery")
public class DeliveryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AddressJpaEntity address;

    @ElementCollection
    @CollectionTable(name = "delivery_item", joinColumns = @JoinColumn(name = "delivery_id"))
    private List<DeliveryItemJpaEntity> deliveryItems = new ArrayList<>();
}

