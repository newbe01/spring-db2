package db.transaction.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager manager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("== start tx ==");
        TransactionStatus status = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx commit ==");
        manager.commit(status);
        log.info("== commit ==");

    }

    @Test
    void rollback() {
        log.info("== start tx ==");
        TransactionStatus status = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx rollback ==");
        manager.rollback(status);
        log.info("== rollback ==");

    }
}
