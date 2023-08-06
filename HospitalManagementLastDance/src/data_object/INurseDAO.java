/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package data_object;

/**
 *
 * @author LENOVO
 */
public interface INurseDAO {
    public boolean saveNurseFile(String filepath);
    public boolean loadNurseFile(String filepath);
    public void creatNurse();
    public void findNurse();
    public void updateNurse();
    public void deleteNurse();
    public void showNurseList();
    //tam thoi vay di
}
