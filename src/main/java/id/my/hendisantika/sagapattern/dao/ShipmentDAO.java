package id.my.hendisantika.sagapattern.dao;

import id.my.hendisantika.sagapattern.dto.Shipment;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.43
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class ShipmentDAO extends BaseDAO {

    public ShipmentDAO(String url) {
        super(url);
    }

    public boolean insertShipment(Shipment shipment) {
        Date date = new Date();
        Timestamp nowAsTS = new Timestamp(date.getTime());

        String sql = "INSERT INTO shipments(orderid,driverid,address,instructions,createdat,status) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, shipment.getOrderId());
            pstmt.setInt(2, shipment.getDriverId());
            pstmt.setString(3, shipment.getDeliveryAddress());
            pstmt.setString(4, shipment.getDeliveryInstructions());
            pstmt.setTimestamp(5, nowAsTS);
            pstmt.setString(6, Shipment.Status.SCHEDULED.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public void cancelShipment(String orderId) {
        String sql = "UPDATE shipments SET status=? WHERE orderid=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Shipment.Status.CANCELED.name());
            pstmt.setString(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void confirmShipment(String orderId) {
        String sql = "UPDATE shipments SET status=? WHERE orderid=?;";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Shipment.Status.CONFIRMED.name());
            pstmt.setString(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
