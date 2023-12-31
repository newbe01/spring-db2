package db.transaction.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void double_commit() {
        log.info("== start tx1 ==");
        TransactionStatus tx1 = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx1 commit ==");
        manager.commit(tx1);

        log.info("== start tx2 ==");
        TransactionStatus tx2 = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx2 commit ==");
        manager.commit(tx2);
    }

    @Test
    void double_commit_rollback() {
        log.info("== start tx1 ==");
        TransactionStatus tx1 = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx1 commit ==");
        manager.commit(tx1);

        log.info("== start tx2 ==");
        TransactionStatus tx2 = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== start tx2 rollback ==");
        manager.rollback(tx2);
     }

    @Test
    void inner_commit() {
        log.info("== outer tx start ==");
        TransactionStatus outer = manager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTx() :: {}", outer.isNewTransaction());

        log.info("== inner tx start ==");
        TransactionStatus inner = manager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTx() :: {}", inner.isNewTransaction());    // outer 에 여해서 false

        log.info("== inner tx commit ==");
        manager.commit(inner);

        log.info("== outer tx commit ==");
        manager.commit(outer);
    }

    @Test
    void outer_rollback() {
        log.info("== outer tx start ==");
        TransactionStatus outer = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== inner tx start ==");
        TransactionStatus inner = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== inner tx commit ==");
        manager.commit(inner);

        log.info("== outer tx rollback ==");
        manager.rollback(outer);
    }

    @Test
    void inner_rollback() {
        log.info("== outer tx start ==");
        TransactionStatus outer = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== inner tx start ==");
        TransactionStatus inner = manager.getTransaction(new DefaultTransactionAttribute());

        log.info("== inner tx rollback ==");
        manager.rollback(inner);

        log.info("== outer tx commit ==");
//        manager.commit(outer);
        assertThatThrownBy(() -> manager.commit(outer)).isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void inner_rollback_requires_new() {
        log.info("== outer tx start ==");
        TransactionStatus outer = manager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTx() :: {}", outer.isNewTransaction());

        log.info("== inner tx start ==");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionStatus inner = manager.getTransaction(definition);
        log.info("inner.isNewTx() :: {}", inner.isNewTransaction());

        log.info("== inner tx rollback ==");
        manager.rollback(inner);

        log.info("== outer tx commit ==");
        manager.commit(outer);
    }
}
