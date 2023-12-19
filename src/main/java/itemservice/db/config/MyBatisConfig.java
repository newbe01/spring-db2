package itemservice.db.config;

import itemservice.db.repository.ItemRepository;
import itemservice.db.repository.mybatis.ItemMapper;
import itemservice.db.repository.mybatis.MyBatisItemRepository;
import itemservice.db.service.ItemService;
import itemservice.db.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final ItemMapper itemMapper;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MyBatisItemRepository(itemMapper);
    }

}
