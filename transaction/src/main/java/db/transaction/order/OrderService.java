package db.transaction.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("== call order ==");
        repository.save(order);

        log.info("== 결제 프로세스 ==");
        if (order.getUsername().equals("예외")) {
            log.info("system exception");
            throw new RuntimeException("system exception");
        } else if (order.getUsername().equals("잔고부족")) {
            log.info("잔고부족 exception");
            order.setPayStatus("대기");
            throw new NotEnoughMoneyException("잔고부족");
        } else {
            log.info("정상 승인");
            order.setPayStatus("완료");
        }

        log.info("== 결제 프로세스 종료 ==");
    }


}
