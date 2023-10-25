package business_object;

public class OrderDetail {
    private String OrderDetailID;
    private String flowerID;
    private int quantity;
    private long cost;

    public OrderDetail(String orderDetailID, String flowerID, int quantity, long cost) {
        OrderDetailID = orderDetailID;
        this.flowerID = flowerID;
        this.quantity = quantity;
        this.cost = 0;
    }

    public String getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        OrderDetailID = orderDetailID;
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

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}
