package MyFactory;

import data_object.IFlowerDAO;
import data_object.IOrderDAO;

public interface IFactory {
    IFlowerDAO flowerDAO();
    IOrderDAO orderDAO();
}
