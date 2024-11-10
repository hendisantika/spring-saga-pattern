package id.my.hendisantika.sagapattern.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * Project : saga-pattern
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 11/11/24
 * Time: 06.36
 * To change this template use File | Settings | File Templates.
 */
public class BaseDAO {
    private final String url;

    public BaseDAO(String url) {
        this.url = url;
    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected Boolean execute(String sql) {
        try (Connection conn = DriverManager.getConnection(this.url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void createTables(String service) {
        switch (service) {
            case "orders":
                createOrdersTable();
                createOrderDetailsTable();
                createCustomerTable();
                break;
            case "inventory":
                createRestaurantsTable();
                break;
            case "shipments":
                createDriversTable();
                createShipmentTable();
                break;
            case "payments":
                createPaymentsTable();
                break;
            default:
                System.out.println("Service name not recognized");
        }
    }

    private void createOrdersTable() {
        if (!tableExists("orders")) {

            String sql = "CREATE TABLE orders (\n"
                    + "	orderId text PRIMARY KEY,\n"
                    + "	customerId integer NOT NULL,\n"
                    + "	restaurantId integer NOT NULL,\n"
                    + "	deliveryAddress text NOT NULL,\n"
                    + "	createdAt TIMESTAMP NOT NULL,\n"
                    + "	status text NOT NULL\n"
                    + ");";

            execute(sql);
        }
    }

    private void createOrderDetailsTable() {
        if (!tableExists("orders_details")) {
            String sql = "CREATE TABLE orders_details (\n"
                    + "	orderId text PRIMARY KEY,\n"
                    + "	items text NOT NULL,\n"
                    + "	notes text\n"
                    + ");";

            execute(sql);
        }
    }

    private void createCustomerTable() {
        if (tableExists("customers")) {
            return;
        }

        String sql = "CREATE TABLE customers (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	email text NOT NULL,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if (execute(sql)) {
            seedCustomers();
        }
    }

    private void createRestaurantsTable() {
        if (!tableExists("restaurants")) {
            String sql = "CREATE TABLE restaurants (\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	name text NOT NULL,\n"
                    + "	address text NOT NULL,\n"
                    + "	contact text NOT NULL\n"
                    + ");";

            if (execute(sql)) {
                seedRestaurants();
            }
        }
    }

    private void createPaymentsTable() {
        if (tableExists("payments")) {
            return;
        }

        String sql = "CREATE TABLE payments (\n"
                + "	paymentId text PRIMARY KEY,\n"
                + "	orderId text NOT NULL,\n"
                + "	amount number NOT NULL,\n"
                + "	method text,\n"
                + "	status text,\n"
                + "	createdAt TIMESTAMP NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void createDriversTable() {
        if (tableExists("drivers")) {
            return;
        }

        String sql = "CREATE TABLE drivers (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	contact text\n"
                + ");";

        if (execute(sql)) {
            seedDrivers();
        }
    }

    private void createShipmentTable() {
        if (tableExists("shipments")) {
            return;
        }

        String sql = "CREATE TABLE shipments (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	orderId text NOT NULL,\n"
                + "	driverId number NOT NULL,\n"
                + "	address text NOT NULL,\n"
                + "	instructions text,\n"
                + "	status text NOT NULL,\n"
                + "	createdAt TIMESTAMP NOT NULL\n"
                + ");";

        execute(sql);
    }

    private void seedCustomers() {
        String[] queries = {
                "INSERT INTO customers(email, name, contact) VALUES('John Smith','john.smith@example.com','+12126781345');",
                "INSERT INTO customers(email, name, contact) VALUES('Mike Ross','mike.ross@example.com','+15466711147');",
                "INSERT INTO customers(email, name, contact) VALUES('Martha Williams','martha.williams@example.com','+12790581941');"
        };

        for (String query : queries) {
            execute(query);
        }
    }
}
