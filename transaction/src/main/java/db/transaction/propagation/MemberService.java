package db.transaction.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository st==");
        memberRepository.save(member);
        log.info("== memberRepository ed==");

        log.info("== logRepository st==");
        logRepository.save(logMessage);
        log.info("== logRepository ed==");
    }

    @Transactional
    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository st==");
        memberRepository.save(member);
        log.info("== memberRepository ed==");

        log.info("== logRepository st==");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("== log save fail, logMessage :: {} ==", logMessage.getMessage());
            log.info("정상흐름반환");
        }
        log.info("== logRepository ed==");
    }

}
