package itemservice.db;

import itemservice.db.config.MemoryConfig;
import itemservice.db.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import(MemoryConfig.class)
@SpringBootApplication(scanBasePackages = "itemservice.db.web")
public class DbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(ItemRepository itemRepository) {
        return new TestDataInit(itemRepository);
    }


}
