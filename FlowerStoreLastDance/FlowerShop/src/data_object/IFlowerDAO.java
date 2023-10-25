package data_object;

public interface IFlowerDAO {
    public boolean saveFlowersFile (String filepath);
    public boolean loadFlowersFile (String filepath);

    public void createFlower();
    public void findFlower();
    public void updateFlower();
    public void deleteFlower();
    public void displayFlowerList();
    public void findFlowerDate();
}
