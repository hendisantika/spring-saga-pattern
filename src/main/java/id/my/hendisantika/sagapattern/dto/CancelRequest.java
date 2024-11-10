package id.my.hendisantika.sagapattern.dto;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.29
 * To change this template use File | Settings | File Templates.
 */
@Data
public class CancelRequest {
    private String orderId;
}
