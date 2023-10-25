package data_object;

public interface IOrderDAO {

    public boolean saveOderFile(String filepath);
    public boolean loadOrderFile(String filepath);
    public void createOrder();
    public void displayOrder();
    public void sortOrder();

}
