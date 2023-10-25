package data_object;

import MyUtils.Inputter;
import MyUtils.Utils;
import business_object.Flower;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlowerDAO implements IFlowerDAO {

    public static Set<Flower> flowerSet;
    public static final String flowerFile = "flowers.txt";

    public FlowerDAO() {
        flowerSet = new TreeSet<>();
    }

    @Override
    public boolean saveFlowersFile(String flowerFile) {
        File f = new File(flowerFile);
        PrintWriter w = null;
        try {
            if (f.exists()) {
                String ans = Utils.getStringreg("Do you want to override flower file? (Y/N): ", "[YyNn]", "You should answer: Y/N", "Wrong input format.");
                if (ans.equalsIgnoreCase("n")) {
                    return false;
                }
                //ghi file
                w = new PrintWriter(flowerFile);
                for (Flower flower : flowerSet) {
                    w.println(flower.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Null pointer exception.");
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
                System.out.println("Closing flower file error.");
            }
        }
        return true;
    }

    @Override
    public boolean loadFlowersFile(String filepath) {
        File f = new File(filepath);
        FileReader fr = null;
        BufferedReader r = null;
        if (!f.exists()) {
            System.out.println("FILE NOT FOUND");
            return false;
        } else {
            try {
                fr = new FileReader(f);
                r = new BufferedReader(fr);
                while (r.ready()) {
                    String s = r.readLine();
                    String[] arr = s.split(",");
                    Flower flower = new Flower(arr[0].trim(), arr[1].trim(), LocalDate.parse(arr[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), Integer.parseInt(arr[3]), arr[4].trim());
                    flowerSet.add(flower);
                }
            } catch (IOException e) {
                System.out.println("IO Exception occurred!");
            } catch (NumberFormatException e) {
                System.out.println("Data can not parse to Integer!");
            } finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }
                    if (r != null) {
                        r.close();
                    }
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
            return true;
        }

    }

    @Override
    public void createFlower() {
        String flowerid;
        String descript;
        LocalDate impDate;
        String catergory;
        int unitprice;
        boolean cont = false;
        do {
            flowerid = Utils.getStringreg("Input new Flower's ID(Fxxxx(x is a number) - for example: F0001): ", "^F\\d{4}", "Cannot be empty", "Wrong format ID");
            if (checkDupplicateID(flowerid) == true) {
                System.out.println("Sorry, the ID has been existed.");
                cont = true;
            } else {
                cont = false;
            }
        } while (cont == true);
        do {
            descript = Utils.getStringreg("Input the description: ", ".{3,50}", "Cannot be empty", "Wrong format! Must contain 3 to 50 characters");
            descript = descript.trim();
            if (descript.equalsIgnoreCase("")) {
                System.out.println("Sorry, you cannot type space values and enter.");
            }
        } while (descript.equalsIgnoreCase(""));

        impDate = Utils.getDate("Input the import date", "It must follow the pattern: (dd/MM/yyyy)");
        unitprice = Utils.getInt("Enter the unit price($): ", 0);
        catergory = Utils.getString("Input the category: ", "Cannot be empty");
        Flower newFlower = new Flower(flowerid, descript, impDate, unitprice, catergory);

        flowerSet.add(newFlower);
        System.out.println("Adding the new Flower successfully.");
    }

    @Override
    public void findFlower() {

        String data = Utils.getString("Input id or part the flower's name: ", "Cannot be empty");
        data = data.trim();
        if (flowerSet.isEmpty()) {
            System.out.println("The Flowers list is empty.");
        } else {
            List<Flower> tmpFlowerList = new ArrayList<>();
            for (Flower flower : flowerSet) {
                if (flower.getId().contains(data.toUpperCase()) || flower.getDescription().contains(data)) {
                    tmpFlowerList.add(flower);
                }
            }
            if (tmpFlowerList.size() != 0) {
                System.out.println("Flower List results: ");
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
                System.out.println("| Flower ID |       Description           |   Import Date    |     Unit Price     |       Category     |");
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
                for (Flower searchFlower : tmpFlowerList) {
                    searchFlower.screenTime();
                }
                System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
            } else {
                System.out.println("The flower does not exist.");
            }

        }
    }

    @Override
    public void updateFlower() {
        displayFlowerList();
        String nameSearch = Utils.getString("Enter the name(Description): ", "Cannot be empty");
        nameSearch = nameSearch.trim();
        if (this.getFlowerByName(nameSearch) != null) {

            System.out.println("[1]  - ID");
            System.out.println("[2]  - Description");
            System.out.println("[3]  - Import Date");
            System.out.println("[4]  - Unit Price");
            System.out.println("[5]  - Category");
            System.out.println("[0] or [6] - Update and turn back to main menu.");
            int choice;
            String id;
            String newDiscription = "";
            LocalDate newDate;
            int newUnitPrice;
            String newCategory;
            do {
                choice = Utils.getChoice("Enter your choice: ", 0, 6);
                switch (choice) {
                    case 1:
                        id = Utils.getStringreg("Enter new flower id: (Example: F1234): ", "^F\\d{4}", "Your ID must be filled in", "Wrong input format");
                        if (!this.checkDupplicateID(id)) {
                            this.getFlowerByName(nameSearch).setId(id);
                        } else {
                            System.out.println("The flower ID has already existed.");
                        }

                        break;
                    case 2:
                        do {
                            newDiscription = Utils.getStringreg("Input the description: ", ".{3,50}", "Cannot be empty", "Wrong format! Must contain 3 to 50 characters");
                            newDiscription = newDiscription.trim();
                            if (newDiscription.equalsIgnoreCase("")) {
                                System.out.println("Sorry, you cannot type space values and enter.");
                            }
                        } while (newDiscription.equalsIgnoreCase(""));
                        this.getFlowerByName(nameSearch).setDescription(newDiscription);
                        nameSearch = newDiscription;
                        break;
                    case 3:
                        newDate = Utils.getDate("Enter new import date", "Wrong format! Try to input valid date");
                        this.getFlowerByName(nameSearch).setImportDate(newDate);
                        break;
                    case 4:
                        newUnitPrice = Utils.getInt("Enter new unit price: ", 0);
                        this.getFlowerByName(nameSearch).setUnitPrice(newUnitPrice);
                        break;
                    case 5:
                        do{
                            newCategory = Utils.getString("Enter new Category: ", "Cannot be empty.");
                            newCategory = newCategory.trim();
                            if (newCategory.equalsIgnoreCase("")) {
                                System.out.println("Sorry, you cannot type space values and enter.");
                            }
                        }while(newCategory.equalsIgnoreCase(""));
                        this.getFlowerByName(nameSearch).setCategory(newCategory);
                        break;
                    default:
                        String ans = "";
                        do {
                            ans = Utils.getStringreg("Do you want to confirm those information[Y/N]? ", "[YyNn]", "Cannot be empty", "You should input Y or N.");
                        } while (ans.equalsIgnoreCase("N"));
                        
                }
            } while (choice > 0 && choice < 6);
            System.out.println("Updated successfully.");
        } else {
            System.out.println("The Flower does not exist.");
        }
    }

    @Override
    public void deleteFlower() {
        this.displayFlowerList();
        String IDSearch = Utils.getStringreg("Enter flower ID (Fxxxx - example: F0001):", "F\\d{4}", "Cannot be null", "Wrong input format");
        IDSearch = IDSearch.trim().toUpperCase();
        for (Flower flower : flowerSet) {
            if (flower.getId().equalsIgnoreCase(IDSearch)) {
                String choice = Inputter.inputYesNo("Do you want to confirm to remove? (Y/N)");
                if (choice.equalsIgnoreCase("Y")) {
                    flowerSet.remove(flower);
                    System.out.println("The flower " + IDSearch + " has been removed.");
                    break;
                } else {
                    System.out.println("Delete failed.");
                }
            }
        }
    }

    @Override
    public void displayFlowerList() {
        System.out.println("Flower List results: ");
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
        System.out.println("| Flower ID |       Description           |   Import Date    |     Unit Price     |       Category     |");
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
        for (Flower flower : flowerSet) {
            flower.screenTime();
        }
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
    }

    public static Flower getFlowerByID(String data) {
        Flower result = null;
        for (Flower flower : flowerSet) {
            if (flower.getId().equalsIgnoreCase(data)) {
                result = flower;
                return result;
            }
        }
        return null;
    }

    public boolean checkDupplicateID(String id) {
        for (Flower flower : flowerSet) {
            if (flower.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void showFlowerList() {
        System.out.println("Flower List results: ");
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
        System.out.println("| Flower ID |       Description           |   Import Date    |     Unit Price     |       Category     |");
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
        for (Flower flower : flowerSet) {
            flower.screenTime();
        }
        System.out.println("+----------+------------------------------+------------------+--------------------+--------------------+");
    }
    
    public static Flower getFlowerByName(String data){
        Flower result = null;
        for (Flower flower : flowerSet) {
            if (flower.getDescription().equalsIgnoreCase(data)) {
                result = flower;
                return result;
            }
        }
        return null;
    }
    @Override
    public void findFlowerDate(){
        LocalDate date = Utils.getDate("Input id or part the flower's name: ","Wrong format");
        for (Flower flower : flowerSet) {
            if (flower.getImportDate().equals(date)) {
                flower.screenTime();
            }
        }
    }

}
