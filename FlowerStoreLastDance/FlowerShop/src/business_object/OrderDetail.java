/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business_object;

/**
 *
 * @author LENOVO
 */
public class OrderDetail {
    private String orderDetailID;
    private String flowerID;
    private int quantity;
    private int flowerCost;

    public OrderDetail() {
    }

    public OrderDetail(String orderDetailID, String flowerID, int quantity, int flowerCost) {
        this.orderDetailID = orderDetailID;
        this.flowerID = flowerID;
        this.quantity = quantity;
        this.flowerCost = flowerCost;
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public String getFlowerID() {
        return flowerID;
    }

    public void setFlowerID(String flowerID) {
        this.flowerID = flowerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFlowerCost() {
        return flowerCost;
    }

    public void setFlowerCost(int flowerCost) {
        this.flowerCost = flowerCost;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%d|%d",
                this.getOrderDetailID(),
                this.getFlowerID(),
                this.getQuantity(),
                this.getFlowerCost());
    }
}
