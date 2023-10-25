package data_object;

import MyUtils.Utils;
import business_object.Order;
import business_object.OrderDetail;
import business_object.OrderHeader;
import java.time.LocalDate;

public class OrderDAO implements IOrderDAO{
    
    
    @Override
    public boolean saveOderFile(String filepath) {
        return false;
    }

    @Override
    public boolean loadOrderFile(String filepath) {
        return false;
    }

    @Override
    public void createOrder() {
        //Khoi tao header
        String orderID, customerName;
        LocalDate orderDate;
        orderID = Utils.getStringreg("Please input Order header ID: ", "d{4}", "Cannot be empty.", "Wrong input format.");
        customerName = Utils.getString("Please enter customer's name: ", "Cannot be empty.");
        orderDate = Utils.getDate("Please enter the date: ", "Wrong input format. Please enter a valid date (dd/mm/yyyy)");
        OrderHeader orderHead = new OrderHeader(orderID, orderDate, customerName);
        //Khoi tao detail
        String orderDetailID,flowerID;
        int quantity;
        long cost;
        
        orderDetailID = Utils.getStringreg("Please input Order detail's ID:", "^D\\d{4}$", "Cannot be empty", "Wrong input format. ");
        flowerID = Utils.getStringreg("Input new Flower's ID(Fxxx(x is a number) - for example: F0001): ", "^F\\d{4}", "Cannot be empty", "Wrong format ID");
        quantity = Utils.getInt("Please enter the quantity", 0);
        cost = (long) quantity * FlowerDAO.getFlower(flowerID).getUnitPrice();
        
        OrderDetail detail = new OrderDetail(orderDetailID, flowerID, quantity, cost);
        
        Order order = new Order(orderHead);
    }

    @Override
    public void displayOrder() {

    }

    @Override
    public void sortOrder() {

    }
}
