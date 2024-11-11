package id.my.hendisantika.sagapattern.worker;

import id.my.hendisantika.sagapattern.dto.CancelRequest;
import id.my.hendisantika.sagapattern.dto.CheckInventoryRequest;
import id.my.hendisantika.sagapattern.dto.DriverNotificationRequest;
import id.my.hendisantika.sagapattern.dto.FoodItem;
import id.my.hendisantika.sagapattern.dto.Order;
import id.my.hendisantika.sagapattern.dto.OrderRequest;
import id.my.hendisantika.sagapattern.dto.Payment;
import id.my.hendisantika.sagapattern.dto.PaymentRequest;
import id.my.hendisantika.sagapattern.dto.ShippingRequest;
import id.my.hendisantika.sagapattern.service.InventoryService;
import id.my.hendisantika.sagapattern.service.OrderService;
import id.my.hendisantika.sagapattern.service.PaymentService;
import id.my.hendisantika.sagapattern.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 07.06
 * To change this template use File | Settings | File Templates.
 */
@AllArgsConstructor
@Component
@ComponentScan(basePackages = {"io.orkes"})
public class ConductorWorkers {
    /**
     * Note: Using this setting, up to 5 tasks will run in parallel, with tasks being polled every 200ms
     */
    @WorkerTask(value = "order_food", threadCount = 3, pollingInterval = 300)
    public TaskResult orderFoodTask(OrderRequest orderRequest) {
        String orderId = OrderService.createOrder(orderRequest);

        TaskResult result = new TaskResult();
        Map<String, Object> output = new HashMap<>();

        if (orderId != null) {
            output.put("orderId", orderId);
            result.setOutputData(output);
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            output.put("orderId", null);
            result.setStatus(TaskResult.Status.FAILED);
        }
        return result;
    }

    @WorkerTask(value = "check_inventory", threadCount = 2, pollingInterval = 300)
    public TaskResult checkInventoryTask(CheckInventoryRequest checkInventoryRequest) {
        int restaurantId = checkInventoryRequest.getRestaurantId();
        ArrayList<FoodItem> items = checkInventoryRequest.getItems();
        boolean availability = InventoryService.checkAvailability(restaurantId, items);

        TaskResult result = new TaskResult();

        if (availability) {
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            result.setReasonForIncompletion("Restaurant is closed");
            result.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        }

        return result;
    }

    @WorkerTask(value = "make_payment", threadCount = 2, pollingInterval = 300)
    public TaskResult makePaymentTask(PaymentRequest paymentRequest) {
        TaskResult result = new TaskResult();

        Payment payment = PaymentService.createPayment(paymentRequest);
        Map<String, Object> output = new HashMap<>();
        output.put("orderId", payment.getOrderId());
        output.put("paymentId", payment.getPaymentId());
        output.put("paymentStatus", payment.getStatus().name());

        if (payment.getStatus() == Payment.Status.SUCCESSFUL) {
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            output.put("error", payment.getErrorMsg());
            result.setStatus(TaskResult.Status.FAILED_WITH_TERMINAL_ERROR);
        }

        result.setOutputData(output);

        return result;
    }

    @WorkerTask(value = "ship_food", threadCount = 2, pollingInterval = 300)
    public TaskResult shipFoodTask(ShippingRequest shippingRequest) {
        TaskResult result = new TaskResult();
        Map<String, Object> output = new HashMap<>();
        int driverId = ShipmentService.createShipment(shippingRequest);
        if (driverId != 0) {
            result.setStatus(TaskResult.Status.COMPLETED);
        } else {
            result.setStatus(TaskResult.Status.FAILED);
        }
        return result;
    }

    @WorkerTask(value = "notify_driver", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForDriverNotifications(DriverNotificationRequest driverNotificationRequest) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @WorkerTask(value = "notify_customer", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> checkForCustomerNotifications(Order order) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @WorkerTask(value = "cancel_payment", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> cancelPaymentTask(CancelRequest cancelRequest) {
        Map<String, Object> result = new HashMap<>();
        PaymentService.cancelPayment(cancelRequest.getOrderId());
        return result;
    }

    @WorkerTask(value = "cancel_delivery", threadCount = 2, pollingInterval = 300)
    public Map<String, Object> cancelDeliveryTask(CancelRequest cancelRequest) {
        Map<String, Object> result = new HashMap<>();
        ShipmentService.cancelDelivery(cancelRequest.getOrderId());
        return result;
    }
}
