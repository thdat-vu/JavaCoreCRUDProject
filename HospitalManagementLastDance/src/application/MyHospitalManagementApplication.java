/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import MyUtils.Inputter;
import MyUtils.Utils;
import data_object.NurseDAO;
import data_object.PatientDAO;
import java.util.Scanner;
import service.Hospital;

/**
 *
 * @author LENOVO
 */
public class MyHospitalManagementApplication {

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner sc = new Scanner(System.in);
       if (!hospital.loadNurseFile(NurseDAO.nurseFile)||!hospital.loadPatientFile(PatientDAO.PatientFile))  {System.out.println("load fail!");}
//        PatientDAO patientDAO = new PatientDAO();
//        NurseDAO nurseDAO = new NurseDAO();

        int option = 0;
        String ans;
        System.out.println("\t=======HOSPITAL MANAGEMENT=======");
        HOME:
        do {
            System.out.println("1. Nurse's management");
            System.out.println("[1]     1.1 Create a nurse");
            System.out.println("[2]     1.2 Find the nurse");
            System.out.println("[3]     1.3 Update the nurse");
            System.out.println("[4]     1.4 Delete the nurse");
            System.out.println("[5]     1.5 Display nurses");
            System.out.println("2. Patient's management");
            System.out.println("[6]     2.1 Add a patient");
            System.out.println("[7]     2.2 Display patients");
            System.out.println("[8]     2.3 Sort the patients list");
            System.out.println("[9]     2.4 Save data");
            System.out.println("[10]    2.5 Load data");
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
                            hospital.creatNurse();
                        choice = Inputter.inputYesNo("Do you want to continue adding a new nurse?(Y/N)");
                    } while (choice.equalsIgnoreCase("y"));
                }
                break;

                case 2: {
                        hospital.findNurse();
                }
                break;
                case 3: {
                        hospital.updateNurse();
                }
                break;
                case 4: {
                        hospital.deleteNurse();
                }
                case 5: {
                    hospital.displayNurseList();
                }
                break;
                case 6: {
                        hospital.createPatient();
                }
                break;
                case 7: {
                        hospital.displayPatientList();
                }
                break;
                case 8: {
                        hospital.sortPatientList();
                }
                break;
                case 9: {
                        if(hospital.savePatientFile(PatientDAO.PatientFile)) System.out.println("Save patients successfully");
                        if(hospital.saveNurseFile(NurseDAO.nurseFile)) System.out.println("Save nurses successfully");
                }
                break;

                case 10: {
                    try {
                        if(hospital.loadPatientFile(PatientDAO.PatientFile)) System.out.println("Load patients successfully");
                        if(hospital.loadNurseFile(NurseDAO.nurseFile)) System.out.println("Load nurses successfully");
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                }
                break;
                default:
                    hospital.savePatientFile(PatientDAO.PatientFile);
                    hospital.saveNurseFile(NurseDAO.nurseFile);
                    ans = Utils.getStringreg("Do you want to quit?(y/n): ", "[YyNn]", "You should input something here: ","You should input y or n");
                    if (ans.equalsIgnoreCase("y")) {
                        System.out.println("Thank you for using service.");
                    }else if (ans.equalsIgnoreCase("n")){
                        option = 5;
                        continue HOME;
                    }
            }
        } while (option > 0 && option < 11);
    }
}
