package itemservice.db.config;

import itemservice.db.repository.ItemRepository;
import itemservice.db.repository.jpa.JpaItemRepositoryV2;
import itemservice.db.repository.jpa.SpringDataJpaItemRepository;
import itemservice.db.service.ItemService;
import itemservice.db.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository springDataJpaItemRepository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(springDataJpaItemRepository);
    }

}
