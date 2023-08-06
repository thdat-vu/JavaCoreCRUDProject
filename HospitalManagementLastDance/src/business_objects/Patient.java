/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business_objects;

import data_object.NurseDAO;
import static data_object.NurseDAO.nurseList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author LENOVO
 */
public class Patient extends Person{
    private String diagnosis;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private ArrayList<String> nursesAssigned;
    public Patient(String id, String name, int age, String gender, String address, String phone, String diagnosis, LocalDate admissionDate, LocalDate dischargeDate){
        super(id, name, age, gender, address, phone);
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        nursesAssigned = new ArrayList<String>();
    }
    
    public void addNurseAssigned(String nurseID){
        if (nursesAssigned.size() < 2) {
            nursesAssigned.add(nurseID);
        }
        else {
            System.out.println("The nurse is taking care of by 2 nurses.");
        }
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public ArrayList<String> getNursesAssigned() {
        return nursesAssigned;
    }

    public void setNursesAssigned(ArrayList<String> nursesAssigned) {
        this.nursesAssigned = nursesAssigned;
    }
    //chua xong dau
    //|Patient Id| Admission Date|          Full name           |   Phone    |      Diagnosis     |
    public void screenTime() {
        System.out.println(id + "\t\t" + admissionDate + "\t\t" + name + "\t\t  " + phone + "\t" + diagnosis);
    }
    
    //String id, String name, int age, String gender, String address, String phone, String diagnosis, LocalDate admissionDate, LocalDate dischargeDate){
    //2 thằng khốn nạn ngày nhập vs ngày xuất viện nhớ nhìn cho kĩ.

    @Override
    public String toString() {
        return this.getId() + ","+ this.getName() +","+this.getAge()+ "," + this.getGender() + "," + this.getAddress() +"," + this.getPhone() + "," + this.getDiagnosis() + "," + this.getAdmissionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "," + this.getDischargeDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    
    
}
