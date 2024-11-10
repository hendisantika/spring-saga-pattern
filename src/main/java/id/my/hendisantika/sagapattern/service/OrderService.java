package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.OrdersDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.54
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@AllArgsConstructor
@Service
public class OrderService {

    private static final OrdersDAO ORDERS_DAO = new OrdersDAO("jdbc:sqlite:food_delivery.db");
}
