/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import MyUtils.Utils;
import business_objects.Nurse;
import business_objects.Patient;
import static data_object.PatientDAO.patientList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author LENOVO
 */
public class NurseDAO implements INurseDAO {

    public static String nurseFile = "nurses.txt";
    public static HashMap<String, Nurse> nurseList;

    public NurseDAO() {
        nurseList = new HashMap<>();

    }
    Scanner sc = new Scanner(System.in);

    @Override
    public boolean saveNurseFile(String nurseFile) {
        File f = new File(nurseFile);
        PrintWriter w = null;
        try {
            if (f.exists()) {
                String ans = Utils.getStringreg("Do you want to override nurse file? (Y/N): ", "[YyNn]", "You should answer: Y/N", "Wrong input format.");
                if (ans.equalsIgnoreCase("n")) {
                    return false;
                }
                //ghi file
                w = new PrintWriter(nurseFile);
                for (Nurse nurse : nurseList.values()) {
                    //cham 2 thang benh nhan
                    if (nurse.getPatientsAssigned().size() == 2) {
                        w.println(nurse.toString() + "," + nurse.getPatientsAssigned().get(0) + "," + nurse.getPatientsAssigned().get(1));
                    }
                    //cham 1 thang benh nhan

                    if (nurse.getPatientsAssigned().size() == 1) {
                        w.println(nurse.toString() + "," + nurse.getPatientsAssigned().get(0));
                    }
                    //khong co cham nom thang benh nhan nao het

                    if (nurse.getPatientsAssigned().isEmpty()) {
                        w.println(nurse.toString());
                    }
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
                System.out.println("Closing nurse nurse file error.");
            }
        }
        return true;
    }

    @Override
    public boolean loadNurseFile(String filepath) {
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
                    Nurse nurse = new Nurse(arr[0].trim(), arr[1].trim(), Integer.parseInt(arr[2].trim()), arr[3].trim(), arr[4].trim(), arr[5].trim(), arr[6].trim(), arr[7].trim(), arr[8].trim(), Long.parseLong(arr[9].trim()));

                    if (arr.length == 12) {// nurse has taken care of 2 patients
                        nurse.getPatientsAssigned().add(arr[10].trim());
                        nurse.getPatientsAssigned().add(arr[11].trim());
                    } else if (arr.length == 11) {//nurse has taken care of 1 patient
                        nurse.getPatientsAssigned().add(arr[10].trim());
                    }
                    nurseList.put(nurse.getStaffID(), nurse);
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
    public void creatNurse() {
        this.displayNurse();
        String id, name, gender, address, phone, staffID, department, shift;
        int age;
        long salary;
        do {
            staffID = Utils.getStringreg("Enter your staff ID (Yxxx - example: Y0001): ", "^Y\\d{4}$", "Cannot be null", "Wrong input format");
            if (nurseList.containsKey(staffID)) {
                System.out.println("The Staff ID has already existed.");
            }
        } while (nurseList.containsKey(staffID));
        name = Utils.getString("Enter your name please: ", "Please fill in your name");
        do {
            id = Utils.getStringreg("Enter your id: (Example: P1234): ", "^P\\d{4}$", "Your ID must be filled in", "Wrong input format");
            if (this.checkDupplicateID(id)) {
                System.out.println("The ID has already existed.");
            }
        } while (this.checkDupplicateID(id));
        age = Utils.getInt("Enter your age: ", 17);
        gender = Utils.getString("Enter your gender: ", "Gender must be filled in");
        address = Utils.getString("Enter address: ", "Address must be filled in");
        phone = Utils.getStringreg("Enter your phone please: ", "^0[35789]\\d{8}$", "Your phone must be filled in", "Invalid phone number");
        department = Utils.getStringreg("Enter department: ", ".{3,5}", "Department must be filled in.", "Wrong input format.");
        shift = Utils.getString("Enter shift: ", "Shift cannot be empty");
        salary = Utils.getLong("Enter salary: ", 0);
        Nurse tmpNurse = new Nurse(id, name, age, gender, address, phone, staffID, department, shift, salary);
        nurseList.put(staffID, tmpNurse);
        System.out.println("Create Nurse successfully");
    }

    @Override
    public void findNurse() {

        String data = Utils.getString("Enter a Patient's ID: ", "Cannot be empty");
        data = data.trim();
        if (nurseList.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            ArrayList<Nurse> nameList = new ArrayList();
            for (Nurse nurse : nurseList.values()) {
                if (nurse.getPatientsAssigned().get(0).contains(data)) {
                    nameList.add(nurse);
                }
                
            }
            if (nameList.isEmpty()) {
                System.out.println("The nurse does not exist.");

            } else {
                System.out.println("Search results: ");
                System.out.println("+----------+------------------------------+------------+--------------------+--------------------+");
                System.out.println("| Nurse ID |          Full name           |   Phone    |     Department     |  Patients Cared    |");
                System.out.println("+----------+------------------------------+------------+--------------------+--------------------+");
                for (Nurse nurse : nameList) {
                    nurse.screenTime();
                }
                System.out.println("+----------+------------------------------+------------+--------------------+");
            }
        }

    }

    @Override
    public void updateNurse() {
        displayNurse();
        String staffIDSearch = Utils.getStringreg("Enter staff ID (Yxxx - example: Y0001):", "Y\\d{4}", "Cannot be null", "Wrong input format");
        if (nurseList.get(staffIDSearch) != null) {
            System.out.println("[1]  - ID");
            System.out.println("[2]  - Name");
            System.out.println("[3]  - Age");
            System.out.println("[4]  - Gender");
            System.out.println("[5]  - Address");
            System.out.println("[6]  - Phone number");
            System.out.println("[7]  - Department");
            System.out.println("[8]  - Shift");
            System.out.println("[9]  - Salary");
            System.out.println("[10] - Patients assigned");
            System.out.println("[0] or [11] - Update and turn back to main menu.");
            int choice;
            do {
                choice = Utils.getChoice("Enter your choice: ", 0, 11);
                switch (choice) {
                    case 1:
                        String id = Utils.getStringreg("Enter your id: (Example: P1234)", "^P\\d{4}", "Your ID must be filled in", "Wrong input format");
                        do {
                            if (this.checkDupplicateID(id)) {
                                System.out.println("The ID have already exist.");
                            }
                        } while (this.checkDupplicateID(id));
                        if (!this.checkDupplicateID(id)) {
                            nurseList.get(staffIDSearch).setId(id);
                        }

                        break;
                    case 2:
                        String name = Utils.getString("Enter name: ", "Name cannot be empty");
                        nurseList.get(staffIDSearch).setName(name);
                        break;
                    case 3:
                        int age = Utils.getInt("Enter new age: ", 17);
                        break;
                    case 4:
                        String gender = Utils.getString("Enter new gender", "Gender cannot be empty");
                        nurseList.get(staffIDSearch).setGender(gender);
                        break;
                    case 5:
                        String address = Utils.getString("Enter new address", "Address cannot be empty.");
                        nurseList.get(staffIDSearch).setAddress(address);
                        break;
                    case 6:
                        break;
                    case 7:
                        String department = Utils.getString("Enter new department: ", "Department's name cannot be empty.");
                        nurseList.get(staffIDSearch).setDepartment(department);
                        break;
                    case 8:
                        String shift = Utils.getString("Enter new shift: ", "Shift cannot be null");
                        nurseList.get(staffIDSearch).setShift(shift);
                        break;
                    case 9:
                        long salary = Utils.getLong("Enter new salary", 0);
                        nurseList.get(staffIDSearch).setSalary(salary);
                        break;
                    case 10:
                        System.out.println("Choose the patient on the list.");
                        for (Map.Entry<String, Patient> entry : patientList.entrySet()) {
                            System.out.println(entry.getKey() + "-" + entry.toString());
                        }
                        String idPatientSearch = Utils.getStringreg("Enter patient id: (Example: P1234)", "^P\\d{4}$", "Your ID must be filled in", "Wrong input format");
                        idPatientSearch = idPatientSearch.trim();
                        Patient patient = patientList.get(idPatientSearch);
                        if (patient.getNursesAssigned().size() == 2) {
                            System.out.println("The patient is taken care of by 2 nurses.");
                        } else {
                            nurseList.get(staffIDSearch).addPatientAssigned(patient.getId());
                        }

                        break;
                    default:
                        String ans = "";
                        do {
                            ans = Utils.getStringreg("Do you want to confirm those information[Y/N]? ", "[YyNn]", "Cannot be empty", "You should input Y or N.");
                        } while (ans.equalsIgnoreCase("N"));

                }
            } while (choice > 0 && choice < 11);
            System.out.println("Updated successfully.");
        } else {
            System.out.println("The nurse does not exist.");
        }
    }

    @Override
    public void deleteNurse() {
        showNurseList();
        String staffIDSearch = Utils.getStringreg("Enter staff ID (Yxxx - example: Y0001): ", "Y\\d{4}", "Cannot be null", "Wrong input format");
        staffIDSearch = staffIDSearch.trim();
        if (nurseList.get(staffIDSearch) == null) {
            System.out.println("The nurse does not exist.");
        } else {
            if (nurseList.get(staffIDSearch).getPatientsAssigned().size() == 0) {
                nurseList.remove(staffIDSearch);
                System.out.println("The nurse " + staffIDSearch + " has been removed.");
            } else {
                System.out.println("The nurse cannot be removed because he/she is taking care of another patients.");
            }
        }
    }

    @Override
    public void showNurseList() {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        tmpNurseList.addAll(nurseList.values());

        sortByStaffID(tmpNurseList);

    }

    public static Nurse searchNurseID(String staffID) {
        Nurse nure = nurseList.get(staffID);
        if (!nurseList.containsKey(staffID)) {
            return null;
        }
        return nure;
    }

    public void displayNurse() {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        tmpNurseList.addAll(nurseList.values());

        //sortByStaffID(tmpNurseList);
        sortByStaffID(tmpNurseList);

    }

    public boolean checkDupplicateID(String id) {
        ArrayList<Nurse> tmpNurseList = new ArrayList<>();
        for (Nurse nurse : nurseList.values()) {
            if (nurse.getId().equalsIgnoreCase(id)) {
                tmpNurseList.add(nurse);
            }
        }
        if (tmpNurseList.isEmpty()) {
            return false;
        }
        return true;
    }

    public void sortByStaffID(List<Nurse> tmpNurseList) {
        Collections.sort(tmpNurseList, (Nurse t , Nurse t1) -> t.getStaffID().compareTo(t1.getStaffID()));
//        Collections.sort(tmpNurseList, new Comparator<Nurse>() {
//            @Override
//            public int compare(Nurse t, Nurse t1) {
//                return t.getStaffID().compareTo(t1.getStaffID());
//            }
//        });
        System.out.println("LIST OF NURSES");
        System.out.println("Sorted by: Nurse's StaffID");
        System.out.println("+----------+------------------------------+------------+--------------------+--------------------+");
        System.out.println("| Nurse ID |          Full name           |   Phone    |     Department     |  Patients Cared    |");
        System.out.println("+----------+------------------------------+------------+--------------------+--------------------+");
        for (Nurse nurse
                : tmpNurseList) {
            nurse.screenTime();
        }
        System.out.println("+----------+------------------------------+------------+--------------------+--------------------+");
    }

}
