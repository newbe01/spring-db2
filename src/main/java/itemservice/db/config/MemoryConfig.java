package itemservice.db.config;

import itemservice.db.repository.ItemRepository;
import itemservice.db.repository.memory.MemoryItemRepository;
import itemservice.db.service.ItemService;
import itemservice.db.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
