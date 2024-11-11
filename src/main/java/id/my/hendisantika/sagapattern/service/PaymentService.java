package id.my.hendisantika.sagapattern.service;

import id.my.hendisantika.sagapattern.dao.PaymentsDAO;
import id.my.hendisantika.sagapattern.dto.Payment;
import id.my.hendisantika.sagapattern.dto.PaymentRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 07.02
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class PaymentService {

    private static final PaymentsDAO paymentsDAO = new PaymentsDAO("jdbc:sqlite:food_delivery.db");

    public static Payment createPayment(PaymentRequest paymentRequest) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Payment payment = new Payment();
        payment.setPaymentId(uuidAsString);
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getMethod());
        payment.setStatus(Payment.Status.PENDING);

        // Check if returned error is non-empty, i.e failure
        if (!paymentsDAO.insertPayment(payment).isEmpty()) {
            log.error("Failed to process payment for order {}", paymentRequest.getOrderId());
            payment.setErrorMsg("Payment creation failure");
            payment.setStatus(Payment.Status.FAILED);
        } else {
            if (makePayment(payment)) {
                payment.setStatus(Payment.Status.SUCCESSFUL);
            } else {
                payment.setStatus(Payment.Status.FAILED);
            }
        }

        // Record final status
        paymentsDAO.updatePaymentStatus(payment);
        return payment;
    }

}
