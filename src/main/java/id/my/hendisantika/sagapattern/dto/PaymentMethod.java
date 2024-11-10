package id.my.hendisantika.sagapattern.dto;

import lombok.Data;
import lombok.ToString;

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
@ToString
public class PaymentMethod {
    private String type;
    private PaymentDetails details;
}
