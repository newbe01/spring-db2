package itemservice.db.service;

import itemservice.db.domain.Item;
import itemservice.db.repository.ItemSearchCond;
import itemservice.db.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);

}
