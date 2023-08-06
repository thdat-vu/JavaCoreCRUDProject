/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package MyFactory;

import data_object.INurseDAO;
import data_object.INurseDAO;
import data_object.INurseDAO;
import data_object.IPatientDAO;
import data_object.IPatientDAO;
import data_object.IPatientDAO;

/**
 *
 * @author LENOVO
 */
public interface IDAOFactory{
    IPatientDAO patientDAO();
    INurseDAO nurseDAO();
}
