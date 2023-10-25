/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business_object;

import java.util.HashMap;

/**
 *
 * @author LENOVO
 */
public class Order {
    OrderHeader orderHeader;
    HashMap<String, OrderDetail> detailMap;

    public Order(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
        detailMap = new HashMap<>();
    }

    public OrderHeader getOrderHeader() {
        return orderHeader;
    }

    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public HashMap<String, OrderDetail> getDetailMap() {
        return detailMap;
    }

    public void setDetailMap(HashMap<String, OrderDetail> detailMap) {
        this.detailMap = detailMap;
    }

    @Override
    public String toString() {
        return "Order{" + "orderHeader=" + orderHeader + ", detailMap=" + detailMap + '}';
    }
    
}
