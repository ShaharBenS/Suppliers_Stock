package DAL;

import SharedClasses.Item;
import SharedClasses.Quantity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Items {
    private Connection c;
    private Statement stmt;


    public Items(Connection c) {
        this.c = c;
        stmt = null;
    }

    public boolean addItem(Item item) {
        try
        {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Items (ID, Name, CategoryNumber, Manufacture) " +
                    "VALUES (?,?,?,?);");
            ps.setInt(1, item.getItemID());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getCategoryNumber());
            ps.setString(4, item.getManufacture());
            ps.executeUpdate();

            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public Item[] getAllItems()
    {
        Item[] items = null;
        List<Item> itemList = new ArrayList<>();
        String query =  "SELECT * " +
                        "FROM ITEMS;";

        try
        {
            Statement statement = c.createStatement();
            ResultSet result = statement.executeQuery(query);

            int index = 0;
            while(result.next())
            {
                Item item = new Item(result.getInt("ID"),result.getString("NAME"),
                        result.getInt("CategoryNumber"),result.getString("MANUFACTURE"));

                itemList.add(item);
                index++;
            }
            items = new Item[index];
            return itemList.toArray(items);

        } catch (SQLException e)
        {
            return null;
        }
    }

    public boolean setID(int id, int newId) {
        try {
            String sql = "UPDATE Items SET ID = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, newId);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setName(int id, String Name) {
        try {
            String sql = "UPDATE Items SET Name = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, Name);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean setCategory(int id, int category){
        try {
            String sql = "UPDATE Items SET CategoryNumber = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, category);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean setManufacture(int id, String manufacture){
        try {
            String sql = "UPDATE Items SET Manufacture = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, manufacture);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Item getItem(int id) {
        Item item = null;
        try {
            String sqlQuary = "SELECT * FROM Items WHERE ID = " + id + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            item = new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return item;
    }

    public String getItemName (int id){
        String itemName = "";
        try {
            String sqlQuary = "SELECT Name FROM Items WHERE ID = " + id + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            itemName = rs.getString(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return itemName;
    }

    public boolean removeItem(int id) {
        try {
            String sql = "DELETE FROM Items WHERE ID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public boolean ifExist(int itemId) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Items where ID = '" + itemId + "';");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }

    }

}
