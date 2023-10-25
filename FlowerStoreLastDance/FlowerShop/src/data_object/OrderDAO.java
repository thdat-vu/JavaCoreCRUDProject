package data_object;

import MyUtils.Inputter;
import MyUtils.Utils;
import business_object.Order;
import business_object.OrderDetail;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderDAO implements IOrderDAO {

    public static final String orderFile = "orders.txt";
    public static Set<Order> orderSet = orderSet = new HashSet<>();

    public int calculateFlowerCount(){
        int flowerCount = 0;
        for (Order order:
                orderSet) {
           flowerCount += order.getFlowerCount();

        }
        return flowerCount;
    }

    public int calculateTotalCost(){
        int totalCost = 0;
        for (Order order:
                orderSet) {
            totalCost += order.getTotal();

        }
        return totalCost;
    }

    @Override
    public boolean saveOderFile(String filepath) {
        boolean check = false;
        File f = new File(orderFile);
        PrintWriter w = null;
        try {
            if (f.exists()) {
                String ans = Utils.getStringreg("Do you want to overwrite Order file?(Y/N): ", "[YyNn]", "Cannot be empty", "You must answer Y or N");
                if (ans.equalsIgnoreCase("n")) {
                    return false;
                }
                w = new PrintWriter(f);
                for (Order order : orderSet) {
                    w.println(order.toString());
                    for (OrderDetail detail : order.getDetailList().values()) {
                        w.println(detail.toString());
                    }
                }
            }
            check = true;
        } catch (Exception e) {
            System.out.println("Error when save to Order File!");
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                System.out.println("Error when close Order File!");
            }
        }
        return check;

    }

    @Override
    public boolean loadOrderFile(String filepath) {
        boolean check = false;
        File f = new File(filepath);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            while (br.ready()) {
                String s = br.readLine();
                String arr[] = s.split(",");
                String orderID = arr[0].trim();
                LocalDate orderDate = LocalDate.parse(arr[1].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String customerName = arr[2].trim();
                int detailCount = Integer.parseInt(arr[3].trim());
                Order order = new Order(orderID, orderDate, customerName, detailCount);
                HashMap<String, OrderDetail> detailHashMap = new HashMap<>();
                for (int i = 0; i < order.getDetailCount(); i++) {
                    s = br.readLine();
                    String arr2[] = s.split("\\|");
                    String orderDetail = arr2[0].trim();
                    String flowerID = arr2[1].trim();
                    int quantity = Integer.parseInt(arr2[2]);
                    int flowerCost = Integer.parseInt(arr2[3]);
                    OrderDetail orderDetail1 = new OrderDetail(orderDetail, flowerID, quantity, flowerCost);
                    detailHashMap.put(orderDetail, orderDetail1);
                }
                order.setDetailList(detailHashMap);
                orderSet.add(order);
            }
            check = true;
        } catch (NumberFormatException e) {
            System.out.println("Data cannot be parsed into Integer!");
        } catch (FileNotFoundException ex) {
            System.out.println("FILE ORDER NOT FOUND!");
        } catch (IOException ex) {
            System.out.println("IO Exception!");
        } catch (DateTimeException e) {
            System.out.println("Date cannot be parse!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Out of index when read Order file!");
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                System.out.println("Close Order File Error!");
            }
        }
        return check;
    }

    @Override
    public void createOrder() {
        //Khoi tao header
        String orderID, customerName;
        LocalDate orderDate;
        do{
            orderID = Utils.getStringreg("Please input Order header ID (xxx - for example: 001): ", "^[0-9][0-9][0-9]$", "Cannot be empty.", "Wrong input format.");
            if (this.checkDupplicateOrderID(orderID)) {
                System.out.println("The Order ID has already existed.");
            }
        }while(this.checkDupplicateOrderID(orderID));
        customerName = Utils.getString("Please enter customer's name: ", "Cannot be empty.");
        orderDate = Utils.getDate("Please enter the date: ", "Wrong input format. Please enter a valid date (dd/mm/yyyy)");
        Order o = new Order(orderID, orderDate, customerName);
        //Khoi tao detail
        String orderDetailID, flowerID;
        int quantity;
        int flowerCost;
        String choice = "";
        Map<String, OrderDetail> tmpDetailList = new HashMap<>();
        do{
            orderDetailID = o.inputOrderDetailID();
            FlowerDAO.showFlowerList();
            flowerID = Utils.getStringreg("Input new Flower's ID(Fxxx(x is a number) - for example: F0001): ", "^F\\d{4}$", "Cannot be empty", "Wrong format ID");
            quantity = Utils.getInt("Please enter the quantity: ", 0);
            flowerCost = quantity * FlowerDAO.getFlowerByID(flowerID).getUnitPrice();
            OrderDetail detail = new OrderDetail(orderDetailID, flowerID, quantity, flowerCost);
            
            tmpDetailList.put(orderDetailID, detail);
            
            choice = Utils.getStringreg("Do you want to add another Order Detail? (Y/N): ", "[YyNn]", "Cannot be empty.", "You just choose Y or N");
            
        }while(choice.equalsIgnoreCase("y"));
        o.setDetailList((HashMap<String, OrderDetail>) tmpDetailList);
        orderSet.add(o);
    }

    @Override
    public void displayOrder() {
        LocalDate startDate = Utils.getDate("Enter start date: ", "Wrong input format(dd/mm/yyyy)");
        LocalDate endDate = null;
        do {
            try {
                endDate = Utils.getDate("Please input end date", "Wrong date format, please try again");
                if (endDate.isBefore(startDate)) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("End date must be after start date!");
            }
        } while (endDate.isBefore(startDate));
        System.out.println("LIST OF ORDERS");
        System.out.println("Start date: " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("End date  : " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("+----------+---------------+------------------+------------------------------+----------------+--------------------+");
        System.out.println("|No.       |Order Id       | Order Date       |          Full name           |   FLower Count |      Total Cost    |");
        System.out.println("+----------+---------------+------------------+------------------------------+----------------+--------------------+");
        int i = 1;
        int count = 0, total = 0;
        for (Order order : orderSet) {
            if (((order.getOrderDate().isAfter(startDate) || order.getOrderDate().equals(startDate))) && ((order).getOrderDate().isBefore(endDate) || order.getOrderDate().equals(endDate))) {
                System.out.println("+----------+---------------+------------------+------------------------------+----------------+--------------------+");
                System.out.print("  " + (i++) + "\t\t");
                order.screenTime();
                count += order.getFlowerCount();
                total += order.getTotal();
            }
        }
        System.out.println("+----------+---------------+------------------+------------------------------+----------------+--------------------+");
        //tinh tong bong hoa va tong gia tien.
        System.out.println("|          |Total          |                  |                              |   FLower Count |      Total Cost    |");

        System.out.println("|          |               |                  |                              |   "+count+"\t      |      $"+total+"     |");
        System.out.println("+----------+---------------+------------------+------------------------------+----------------+--------------------+");
    }

    @Override
    public void sortOrder() {
        List<Order> sortOrderList = new ArrayList<Order>();

        for (Order order
                : orderSet) {
            sortOrderList.add(order);
        }
        if (sortOrderList.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            int choice = 0;

            do {
                System.out.println("1. Sort by Order's ID: ");
                System.out.println("2. Sort by Order's date: ");
                System.out.println("3. Sort by Customer's name: ");
                System.out.println("4. Sort by Total cost: ");
                System.out.print("What kind of sort do you want to choose? \n");
                choice = Utils.getChoice("Input [1] - [4] : ", 1, 4);

                switch (choice) {
                    case 1:
                        do {
                            System.out.println("1. ID: Ascending. ");
                            System.out.println("2. ID: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByID(sortOrderList);
                                    break;
                                case 2:
                                    sortDSCByID(sortOrderList);
                                    break;

                            }
                        } while (choice != 1 && choice != 2);
                        break;
                    case 2:
                        do {
                            System.out.println("1. Name: Ascending. ");
                            System.out.println("2. Name: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByDate(sortOrderList);
                                    break;
                                case 2:
                                    sortDSCByDate(sortOrderList);
                                    break;

                            }
                        } while (choice != 1 && choice != 2);
                        break;
                    case 3:
                        do {
                            System.out.println("1. Name: Ascending. ");
                            System.out.println("2. Name: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByCusName(sortOrderList);
                                    break;
                                case 2:
                                    sortDSCByCusName(sortOrderList);
                                    break;

                            }
                        } while (choice != 1 && choice != 2);
                        break;
                    case 4:
                        do {
                            System.out.println("1. Name: Ascending. ");
                            System.out.println("2. Name: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByTotalCost(sortOrderList);
                                    break;
                                case 2:
                                    sortDSCByTotalCost(sortOrderList);
                                    break;

                            }
                        } while (choice != 1 && choice != 2);
                        break;
                    default:
                        throw new AssertionError("Sorting error.");
                }
            } while (choice == 1 && choice == 2);
        }

    }

    private void sortASCByDate(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> t.getOrderDate().compareTo(t1.getOrderDate()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortDSCByDate(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> -1 * t.getOrderDate().compareTo(t1.getOrderDate()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortASCByCusName(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> t.getCustomName().compareTo(t1.getCustomName()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortDSCByCusName(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> -1 * t.getCustomName().compareTo(t1.getCustomName()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortASCByTotalCost(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order o1, Order o2) -> {
            int t = o1.getTotal() - o2.getTotal();
            if (t > 0) {
                return 1;
            }
            if (t < 0) {
                return -1;
            }
            return 0;
        });
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortDSCByTotalCost(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order o1, Order o2) -> {
            int t = o1.getTotal() - o2.getTotal();
            if (t > 0) {
                return -1;
            }
            if (t < 0) {
                return 1;
            }
            return 0;
        });
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortASCByID(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> t.getOrderID().compareTo(t1.getOrderID()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    private void sortDSCByID(List<Order> sortOrderList) {
        Collections.sort(sortOrderList, (Order t, Order t1) -> -1 * t.getOrderID().compareTo(t1.getOrderID()));
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Order Id  | Order Date    |          Full name           |   Quantity |      Total Cost    |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Order order : sortOrderList) {
            System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
            order.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    public boolean checkDupplicateOrderID(String data) {
        for (Order order : orderSet) {
            if (order.getOrderID().equalsIgnoreCase(data)) {
                return true;
            }
        }
        return false;
    }

}
