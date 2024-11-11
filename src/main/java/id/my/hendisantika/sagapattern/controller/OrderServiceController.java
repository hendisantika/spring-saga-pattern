package id.my.hendisantika.sagapattern.controller;

import id.my.hendisantika.sagapattern.dto.FoodDeliveryRequest;
import id.my.hendisantika.sagapattern.service.WorkflowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 09.15
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@AllArgsConstructor
@RestController
public class OrderServiceController {

    private final WorkflowService workflowService;

    @PostMapping(value = "/triggerFoodDeliveryFlow", produces = "application/json")
    public ResponseEntity<Map<String, Object>> triggerFoodDeliveryFlow(@RequestBody FoodDeliveryRequest foodDeliveryRequest) {
        return ResponseEntity.ok(workflowService.startFoodDeliveryWorkflow(foodDeliveryRequest));
    }
}
