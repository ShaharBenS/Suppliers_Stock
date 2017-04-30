package ProgramLauncher;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import BL.CategoryManagement;
import BL.PriceManagement;
import BL.ProductManagement;
import BL.SupplierBL;
import DAL.*;
import PL.Menu;
import PL.PL_Orders;
import PL.PL_Stock;
import PL.PL_Supplier;
import SharedClasses.*;

import javax.swing.plaf.nimbus.State;

/**
 * Created by Shahar on 06/04/17.
 */
public class ProgramLauncher
{
    public static void main(String [] args)
    {
        Connection conn = getConnectionAndInitDatabase("Database.db");

        // DAL INIT
        Items ITEMS = new Items(conn);
        Quantities QUANTITIES = new Quantities(conn);
        Categories CATEGORIES = new Categories(conn);
        Prices PRICES = new Prices(conn, CATEGORIES);

        Contacts CONTACTS = new Contacts(conn);
        Discounts DISCOUNTS = new Discounts(conn);
        Orders ORDERS = new Orders(conn);
        OrdersItems ORDERS_ITEMS = new OrdersItems(conn);
        SupplierItems SUPPLIER_ITEMS = new SupplierItems(conn);
        Suppliers SUPPLIERS = new Suppliers(conn);

        // BL INIT
        SupplierBL SBL = new SupplierBL(CONTACTS, DISCOUNTS, ITEMS, SUPPLIER_ITEMS, SUPPLIERS, ORDERS, ORDERS_ITEMS,QUANTITIES);
        ProductManagement PRODUCT_MANAGEMENT = new ProductManagement(ITEMS, PRICES, QUANTITIES, SBL);
        CategoryManagement CATEGORY_MANAGEMENT = new CategoryManagement(CATEGORIES, ITEMS, PRICES, QUANTITIES);
        PriceManagement PRICE_MANAGEMENT = new PriceManagement(PRICES);

        // PL INIT
        PL_Stock PL_STOCK = new PL_Stock(PRODUCT_MANAGEMENT, PRICE_MANAGEMENT, CATEGORY_MANAGEMENT, SBL);
        PL_Supplier pl_sup= new PL_Supplier (SBL);
        PL_Orders pl_ord= new PL_Orders(SBL);
        Menu MENU = new Menu(PL_STOCK, pl_sup, pl_ord);

        /*
            Database init
         */
        CATEGORIES.addCategory(new Category(103,"Drinks"));
        CATEGORIES.addCategory(new Category(102,"KARTON",103));
        CATEGORIES.addCategory(new Category(100,"Milk",102));
        CATEGORIES.addCategory(new Category(101,"Meat"));
        CATEGORIES.addCategory(new Category(104,"Bread"));
        CATEGORIES.addCategory(new Category(105,"35%", 101));

        SUPPLIERS.addSupplier(new Supplier(100000,"TNUVA",111, 1, 15,"LEOMI",
                "BY HAND","10:50","netivot"));
        SUPPLIERS.addSupplier(new Supplier(200000,"TARA",222, 2, 16,"HAPOALIM",
                "INTERNET ONLY","13:00","shfaram"));

        CONTACTS.addContact(new Contact("20202020",100000, "NEUMI SHEMER",
                "0500500550","a@a.a" ));

        CONTACTS.addContact(new Contact("30303030",200000, "NER NESHAMA",
                "0500500550","b@b.b" ));


        ITEMS.addItem(new Item(111111, "KORNFLEKS", 102, "SHKEL-INC"));


        SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 111111, 100000, 12.5));

        ORDERS.addOrder(new Order(123456, 100000, new Date(new java.util.Date()),"20202020"));

        ORDERS_ITEMS.addOrderItem(new OrderItem(123456, 100000, 111111, 30, 12.5));

        QUANTITIES.addItemQuantity(new Quantity(111111, "SHELF 2-A",0,
                30,10, 0, 30));

        PRICES.addItemPrice(new Price(111111, 20.5, 0, null, null));

        ORDERS.setArrivalDate(123456,new Date(new java.util.Date()));


        // start
        MENU.start();

        try
        {
            conn.commit();
            conn.close();
         //   Thread.sleep(1000);

        } catch (Exception e)
        {
        }
        File f = new File("Database.db"); //TODO delete this
        f.delete();
    }

    private static Connection getConnectionAndInitDatabase(String dataBaseName) {
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
                Suppliers Table : ID, Name, BankNum, BranchBum, AccountNum, Payment, DeliveryMethod, SupplyTime, Address.
             */
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Suppliers " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " Name          TEXT    NOT NULL, " +
                    " BankNum          INT    NOT NULL, " +
                    " BranchNum        INT    NOT NULL, " +
                    " AccountNum	   INT    NOT NULL, " +
                    " Payment         TEXT	NOT NULL," +
                    " DeliveryMethod TEXT NOT NULL," +
                    " SupplyTime TEXT," +
                    " Address TEXT NOT NULL);";
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
                    " PhoneNumber TEXT NOT NULL, " +
                    " Email	TEXT," +
                    "PRIMARY KEY(SupplierID, ID),"+
                    "FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.execute(sql);
            stmt.close();

            /*
                Category : ID, Name, ID_Father. When ID_father is -1, that category has no father.
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS CATEGORY " +
                    "(ID INT PRIMARY KEY     NOT NULL ," +
                    " NAME           CHAR(50) NOT NULL, " +
                    " ID_FATHER  INT DEFAULT NULL REFERENCES CATEGORY(ID) " +
                    " ON UPDATE CASCADE ON DELETE SET NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Items : ID, Name, CategoryNumber, Manufacture.
             */
            stmt = c.createStatement();
            sql = "  CREATE TABLE IF NOT EXISTS Items " +
                    " (ID   INT PRIMARY KEY  NOT NULL," +
                    " NAME   TEXT NOT NULL, " +
                    " CategoryNumber       INT    REFERENCES CATEGORY(ID) ON DELETE SET NULL ON UPDATE CASCADE, " +
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
                Orders : OrderID, SupplierID, Date, ContactNumber, SupplierID(FR), ContactNumber(FR).
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Orders " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    " SupplierID INT   NOT NULL," +
                    " Date  DATE  NOT NULL, " +
                    " ContactID TEXT  NOT NULL, " +
                    "ArrivalDate Date DEFAULT NULL," +
                    " FOREIGN KEY(SupplierID , ContactID) REFERENCES Contacts(SupplierID, ID) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            /*
                OrdersItems : OrderID, SupplierID ,ItemID, Quantity, ,
                              , FinalCost, OrderID(FR), catalogNumber(FR),
                              ItemName(FR), Cost(FR), Discount(FR).
             */
            stmt = c.createStatement();
            sql =    "CREATE TABLE IF NOT EXISTS OrdersItems " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    " ItemID INT NOT NULL,"+
                    " Quantity INT  NOT NULL," +
                    " SupplierID INT NOT NULL, " +
                    " FinalCost REAL  NOT NULL, " +
                    " FOREIGN KEY(OrderID) REFERENCES Orders(OrderID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            /*
                Quantities : ItemID, Location, Defects, Warehouse, Minimum, Store, Order. (Current = Store+Warehouse+Defects)
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS QUANTITIES " +
                    "(ItemID INT REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE ," +
                    "LOCATION TEXT NOT NULL," +
                    "MINIMUM INT NOT NULL," +
                    "ORDER_AMOUNT INT DEFAULT 0," +  //TODO set minimum*3 and add option to change it.
                    "WAREHOUSE INT NOT NULL," +
                    "STORE INT NOT NULL," +
                    "DEFECTS INT NOT NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Prices : ItemID, SellPrice,, Percentage, DateStart, DateEnd.
            */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS PRICES" +
                    "(ItemID INT REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE ," +
                    "SellPrice REAL NOT NULL," +
                    "Percentage INT,"+
                    "DateStart DATE," +
                    "DateEnd DATE);";
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


    /*private static void initializeDatabase(Connection conn)
    {
        String [] Queries = {"INSERT INTO CATEGORY (ID,NAME,ID-FATHER",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO ",
                             "INSERT INTO "};
        try
        {

            for (String Query : Queries) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(Query);
            }
            conn.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }*/
}