/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data_object;

/**
 *
 * @author LENOVO
 */
public interface IPatientDAO {
   public boolean savePatientFile(String filepath) ;
   public boolean loadPatientFile(String filepath) ;
   public void createPatient() throws Exception;
   public void displayPatientList() throws Exception;
   public void sortPatientList() throws Exception;
   
}
