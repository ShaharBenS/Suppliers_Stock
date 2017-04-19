package DAL;

import SharedClasses.Category;
import SharedClasses.Products;

import java.sql.*;
import java.util.SimpleTimeZone;

/**
 * Created by Shahar on 29/03/17.
 */
public class  Category_Data
{
    private Connection connection;
    public Category_Data(Connection c)
    {
        connection = c;
    }


    public boolean addCategory(Category c)
    {
        String query = "INSERT INTO CATEGORY(ID,NAME,ID_FATHER) VALUES(?,?,?)";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, c.getId());
            _ps.setString(2, c.getName());
            if(c.getIdFather()!=-1)
                _ps.setInt(3, c.getIdFather());

            _ps.executeUpdate();
            return true;

        } catch (SQLException e)
        {
            return false;
        }

    }
    public boolean categoryExists(int id)
    {
        String query = "SELECT * FROM CATEGORY WHERE ID = "+id+";";
        try
        {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    /*
        If category not found returns NULL
     */
    public Category getCategory(int id)
    {
        String query = "SELECT * FROM CATEGORY WHERE ID = "+id+";";
        Category category = null;
        try
        {
            Statement state = connection.createStatement();
            ResultSet result = state.executeQuery(query);

            int catID = result.getInt("ID");
            String catName = result.getString("NAME");
            category = new Category(catID, catName);
            category.setIdFather(result.getString("ID_FATHER") == null
            ? -1 : result.getInt("ID_FATHER"));

        } catch (SQLException e) {
            return category;
        }
        return category;
    }
    public boolean updateCategoryId(int id,int newID)
    {
        if(getCategory(id) == null) return false;
        String query = "UPDATE CATEGORY SET ID = ? WHERE ID = ?";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, newID);
            _ps.setInt(2, id);
            int result = _ps.executeUpdate();
            return result > 0;

        } catch (SQLException e)
        {
            return false;
        }
    }
    public boolean updateCategoryName(int id,String name)
    {
        String query = "UPDATE CATEGORY SET NAME = ? WHERE ID = ?";
        try {
            PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setString(1, name);
            _ps.setInt(2, id);
            return _ps.executeUpdate() > 0;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCategoryIdFather(int id,int newFatherId)
    {
        String query = "UPDATE CATEGORY SET ID_FATHER = ? WHERE ID = ?";
        try {
             PreparedStatement _ps = connection.prepareStatement(query);
            _ps.setInt(1, newFatherId);
            _ps.setInt(2, id);
            return _ps.executeUpdate()>0;


        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}