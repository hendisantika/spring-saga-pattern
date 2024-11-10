package id.my.hendisantika.sagapattern.dto;

import lombok.Data;

import java.util.ArrayList;

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
public class OrderRequest {
    private String OrderRequestId;
    private String customerEmail;
    private String customerName;
    private String customerContact;
    private int restaurantId;
    private ArrayList<FoodItem> items;
    private ArrayList<String> notes;
    private String deliveryAddress;
    private String deliveryInstructions;
}
