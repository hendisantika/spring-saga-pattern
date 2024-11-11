package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.ShipmentDAO;
import id.my.hendisantika.sagapattern.dto.Driver;
import id.my.hendisantika.sagapattern.dto.Shipment;
import id.my.hendisantika.sagapattern.dto.ShippingRequest;
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

    public static int createShipment(ShippingRequest shippingRequest) {
        String orderId = shippingRequest.getOrderId();

        Shipment shipment = new Shipment();
        shipment.setOrderId(orderId);
        shipment.setDeliveryAddress(shippingRequest.getDeliveryAddress());
        shipment.setDeliveryInstructions(shippingRequest.getDeliveryInstructions());

        int driverId = findDriver();
        shipment.setDriverId(driverId);

        if (!shipmentDAO.insertShipment(shipment)) {
            log.error("Shipment creation for order {} failed.", orderId);
            return 0;
        }

        Driver driver = new Driver();
        driver.setName("");
        shipmentDAO.readDriver(driverId, driver);

        if (driver.getName().isBlank()) {
            log.error("Shipment creation for order {} failed as driver in the area is not available.", orderId);
            shipmentDAO.cancelShipment(orderId);
            return 0;
        } else {
            log.info("Assigned driver {} to order with id: {}", driverId, orderId);
            shipmentDAO.confirmShipment(orderId);
        }

        return driverId;
    }
}
