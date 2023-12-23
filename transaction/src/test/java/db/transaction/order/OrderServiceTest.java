package db.transaction.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository repository;

    @Test
    void complete() throws NotEnoughMoneyException {

        Order order = new Order();
        order.setUsername("정상");

        orderService.order(order);

        Order findOrder = repository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("완료");
    }

    @Test
    void runtimeException() throws NotEnoughMoneyException {

        Order order = new Order();
        order.setUsername("예외");

        assertThatThrownBy(() -> orderService.order(order)).isInstanceOf(RuntimeException.class);

        Optional<Order> findOne = repository.findById(order.getId());
        assertThat(findOne.isEmpty()).isTrue();
    }

    @Test
    void bizException() {

        Order order = new Order();
        order.setUsername("잔고부족");

        try {
            orderService.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("잔고부족");
        }

        Order findOne = repository.findById(order.getId()).get();
        assertThat(findOne.getPayStatus()).isEqualTo("대기");
    }
}