package id.my.hendisantika.sagapattern.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.35
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Shipment {
    private int id;
    private String orderId;
    private int driverId;
    private String deliveryAddress;
    private String deliveryInstructions;
    private long createdAt;
    private String status;
    public enum Status {
        SCHEDULED,
        CONFIRMED,
        DELIVERED,
        CANCELED
    }
}
