
package business_objects;

import data_object.PatientDAO;

import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class Nurse extends Person{
    private String staffID;
    private String department;
    private String shift;
    private long salary;
    private ArrayList<String> patientsAssigned = new ArrayList<>();

    public Nurse(String id, String name, int age, String gender, String address, String phone, String staffID, String department, String shift, long salary) {
        super(id, name, age, gender, address, phone);
        this.staffID = staffID;
        this.department = department;
        this.shift = shift;
        this.salary = salary;
    }
    
    public void addPatientAssigned(String patientID) {

        if (patientsAssigned.size() < 2) {
            Patient patient = PatientDAO.searchPatient(patientID);
            if (patient.getNursesAssigned().size()<2){
                patientsAssigned.add(patientID);
            }
        }
        else {
            System.out.println("The patient is taken cared by 2 nurses.");
        }
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public ArrayList<String> getPatientsAssigned() {
        return patientsAssigned;
    }

    public void setPatientsAssigned(ArrayList<String> patientsAssigned) {
        this.patientsAssigned = patientsAssigned;
    }
    
    
    //chua xong dau
    
    public void screenTime() {
        //"%s\t\t%s\t\t%s\t%s"
        if (this.getPatientsAssigned().size()==2){
            System.out.println(staffID +"\t\t" + name +"\t\t\t  " + phone +"\t" + department + "\t\t\t" + this.getPatientsAssigned().get(0) +","+this.getPatientsAssigned().get(1));
        } else if (this.getPatientsAssigned().size() == 1) {
            System.out.println(staffID +"\t\t" + name +"\t\t\t  " + phone +"\t" + department +  "\t\t\t" +this.getPatientsAssigned().get(0));
        } else if (this.getPatientsAssigned().isEmpty()) {
            System.out.println(staffID +"\t\t" + name +"\t\t\t  " + phone +"\t" + department);
        }

    }
    //String id, String name, int age, String gender, String address, String phone, String staffID, String department, String shift, long salary
    @Override
    public String toString() {
        return  id + "," + name + "," + age + "," + gender + "," + address+ ","+phone + ","+staffID + "," + department + ","+ shift + ","+ salary;
    }
    

}
