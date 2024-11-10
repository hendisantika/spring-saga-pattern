package id.my.hendisantika.sagapattern.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.33
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Order {

    private String orderId;
    private Customer customer;
    private int restaurantId;
    private String deliveryAddress;
    private long createdAt;
    private Status status;
    private OrderDetails orderDetails;
    public enum Status {
        PENDING,
        ASSIGNED,
        CONFIRMED,
        CANCELLED
    }
}
