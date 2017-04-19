package DAL;

import java.sql.Connection;
import SharedClasses.Order;
import java.sql.PreparedStatement;

/**
 * Created by rotem on 07/04/2017.
 */
public class Orders {

    private Connection c;

    public Orders(Connection c) {
        this.c = c;
    }

    public boolean addOrder(Order order) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Orders (OrderID, SupplierID, SupplierName, Date, ContactNumber) " +
                    "VALUES (?,?,?,?,?);");
            ps.setInt(1, order.getOrderID());
            ps.setInt(2, order.getSupplier());
            ps.setString(3, order.getSupplierName());
            ps.setString(4,order.getDate());
            ps.setString(5,order.getContactNum());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
