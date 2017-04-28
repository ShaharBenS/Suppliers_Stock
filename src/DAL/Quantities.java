package DAL;

import SharedClasses.Quantity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            PreparedStatement p_stmt = conn.prepareStatement("INSERT INTO Quantities(ItemID,LOCATION,MINIMUM,ORDER_AMOUNT,WAREHOUSE," +
                    "STORE,DEFECTS) VALUES(?,?,?,?,?,?,?);");
            p_stmt.setInt(1,quantity.getItemID());
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
        returning Quantities object by given id, or null if not exist
    */
    public Quantity getQuantity(int id)
    {
        Quantity q = null;

        try
        {
            String query = "SELECT * FROM QUANTITIES AS Q WHERE Q.itemID = "+id+";";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            q = new Quantity(resultSet.getInt("ItemID"),resultSet.getString("LOCATION"),
                    resultSet.getInt("DEFECTS"),resultSet.getInt("WAREHOUSE"),resultSet.getInt("MINIMUM"),
                    resultSet.getInt("STORE"),resultSet.getInt("ORDER_AMOUNT"));
            stmt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return q;
    }

    /*
        This method will update 'fieldName' to 'newValue'.
     */
    private boolean updateField(String fieldName, int orderID, Object newValue)
    {
        String query = "UPDATE QUANTITIES SET "+fieldName+" = '"+newValue+"' WHERE ID = "+orderID+";";
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
        return updateField("ORDER_AMOUNT",id,order_amount);
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


    public Quantity[] getAllDefectItems()
    {
        Quantity[] items = null;
        List<Quantity> itemList = new ArrayList<Quantity>();
        String query =  "SELECT * " +
                "FROM QUANTITIES" +
                "WHERE DEFECTS > 0";

        try
        {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            int index = 0;
            while(result.next())
            {
                Quantity quantity = buildQuantityFromResultSet(result);
                if(quantity == null)
                {
                    return null;
                }
                itemList.add(quantity);
                index++;
            }
            items = new Quantity[index];
            return itemList.toArray(items);

        } catch (SQLException e)
        {
            return null;
        }
    }

    private Quantity buildQuantityFromResultSet(ResultSet result)
    {
        try
        {
            return new Quantity(result.getInt("ItemID"),result.getString("LOCATION"),
                    result.getInt("DEFECTS"),result.getInt("WAREHOUSE"),result.getInt("MINIMUM"),
                    result.getInt("STORE"),result.getInt("ORDER_AMOUNT"));
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
