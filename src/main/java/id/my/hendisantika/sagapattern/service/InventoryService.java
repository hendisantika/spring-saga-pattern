package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.InventoryDAO;
import lombok.extern.slf4j.Slf4j;

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
public class InventoryService {

    private static final InventoryDAO inventoryDAO = new InventoryDAO("jdbc:sqlite:food_delivery.db");
}
