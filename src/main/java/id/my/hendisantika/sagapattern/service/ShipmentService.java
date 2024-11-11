package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.ShipmentDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 07.03
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class ShipmentService {

    private static final ShipmentDAO shipmentDAO = new ShipmentDAO("jdbc:sqlite:food_delivery.db");

}
