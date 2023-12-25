package db.transaction.propagation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class LogRepository {

    private final EntityManager em;

    @Transactional
    public void save(Log logMessage) {
        log.info("== save log ==");
        em.persist(logMessage);

        if (logMessage.getMessage().contains("로그예외")) {
            log.info("== log exception ==");
            throw new RuntimeException("exception");
        }

    }

    public Optional<Log> find(String message) {
        return em.createQuery("select l.message from Log l where l.message = :message", Log.class)
                .setParameter("message", message)
                .getResultList().stream().findAny();
    }
}
