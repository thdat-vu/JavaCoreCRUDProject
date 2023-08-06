package data_object;

public interface IFlowerDao {
    public boolean saveFlowersFile (String filepath);
    public boolean loadFlowersFile (String filepath);

    public void createFlower();
    public void findFlower();
    public void updateFlower();
    public void deleteFlower();
    public void displayFlowerList();

}
