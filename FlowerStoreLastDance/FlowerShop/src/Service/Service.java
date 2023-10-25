/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import MyFactory.*;
import data_object.IFlowerDAO;
import data_object.IOrderDAO;
/**
 *
 * @author LENOVO
 */
public class Service implements IService{
    static final IFactory factory = new Factory();
    static final IFlowerDAO flowerDao = factory.flowerDAO();
    static final IOrderDAO orderDao = factory.orderDAO();
    @Override
    public void createFlower() {
        try {
            flowerDao.createFlower();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void findFlower() {
        try {
            flowerDao.findFlower();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void updateFlower() {
        flowerDao.updateFlower();
    }

    @Override
    public void deleteFlower() {
        flowerDao.deleteFlower();
    }

    @Override
    public void createOrder() {
        try {
            orderDao.createOrder();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void displayOrderList() {
        try {
            orderDao.displayOrder();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void displayFlower() {
        try {
            flowerDao.displayFlowerList();
        } catch (Exception e) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void sortOrderList() {
        try {
            orderDao.sortOrder();
        } catch (Exception e) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public boolean saveFlowerFile(String filepath) {
        return flowerDao.saveFlowersFile(filepath);
    }

    @Override
    public boolean loadFlowerFile(String filepath) {
        return flowerDao.loadFlowersFile(filepath);
    }

    @Override
    public boolean saveOrderFile(String filepath) {
        return orderDao.saveOderFile(filepath);
    }

    @Override
    public boolean loadOrderFile(String filepath) {
        return orderDao.loadOrderFile(filepath);
    } 

    @Override
    public void finFlowerDate() {
        flowerDao.findFlowerDate();
    }
}
