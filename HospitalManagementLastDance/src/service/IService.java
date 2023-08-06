/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

/**
 *
 * @author LENOVO
 */
public interface IService {
    public void creatNurse();
    public void findNurse();
    public void updateNurse();
    public void deleteNurse();
    public void createPatient();
    public void displayPatientList();
    public void displayNurseList();
    public void sortPatientList();
    public boolean savePatientFile(String filepath);
    public boolean loadPatientFile(String filepath);
    public boolean saveNurseFile(String filepath);
    public boolean loadNurseFile(String filepath);
//    void displayPatientOfANurse();
//    void displayNurseAssignedForAPatient();
}
