/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import MyUtils.Inputter;
import MyUtils.Utils;
import java.util.Scanner;
import Service.Service;

import static data_object.FlowerDAO.flowerFile;
import static data_object.OrderDAO.orderFile;

/**
 *
 * @author LENOVO
 */
public class MyFloraStore {
    public static void main(String[] args) {
        Service service = new Service();
        Scanner sc = new Scanner(System.in);
       if (!service.loadFlowerFile(flowerFile)||!service.loadOrderFile(orderFile))  {System.out.println("load fail!");}
//        PatientDAO patientDAO = new PatientDAO();
//        NurseDAO nurseDAO = new NurseDAO();

        int option = 0;
        String ans;
        System.out.println("\t=======FLOWER STORE MANAGEMENT=======");
        HOME:
        do {
            System.out.println("1. FLower's management");
            System.out.println("[1]     1.1 Create a flower");
            System.out.println("[2]     1.2 Find the flower");
            System.out.println("[3]     1.3 Update the flower");
            System.out.println("[4]     1.4 Delete the flower");
            System.out.println("[5]     1.5 Display flowers");
            System.out.println("2. Order's management");
            System.out.println("[6]     2.1 Add a order");
            System.out.println("[7]     2.2 Display order");
            System.out.println("[8]     2.3 Sort the orders list");
            System.out.println("[9]     2.4 Save data");
            System.out.println("[10]    2.5 Load data");
            System.out.println("[11] Find flower by date.");
            System.out.println("Other Numbers - Quit");
            System.out.println("Please choose an option from [1] to [10]: ");
            boolean cont = false;
            do {                
               try {
                 option = Integer.parseInt(sc.nextLine().trim());
                 cont = false;
            } catch (Exception e) {
                System.out.println("Number format error.");
                cont = true;
            } 
            } while (cont);
            
           
            switch (option) {
                case 1: {
                    String choice = null;
                    do {
                            service.createFlower();
                        choice = Inputter.inputYesNo("Do you want to continue adding a new flower?(Y/N)");
                    } while (choice.equalsIgnoreCase("y"));
                }
                break;

                case 2: {
                        service.findFlower();
                }
                break;
                case 3: {
                        service.updateFlower();
                }
                break;
                case 4: {
                        service.deleteFlower();
                }
                case 5: {
                    service.displayFlower();
                }
                break;
                case 6: {
                    String choice = null;
                    do{
                        service.createOrder();
                        choice = Inputter.inputYesNo("Do you want to continue adding a new Order?(Y/N)");
                    }while(choice.equalsIgnoreCase("y"));

                }
                break;
                case 7: {
                        service.displayOrderList();
                }
                break;
                case 8: {
                        service.sortOrderList();
                }
                break;
                case 9: {
                        if(service.saveFlowerFile("flowers.txt")) System.out.println("Save flowers successfully");
                        if(service.saveOrderFile("orders.txt")) System.out.println("Save orders successfully");
                }
                break;

                case 10: {
                    try {
                        if(service.loadFlowerFile(flowerFile)) System.out.println("Load flowers successfully");
                        if(service.loadOrderFile(orderFile)) System.out.println("Load orders successfully");
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                }
                case 11:
                    service.finFlowerDate();
                break;
                default:
                    service.saveFlowerFile(flowerFile);
                    service.saveOrderFile(orderFile);
                    ans = Utils.getStringreg("Do you want to quit?(y/n): ", "[YyNn]", "You should input something here: ","You should input y or n");
                    if (ans.equalsIgnoreCase("y")) {
                        System.out.println("Thank you for using service.");
                    }else if (ans.equalsIgnoreCase("n")){
                        option = 5;
                        continue HOME;
                    }
            }
        } while (option > 0 && option < 12);
    }
}
