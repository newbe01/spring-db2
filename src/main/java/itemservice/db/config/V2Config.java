package itemservice.db.config;

import itemservice.db.repository.ItemRepository;
import itemservice.db.repository.jpa.JpaItemRepositoryV3;
import itemservice.db.repository.v2.ItemQueryRepositoryV2;
import itemservice.db.repository.v2.ItemRepositoryV2;
import itemservice.db.service.ItemService;
import itemservice.db.service.ItemServiceV2;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 repositoryV2;    // spring data jpa

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(repositoryV2, itemQueryRepositoryV2());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
        return new ItemQueryRepositoryV2(em);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }

}
