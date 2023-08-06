/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyFactory;

import data_object.INurseDAO;
import data_object.INurseDAO;
import data_object.INurseDAO;
import data_object.IPatientDAO;
import data_object.IPatientDAO;
import data_object.IPatientDAO;
import data_object.NurseDAO;
import data_object.NurseDAO;
import data_object.NurseDAO;
import data_object.PatientDAO;
import data_object.PatientDAO;
import data_object.PatientDAO;

/**
 *
 * @author LENOVO
 */
public class Factory implements IDAOFactory{

    @Override
    public IPatientDAO patientDAO() {
        return new PatientDAO();
        
    }

    @Override
    public INurseDAO nurseDAO() {
        return new NurseDAO();
    }

    
}
