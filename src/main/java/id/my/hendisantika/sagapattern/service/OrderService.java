package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.OrdersDAO;
import id.my.hendisantika.sagapattern.dto.Customer;
import id.my.hendisantika.sagapattern.dto.Order;
import id.my.hendisantika.sagapattern.dto.OrderDetails;
import id.my.hendisantika.sagapattern.dto.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public static String createOrder(OrderRequest orderRequest) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Order order = new Order();
        order.setOrderId(uuidAsString);

        Customer customer = new Customer();
        customer.setEmail(orderRequest.getCustomerEmail());
        customer.setName(orderRequest.getCustomerName());
        customer.setContact(orderRequest.getCustomerContact());
        customer.setId(ORDERS_DAO.insertCustomer(customer));

        log.info("Upsert customer record in DB with id: {}", customer.getId());

        order.setCustomer(customer);
        order.setRestaurantId(orderRequest.getRestaurantId());
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setStatus(Order.Status.PENDING);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(uuidAsString);
        orderDetails.setItems(orderRequest.getItems());
        orderDetails.setNotes(orderRequest.getNotes());

        order.setOrderDetails(orderDetails);

        String error = ORDERS_DAO.insertOrder(order);

        if (error.isEmpty()) {
            log.info("Created order with id: {}", order.getOrderId());
        } else {
            log.error("Order creation failure: {}", error);
            return null;
        }

        return uuidAsString;
    }

    public static Order getOrder(String orderId) {
        Order order = new Order();
        ORDERS_DAO.readOrder(orderId, order);
        return order;
    }
}
