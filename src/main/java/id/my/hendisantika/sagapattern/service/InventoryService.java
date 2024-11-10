package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.InventoryDAO;
import id.my.hendisantika.sagapattern.dto.FoodItem;
import id.my.hendisantika.sagapattern.dto.Restaurant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

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

    public static boolean checkAvailability(int restaurantId, ArrayList<FoodItem> items) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("");
        inventoryDAO.readRestaurant(restaurantId, restaurant);
        return !Objects.equals(restaurant.getName(), "");
    }
}
