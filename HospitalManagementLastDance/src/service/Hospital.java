/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import MyFactory.Factory;
import MyFactory.IDAOFactory;
import data_object.INurseDAO;
import data_object.IPatientDAO;

/**
 *
 * @author LENOVO
 */
public class Hospital implements IService{
    static final IDAOFactory factory= new Factory();
    static final INurseDAO nurseDao= factory.nurseDAO();
    static final IPatientDAO patientDao= factory.patientDAO();
    
    @Override
    public void creatNurse() {
        try {
            nurseDao.creatNurse();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void findNurse() {
        
            nurseDao.findNurse();
        
    }

    @Override
    public void updateNurse() {
        try {
            nurseDao.updateNurse();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void deleteNurse() {
        try {
            nurseDao.deleteNurse();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void createPatient() {
        try {
            patientDao.createPatient();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void displayPatientList() {
        try {
            patientDao.displayPatientList();
        } catch (Exception ex) {
            System.out.println("ERROR!");
        }
    }

    @Override
    public void displayNurseList() {
        nurseDao.showNurseList();
    }

    @Override
    public void sortPatientList()  {

        try {
            patientDao.sortPatientList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean savePatientFile(String filepath) {
        

            return patientDao.savePatientFile(filepath);
        
    }

    @Override
    public boolean loadPatientFile(String filepath) {
        
            return patientDao.loadPatientFile(filepath);
        
    }

    @Override
    public boolean saveNurseFile(String filepath) {
        
            return nurseDao.saveNurseFile(filepath);
        
    }

    @Override
    public boolean loadNurseFile(String filepath) {
        
            return nurseDao.loadNurseFile(filepath);
        
        
    }
    
}
