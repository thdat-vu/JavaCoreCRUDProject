/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import MyUtils.Utils;
import business_objects.Nurse;
import business_objects.Patient;
import static data_object.NurseDAO.nurseList;
import static data_object.NurseDAO.searchNurseID;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class PatientDAO implements IPatientDAO {

    public static String PatientFile = "patients.txt";
    public static HashMap<String, Patient> patientList;

    public PatientDAO() {
        patientList = new HashMap<>();
    }

    @Override
    public boolean savePatientFile(String filepath) {
        File f = new File(filepath);
        PrintWriter w = null;
        try {
           if(f.exists()){
                String ans = Utils.getStringreg("Do you want to override patients file? (Y/N): ", "[YyNn]", "You should answer: Y/N", "Wrong input format.");
                if (ans.equalsIgnoreCase("n")) {return false;}
                //ghi file
                w= new PrintWriter(f);
                for (Patient patient : patientList.values()) {
//                    thật ra là 2 dòng này có thể ko có vì đề bài yêu cầu bệnh nhân luôn có
//                    2 y tá chăm. Nhưng thôi cứ ghi cho nó chặt chẽ.

                    //incase: nếu ko có bệnh nhân nào đc y tá chăm sóc.
                    if (patient.getNursesAssigned().isEmpty()){
                        w.println(patient.toString());
                    }
                    else{
                        w.println(patient.toString()+","+patient.getNursesAssigned().get(0)+","+patient.getNursesAssigned().get(1));
                }

                }

            }

        } catch (Exception e) {
            System.out.println("ERROR");
        } finally {
            try {
                if(w!=null) w.close();
            } catch (Exception e) {
                System.out.println("Closing patients file error.");
            }
        }
        return true;
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))){
//            if(f.exists()){
//                String ans = Utils.getStringreg("Do you want to override patients file? (Y/N): ", "[YyNn]", "You should answer: Y/N", "Wrong input format.");
//                if (ans.equalsIgnoreCase("n")) {return false;}
//            }
//            for (Patient patient:
//                 patientList.values()) {
//                writer.write(patient.toString());
//                writer.close();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        catch (NullPointerException e ){
//            System.out.println("Null pointer exception.");
//        }
//        return true;
    }

    @Override
    public boolean loadPatientFile(String filepath) {
        File f = new File(filepath);
        FileReader fr = null;
        BufferedReader br = null;
        if (!f.exists()) {
            System.out.println("File not found.");
        } else {
            try {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    String s = br.readLine();
                    String arr[] = s.split(",");
                    Patient patient = new Patient(arr[0].trim(), arr[1].trim(), Integer.parseInt(arr[2].trim()), arr[3].trim(), arr[4].trim(), arr[5].trim(), arr[6].trim(), LocalDate.parse(arr[7].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.parse(arr[8].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    if (arr.length == 11) {
                        ArrayList<String> tmpNurseAss = new ArrayList<>();
                        tmpNurseAss.add(arr[9].trim());
                         tmpNurseAss.add(arr[10].trim());
                         patient.setNursesAssigned(tmpNurseAss);
//                        patient.getNursesAssigned().add(arr[9].trim());
//                        patient.getNursesAssigned().add(arr[10].trim());
                    }
                    patientList.put(patient.getId(), patient);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Out of index.");
            } catch (FileNotFoundException ex) {
                System.out.println("File Patient not found.");
            } catch (IOException ex) {
                System.out.println("System IO exception.");
            }catch(DateTimeException ex){
                System.out.println("Date cannot be parsed.");
            }catch(NullPointerException ex){
                System.out.println("Null pointer exception.");
            }
            finally {
                if (f.exists()) {
                    try {
                        br.close();
                        fr.close();
                    } catch (IOException ex) {
                        System.out.println("Closes Error.");
                    }
                    
                }
            }
        }
        return true;
    }

    @Override
    public void createPatient() throws Exception {
        String id, name, gender, address, phone, diagnosis;
        int age;
        LocalDate admissionDate, dischargeDate;
        name = Utils.getString("Enter your name please: ", "Please fill in your name");
        do {
            id = Utils.getStringreg("Enter your id: (Example: P1234): ", "^P\\d{4}$", "Your ID must be filled in", "Wrong input format");
            if (patientList.containsKey(id)) {
                System.out.println("The ID have already exist.");
            }
        } while (patientList.containsKey(id));

        age = Utils.getInt("Enter your age: ", 0);
        gender = Utils.getString("Enter your gender: ", "Gender must be filled in");
        address = Utils.getString("Enter address: ", "Address must be filled in");
        phone = Utils.getStringreg("Enter your phone please: ", "^0[35789]\\d{8}$", "Your phone must be filled in", "Invalid phone number");
        diagnosis = MyUtils.Inputter.inputNonBlankStr("Enter a diagnosis");
        admissionDate = Utils.getDate("Enter admission date: ", "Please fill in the admission date.");
        boolean cont = false;
        do {
            dischargeDate = Utils.getDate("Please fill in discharge date: ", "Please fill in valid discharge date. ");
            if (dischargeDate.isBefore(admissionDate)) {
                System.out.println("The discharge date is invalid");
                cont = true;
            }else{
                cont = false;
            }
        } while (cont);
        Patient tmpPatient = new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate);
        do {
            System.out.println("Nurses on the list.");
            for (Map.Entry<String, Nurse> entry : nurseList.entrySet()) {
                if (entry.getValue().getPatientsAssigned().size() < 2) {
                    System.out.println(entry.getKey() +"-"+entry.getValue());
                }
            }
            String staffidNurseSearch = Utils.getStringreg("Enter staff id: (Example: Y0001): ", "^Y\\d{4}$", "ID must be filled in", "Wrong input format");
            staffidNurseSearch = staffidNurseSearch.trim();
            //Nurse nurse = nurseList.get(staffidNurseSearch);
            if (tmpPatient.getNursesAssigned().isEmpty()) {
                tmpPatient.addNurseAssigned(staffidNurseSearch);
            }else if (!tmpPatient.getNursesAssigned().get(0).equalsIgnoreCase(staffidNurseSearch)) {
                tmpPatient.addNurseAssigned(staffidNurseSearch);
            }else{
                System.out.println("The nurse has been already added.");
            }

            
            Nurse n = searchNurseID(staffidNurseSearch);
            n.getPatientsAssigned().add(id);
        } while (tmpPatient.getNursesAssigned().size() < 2);

//        patientList.get().addPatientAssigned(patient);
        patientList.put(id, tmpPatient);

        System.out.println("Create patient successfully.");
    }

    @Override
    public void displayPatientList() throws Exception {
        System.out.println("Application show patients information based on typed date range(admission date)");
        LocalDate startDate = Utils.getDate("Please input start date: ", "Wrong format, try again");
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
        System.out.println("LIST OF PATIENTS");
        System.out.println("Start date: " + startDate);
        System.out.println("End date  : " + endDate);
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        
        for (Patient patient : patientList.values()) {
            if ((patient.getAdmissionDate().isAfter(startDate) && patient.getAdmissionDate().isBefore(endDate))
                    || patient.getAdmissionDate().isEqual(endDate) || patient.getAdmissionDate().isEqual(startDate)) {
                patient.screenTime();
            }
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }

    @Override
    public void sortPatientList() throws Exception {
        List<Patient> sortedPatient = new ArrayList<Patient>();
        for (Patient patient:
             patientList.values()) {
            sortedPatient.add(patient);
        }
        if (sortedPatient.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            int choice = 0;
            do {
                System.out.println("1. Sort by patient's ID: ");
                System.out.println("2. Sort by patient's name: ");
                System.out.print("What kind of sort do you want to choose? \n");
                choice = Utils.getChoice("Input [1] or [2] : ", 1, 2);
                switch (choice) {
                    case 1:
                        do {
                            System.out.println("1. ID: Ascending. ");
                            System.out.println("2. ID: Descending.");
                            choice = Utils.getChoice("Input [1] or [2]: ", 1, 2);
                            switch (choice) {
                                case 1:
                                    sortASCByID(sortedPatient);
                                    break;
                                case 2:
                                    sortDSCByID(sortedPatient);
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
                                    sortASCByName(sortedPatient);
                                    break;
                                case 2:
                                    sortDSCByName(sortedPatient);
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

    private void sortASCByID(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> t.getId().compareTo(t1.getId()));
//        Collections.sort(sortedPatient, new Comparator<Patient>() {
//            @Override
//            public int compare(Patient t, Patient t1) {
//                return t.getId().compareTo(t1.getId());
//            }
//        });
        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's ID");
        System.out.println("Sort order: ASC");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");

    }

    private void sortDSCByID(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> -1 * (t.getId().compareTo(t1.getId())));
        //Collections.sort(sortedPatient, new Comparator<Patient>() {
//            @Override
//            public int compare(Patient t, Patient t1) {
//                return -1*(t.getId().compareTo(t1.getId()));
//            }
//        });
        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's ID");
        System.out.println("Sort order: DSC");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");

    }

    private void sortASCByName(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> (t.getName().compareTo(t1.getName())));
//        Collections.sort(sortedPatient, new Comparator<Patient>() {
//            @Override
//            public int compare(Patient t, Patient t1) {
//                return (t.getName().compareTo(t1.getName()));
//            }
//        });
        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's Name");
        System.out.println("Sort order: ASC");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");

    }

    private void sortDSCByName(List<Patient> sortedPatient) {
        Collections.sort(sortedPatient, (Patient t, Patient t1) -> (t.getName().compareTo(t1.getName())) * -1);
//        Collections.sort(sortedPatient, new Comparator<Patient>() {
//            @Override
//            public int compare(Patient t, Patient t1) {
//                return (t.getName().compareTo(t1.getName())) * -1;
//                }
//        });
        System.out.println("LIST OF PATIENTS");
        System.out.println("Sorted by: Patient's Name");
        System.out.println("Sort order: DSC");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        System.out.println("|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |");
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
        for (Patient patient : sortedPatient) {
            patient.screenTime();
        }
        System.out.println("+----------+---------------+------------------------------+------------+--------------------+");
    }
    public static Patient searchPatient(String id){
        if (patientList.containsKey(id)){
            return patientList.get(id);
        }
        return null;
    }
}
