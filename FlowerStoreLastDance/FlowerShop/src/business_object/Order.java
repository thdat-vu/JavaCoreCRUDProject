/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business_object;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import MyUtils.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author LENOVO
 */
public class Order {

    private String orderID;
    private LocalDate orderDate;
    private String customName;
    private HashMap<String, OrderDetail> detailList;
    private int detailCount;
    private int total;
    private int flowerCount;
    public Order() {
    }

    public Order(String orderID, LocalDate orderDate, String customName, HashMap<String, OrderDetail> detailList) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customName = customName;
        this.detailList = detailList;
        for (OrderDetail detail : detailList.values()) {
            detailCount += detail.getQuantity();
            total += detail.getFlowerCost();
        }
    }

    public Order(String orderID, LocalDate orderDate, String customName, int detailCount) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customName = customName;
        this.detailCount = detailCount;
        this.detailList = new HashMap<>();
        for (OrderDetail detail : detailList.values()) {
            detailCount += detail.getQuantity();
            total += detail.getFlowerCost();
        }
    }

    public Order(String orderID, LocalDate orderDate, String customName) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.customName = customName;
        this.detailList = new HashMap<>();
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public HashMap<String, OrderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(HashMap<String, OrderDetail> detailList) {
        this.detailList = detailList;
        for (OrderDetail detail : detailList.values()) {
            flowerCount+=detail.getQuantity();
            total+=detail.getFlowerCost();
        }
    }

    public int getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(int detailCount) {
        this.detailCount = detailCount;
    }

    public int getFlowerCount() {

        return flowerCount;
    }

    public void setFlowerCount(int flowerCount) {
        this.flowerCount = flowerCount;
    }

    public int getTotal() {

        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderDetail getOrderDetail(String orderDetailID) {
        if (this.getDetailList() != null) {
            return this.getDetailList().get(orderDetailID);
        }
        return null;
    }

    public String inputOrderDetailID() {
        String orderDetailID;
        while (true) {
            orderDetailID = Utils.getStringreg("Enter Order Detail ID (ODxxx): ", "^OD\\d{3}$", "Cannot be null", "Wrong input format.");
            if (this.getOrderDetail(orderDetailID) == null) {
                break;
            } else {
                System.out.println("Sorry, the Detail ID is duplicated!");
            }
        }
        return orderDetailID;
    }

    @Override
    public String toString() {
        return getOrderID()+","+getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+","+getCustomName()+","+getDetailList().size();
    }

    public void screenTime() {
        System.out.println(this.getOrderID()+"\t\t"+this.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"\t\t"+this.getCustomName()+"\t\t"+this.getFlowerCount()+"\t\t"+ "$"+ this.getTotal());
    }


}
