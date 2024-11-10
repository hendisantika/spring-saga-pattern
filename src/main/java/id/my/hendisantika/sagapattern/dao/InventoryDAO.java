package id.my.hendisantika.sagapattern.dao;

import id.my.hendisantika.sagapattern.dto.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class InventoryDAO extends BaseDAO {

    public InventoryDAO(String url) {
        super(url);
    }

    public void readRestaurant(int restaurantId, Restaurant restaurant) {
        String sql = "SELECT name, address, contact FROM restaurants WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setContact(rs.getString("contact"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
