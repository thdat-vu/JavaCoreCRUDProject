package MyFactory;

import business_object.Order;
import data_object.FlowerDAO;
import data_object.IFlowerDAO;
import data_object.IOrderDAO;
import data_object.OrderDAO;
public class Factory implements IFactory{

    @Override
    public IFlowerDAO flowerDAO() {
        return new FlowerDAO();
    }

    @Override
    public IOrderDAO orderDAO() {
        return new OrderDAO();
    }
}
