package business_object;

import java.time.LocalDate;

public class OrderHeader {
    private String order_id;
    private LocalDate orderDate;
    private String customerName;

    public OrderHeader(String order_id, LocalDate orderDate, String customerName) {
        this.order_id = order_id;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
