package db.transaction.apply;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class InitTxTest {

    @Autowired Tmp tmp;

    @Test
    void initTest() {
        // 초기화 코드는 스프링이 초기화시점에 호출한다.
//        tmp.initV1(); 직접호출시 트랜잭션 적용됨
    }

    @TestConfiguration
    static class InitTxTestConfig {
        @Bean
        Tmp tmp() {
            return new Tmp();
        }
    }

    @Slf4j
    static class Tmp {

        @Transactional
        @PostConstruct
        public void initV1() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tmp init @PostConstruct tx active :: {}", isActive);
        }

        @Transactional
        @EventListener(ApplicationReadyEvent.class)
        public void initV2() {
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tmp init @EventListener tx active :: {}", isActive);
        }
    }
}
