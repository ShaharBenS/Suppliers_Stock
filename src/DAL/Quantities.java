package DAL;

import SharedClasses.Quantity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shahar on 21/04/17.
 */
public class Quantities
{
    private Connection conn;

    public Quantities(Connection conn)
    {
        this.conn = conn;
    }

    public boolean addItemQuantity(Quantity quantity)
    {
        try
        {
            PreparedStatement p_stmt = conn.prepareStatement("INSERT INTO Quantites(ID,LOCATION,MINIMUM,ORDER,WAREHOUSE," +
                    "STORE,DEFECTS) VALUES(?,?,?,?,?,?,?);");
            p_stmt.setInt(1,quantity.getId());
            p_stmt.setString(2,quantity.getLocation());
            p_stmt.setInt(3,quantity.getMinimum());
            p_stmt.setInt(4,quantity.getAmount_to_order());
            p_stmt.setInt(5,quantity.getWarehouse());
            p_stmt.setInt(6,quantity.getStore());
            p_stmt.setInt(7,quantity.getDefects());
            p_stmt.executeUpdate();

            conn.commit();
            p_stmt.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /*
        This method will update 'fieldName' to 'newValue'.
     */
    public boolean updateField(String fieldName,int itemID,Object newValue)
    {
        String query = "UPDATE QUANTITIES SET "+fieldName+" = '"+newValue+"' WHERE ID = "+itemID+";";
        try
        {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query) > 0;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    public boolean updateLocation(int id, String newLocation)
    {
        return updateField("LOCATION",id,newLocation);
    }

    public boolean updateMinimum(int id, int minimum_amount)
    {
        return updateField("MINIMUM",id,minimum_amount);
    }
    public boolean updateOrder(int id, int order_amount)
    {
        return updateField("ORDER",id,order_amount);
    }

    public boolean updateWarehouse(int id, int warehouse_amount)
    {
        return updateField("WAREHOUSE",id,warehouse_amount);
    }

    public boolean updateStore(int id, int store_amount)
    {
        return updateField("STORE",id,store_amount);
    }

    public boolean updateDefects(int id, int defects_amount)
    {
        return updateField("DEFECTS",id,defects_amount);
    }

}
