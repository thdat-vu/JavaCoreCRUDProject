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

public class FlowerDAO implements IFlowerDao{
    public static Set<Flower> flowerSet;

    public FlowerDAO() {
        flowerSet = new HashSet<>();
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
            flowerid = Utils.getStringreg("Input new Flower's ID(Fxxx(x is a number) - for example: F0001): ", "^F\\d{4}", "Cannot be empty", "Wrong format ID");
            if (checkDupplicateID(flowerid)){
                System.out.println("Sorry, the ID has been existed.");
                cont = true;
            }
            else{
                cont = false;
            }
        }while(cont);

        descript = Utils.getStringreg("Input the description: ", ".{3,50}", "Cannot be empty", "Wrong format! Must contain 3 to 50 characters");
        impDate = Utils.getDate("Input the import date", "It must follow the pattern: (dd/MM/yyyy)");
        unitprice = Utils.getInt("Enter the unit price($): ", 0);
        catergory = Utils.getString("Input the category: ", "Cannot be empty");
        Flower newFlower = new Flower(flowerid, descript, impDate, unitprice, catergory);
        
        flowerSet.add(newFlower);
        System.out.println("Adding the new Flower successfully.");
    }

    @Override
    public void findFlower() {
        Flower result = null;
        String data = Utils.getString("Input id or part the flower's name: ", "Cannot be empty");
        data = data.trim().toUpperCase();
       
        if (flowerSet.isEmpty()){
            System.out.println("The Flowers list is empty.");
        }
        else{
            for (Flower flower : flowerSet) {
                if (flower.getId().equals(data)||flower.getCategory().contains(data)) {
                    result = flower;
                    System.out.println(flower);
                    break;
                }
                else{
                    System.out.println("The flower does not exist.");
                }
            }
        }
    }

    @Override
    public void updateFlower() {
        displayFlowerList();
        String IDSearch = Utils.getStringreg("Enter staff ID (Yxxx - example: Y0001):", "Y\\d{4}", "Cannot be null", "Wrong input format");
        if (this.getFlower(IDSearch) != null) {
            System.out.println("[1]  - ID");
            System.out.println("[2]  - Description");
            System.out.println("[3]  - Import Date");
            System.out.println("[4]  - Category");
            System.out.println("[0] or [5] - Update and turn back to main menu.");
            int choice;
            do {
                choice = Utils.getChoice("Enter your choice: ", 0, 5);
                switch (choice) {
                    case 1:
                        String id = Utils.getStringreg("Enter your id: (Example: P1234)", "^P\\d{4}", "Your ID must be filled in", "Wrong input format");
                        do {
                            if (this.checkDupplicateID(id)) {
                                System.out.println("The ID have already exist.");
                            }
                        } while (this.checkDupplicateID(id));
                        if (!this.checkDupplicateID(id)) {
                            this.getFlower(IDSearch).setId(id);
                        }

                        break;
                    case 2:
                        String newDiscription = Utils.getString("Enter new discription: ", "Discription cannot be empty");
                        this.getFlower(IDSearch).setDescription(newDiscription);
                        break;
                    case 3:
                        LocalDate newDate = Utils.getDate("Enter new importDate", "Wrong format! Try to input valid date");
                        this.getFlower(IDSearch).setImportDate(newDate);
                    case 4:
                        String newCategory = Utils.getString("Enter new category", "Category cannot be empty");
                        this.getFlower(IDSearch).setCategory(newCategory);
                        break;
                    
                    default:
                        String ans = "";
                        do {
                            ans = Utils.getStringreg("Do you want to confirm those information[Y/N]? ", "[YyNn]", "Cannot be empty", "You should input Y or N.");
                        } while (ans.equalsIgnoreCase("N"));

                }
            } while (choice > 0 && choice < 5);
            System.out.println("Updated successfully.");
        } else {
            System.out.println("The Flower does not exist.");
        }
    }

    @Override
    public void deleteFlower() {
        String IDSearch = Utils.getStringreg("Enter staff ID (Yxxx - example: Y0001):", "Y\\d{4}", "Cannot be null", "Wrong input format");
        IDSearch = IDSearch.trim().toUpperCase();
        for (Flower flower : flowerSet) {
            if (flower.getId().equalsIgnoreCase(IDSearch)) {
                String choice = Inputter.inputYesNo("Do you want to confirm to remove? (Y/N)");
                if (choice.equalsIgnoreCase("Y")) {
                    flowerSet.remove(flower);
                    System.out.println("The flower " + IDSearch + " has been removed.");
                }
                else{
                    System.out.println("Delete failed.");
                }
            }
        }
    }

    @Override
    public void displayFlowerList() {
        Iterator<Flower> it = flowerSet.iterator();
        while (it.hasNext()){
            System.out.println(it);
            it = (Iterator<Flower>) it.next();
        }
    }

    public static Flower getFlower(String data){
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
            if (flower.equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
       FlowerDAO flowerlist  = new FlowerDAO();
       flowerlist.createFlower();
       flowerlist.findFlower();
    }
}

