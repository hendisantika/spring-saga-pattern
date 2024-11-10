package id.my.hendisantika.sagapattern.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.34
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Payment {
    private String paymentId;
    private String orderId;
    private double amount;
    private PaymentMethod paymentMethod;
    private Status status;
    private long createdAt;
    private String errorMsg;
    public enum Status {
        PENDING,
        FAILED,
        SUCCESSFUL,
        CANCELED
    }
}
