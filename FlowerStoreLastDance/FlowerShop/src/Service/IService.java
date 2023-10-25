/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

/**
 *
 * @author LENOVO
 */
public interface IService {
    public void createFlower();
    public void findFlower();
    public void updateFlower();
    public void deleteFlower();
    public void createOrder();
    public void displayOrderList();
    public void displayFlower();
    public void sortOrderList();
    public boolean saveFlowerFile(String filepath);
    public boolean loadFlowerFile(String filepath);
    public boolean saveOrderFile(String filepath);
    public boolean loadOrderFile(String filepath);
    public void finFlowerDate();
}
