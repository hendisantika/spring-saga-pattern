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
public class PaymentRequest {
    private String orderId;
    private int customerId;
    private float amount;
    private PaymentMethod method;
}