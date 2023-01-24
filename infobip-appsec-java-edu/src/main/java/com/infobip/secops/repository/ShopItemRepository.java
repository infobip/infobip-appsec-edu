package com.infobip.secops.repository;

import com.infobip.secops.model.ShopItem;
import org.springframework.data.repository.CrudRepository;

public interface ShopItemRepository extends CrudRepository<ShopItem, Long> {
}
