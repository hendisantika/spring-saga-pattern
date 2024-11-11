package id.my.hendisantika.sagapattern;

import id.my.hendisantika.sagapattern.dao.BaseDAO;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@AllArgsConstructor
@ComponentScan(basePackages = {"io.orkes"})
public class SpringSagaPatternApplication {
    private static final BaseDAO db = new BaseDAO("jdbc:sqlite:food_delivery.db");

    public static void initDB() {
        db.createTables("orders");
        db.createTables("inventory");
        db.createTables("payments");
        db.createTables("shipments");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSagaPatternApplication.class, args);
        initDB();
    }

}
