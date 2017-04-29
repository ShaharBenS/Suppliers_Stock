package SharedClasses;

/**
 * Created by rotem on 06/04/2017.
 */

public class Order {
    private int orderID;
    private int supplierID;
    private String supName;
    private Date date;
    private String ContactID;
    private String ContactNum;
    private OrderItem[] orderItems;

    public Order(int OrderID, int supplierID, String supName, Date date,  String ContactID,String ContactNum, OrderItem[] orderItems){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.supName= supName;
        this.date = new Date(date);
        this.ContactID= ContactID;
        this.ContactNum = ContactNum;
        this.orderItems = orderItems;
    }
    public Order(int OrderID, int supplierID, String supName, Date date,String ContactID, String ContactNum){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.supName= supName;
        this.date = new Date(date);
        this.ContactID= ContactID;
        this.ContactNum = ContactNum;
    }
    
    public Order(Order ord, OrderItem[] orderItems){
    	this.orderID = ord.getOrderID();
        this.supplierID = ord.getSupplier();
        this.supName= ord.getSupplierName();
        this.date = ord.getDate();
        this.ContactID= ord.getContactID();
        this.ContactNum = ord.getContactNum();
        this.orderItems = orderItems;
    }

    public int getOrderID(){ return orderID;}

    public void setOrderID(int orderID){this.orderID=orderID;}

    public int getSupplier(){return supplierID;}

    public void setSupplierID(int supID){this.supplierID=supID;}

    public String getSupplierName(){return supName;}

    public void setSupplierName(String supName){this.supName=supName;}

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

    public String getContactID(){ return ContactID;}

    public void setContactID(String contactID){this.ContactID=contactID;}

    public String getContactNum(){return ContactNum;};

    public void setContactNum(String contactNum){this.ContactNum = contactNum;}

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }
}
