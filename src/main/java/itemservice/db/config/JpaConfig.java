package itemservice.db.config;

import itemservice.db.repository.ItemRepository;
import itemservice.db.repository.jpa.JpaItemRepository;
import itemservice.db.service.ItemService;
import itemservice.db.service.ItemServiceV1;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager em;
    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }

}
