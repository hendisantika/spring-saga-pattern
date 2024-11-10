package id.my.hendisantika.sagapattern.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.40
 * To change this template use File | Settings | File Templates.
 */
public class OrdersDAO extends BaseDAO {

    public OrdersDAO(String url) {
        super(url);
    }

    public String insertOrder(Order order) {
        Date date = new Date();
        Timestamp nowAsTS = new Timestamp(date.getTime());

        String itemsStr = String.join("", order.getOrderDetails().getItems().toString());

        String notesStr = null;

        if (!order.getOrderDetails().getNotes().isEmpty()) {
            notesStr = String.join("", order.getOrderDetails().getNotes().toString());
        } else {
            notesStr = "";
        }

        String sql = "INSERT INTO orders(orderid,customerid,restaurantid,deliveryaddress,createdat,status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getOrderId());
            pstmt.setInt(2, order.getCustomer().getId());
            pstmt.setInt(3, order.getRestaurantId());
            pstmt.setString(4, order.getDeliveryAddress());
            pstmt.setTimestamp(5, nowAsTS);
            pstmt.setString(6, order.getStatus().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        sql = "INSERT INTO orders_details(orderid,items,notes) VALUES(?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getOrderId());
            pstmt.setString(2, itemsStr);
            pstmt.setString(3, notesStr);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return "";
    }
}
