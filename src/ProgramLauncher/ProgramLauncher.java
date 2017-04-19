package ProgramLauncher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import PL.Menu;

/**
 * Created by Shahar on 06/04/17.
 */
public class ProgramLauncher
{
    public static void main(String [] args) {
        //TODO create all layers.

        Connection conn = getConnectionAndInitDatabase("Database.db");
        new Menu().start();
    }

    public static Connection getConnectionAndInitDatabase(String dataBaseName) {
        Connection c = null;
        Statement stmt = null;
        try {
            /*Opening Connection*/
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dataBaseName);

            c.createStatement().execute("PRAGMA FOREIGN_KEYS = ON;");
            c.setAutoCommit(false);
            /*Creating Tables if they are NOT existed */

            /*
                Suppliers Table : ID, Name, BankNum, BranchBum, AccountNum, Payment, DeliveryMethod, SupplyTime.
             */
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Suppliers " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " Name          TEXT    NOT NULL, " +
                    " BankNum          INT    NOT NULL, " +
                    " BranchNum           INT    NOT NULL, " +
                    " AccountNum				INT     NOT NULL, " +
                    " Payment         TEXT	NOT NULL," +
                    " DeliveryMethod TEXT NOT NULL," +
                    " SupplyTime TEXT);";               // TODO add address field!
            stmt.execute(sql);
            stmt.close();

            /*
                Contacts Table : ID, Full name, Phone Number, Email.
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Contacts " +
                    "(ID   TEXT NOT NULL," +
                    "SupplierID INT  NOT NULL," +
                    " FullName   TEXT  NOT NULL, " +
                    " PhoneNumber           TEXT    NOT NULL, " +
                    " Email	TEXT," +
                    "PRIMARY KEY(SupplierID, ID),"+
                    "FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            /*
                Items : ID, Name, CategoryNumber, Manufacture.
             */
            stmt = c.createStatement();
            sql = "  CREATE TABLE IF NOT EXISTS Items " +
                  " (ID   INT PRIMARY KEY  NOT NULL," +
                    " NAME   TEXT NOT NULL, " +
                    " CategoryNumber       TEXT    NOT NULL, " +
                    " Manufacture          TEXT    NOT NULL);";
            stmt.execute(sql);
            stmt.close();


            /*
                SuppliersItems : SupplierID, ItemID, CatalogNumber, Cost, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS SupplierItems " +
                    "(SupplierID INT   NOT NULL," +
                    "ItemID INT  PRIMARY KEY   NOT NULL," +
                    "CatalogNumber INT NOT NULL,"+
                    " Cost REAL  NOT NULL, " +
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    "FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            /*
                Discounts : SupplierID, ItemID, Quantity, DiscountPercentage, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Discounts " +
                    "(SupplierID INT  NOT NULL," +
                    "ItemID INT   NOT NULL," +
                    "Quantity INT NOT NULL,"+
                    " DiscountPercentage INT  NOT NULL, " +
                    "PRIMARY KEY (SupplierID, ItemID, Quantity),"+
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    "FOREIGN KEY(ItemID) REFERENCES Items(ID) ON DELETE CASCADE ON UPDATE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            /*
                Orders : OrderID, SupplierID, SupplierName, Date, ContactNumber, SupplierID(FR), ContactNumber(FR).
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Orders " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    "SupplierID INT   NOT NULL," +
                    "SupplierName TEXT NOT NULL,"+
                    " Date TEXT  NOT NULL, " +
                    " ContactNumber TEXT  NOT NULL, " +
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    "FOREIGN KEY(ContactNumber) REFERENCES SupplierItems(PhoneNumber) ON DELETE CASCADE ON UPDATE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            /*
                OrdersItems : OrderID, catalogNumber, ItemName, Quantity, Cost,
                              Discount, FinalCost, OrderID(FR), catalogNumber(FR),
                              ItemName(FR), Cost(FR), Discount(FR).
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS OrdersItems " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    "catalogNumber INT   NOT NULL," +
                    "ItemName TEXT NOT NULL,"+
                    " Quantity INT  NOT NULL, " +
                    " Cost REAL  NOT NULL, " +
                    " Discount INT  NOT NULL, " +
                    " FinalCost REAL  NOT NULL, " +
                    " FOREIGN KEY(OrderID) REFERENCES Orders(OrderID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(catalogNumber) REFERENCES SupplierItems(catalogNumber) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemName) REFERENCES Items(Name) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(Cost) REFERENCES SupplierItems(Cost) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    "FOREIGN KEY(Discount) REFERENCES Discounts(DiscountPercentage) ON DELETE CASCADE ON UPDATE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();

            /*
                Category : ID, Name, ID_Father. When ID_father is -1, that category has no father.
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS CATEGORY " +
                    "(ID INT PRIMARY KEY     NOT NULL ," +
                    " NAME           CHAR(50) NOT NULL, " +
                    " ID_FATHER  INT NOT NULL DEFAULT -1 REFERENCES CATEGORY(ID) " +
                    " ON UPDATE CASCADE ON DELETE SET NULL);";
            stmt.execute(sql);
            stmt.close();

            
            c.commit();
            stmt.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
    }
}
