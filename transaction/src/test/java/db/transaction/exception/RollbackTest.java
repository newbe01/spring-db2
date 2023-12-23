package db.transaction.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RollbackTest {

    @Autowired
    RollbackService rollbackService;

    @Test
    void runtimeException() {
        assertThatThrownBy(() -> rollbackService.runtimeException()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException() {
        assertThatThrownBy(() -> rollbackService.checkedException()).isInstanceOf(TmpException.class);
    }

    @Test
    void rollbackFor() {
        assertThatThrownBy(() -> rollbackService.checkedException()).isInstanceOf(TmpException.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {
        @Bean
        RollbackService rollbackService() {
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {

        // RuntimeException : rollback
        @Transactional
        public void runtimeException() {
            log.info("== call runtimeException ==");
            throw new RuntimeException();
        }

        // CheckedException : commit
        @Transactional
        public void checkedException() throws TmpException {
            log.info("== call checkedException ==");
            throw new TmpException();
        }

        // CheckedException : rollback
        @Transactional(rollbackFor = TmpException.class)
        public void  rollbackFor() throws TmpException {
            log.info("== call checkedException ==");
            throw new TmpException();
        }

    }

    static class TmpException extends Exception {
    }

}
