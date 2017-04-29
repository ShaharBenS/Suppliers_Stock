package SharedClasses;

/**
 * Created by rotem on 06/04/2017.
 */

public class Order {
    private int orderID;
    private int supplierID;
    private Date date;
    private String ContactID;
    private OrderItem[] orderItems;

    public Order(int OrderID, int supplierID, Date date,  String ContactID, OrderItem[] orderItems){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.ContactID= ContactID;
        this.orderItems = orderItems;
    }
    public Order(int OrderID, int supplierID, Date date,String ContactID){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.ContactID= ContactID;
    }
    
    public Order(Order ord, OrderItem[] orderItems){
    	this.orderID = ord.getOrderID();
        this.supplierID = ord.getSupplier();
        this.date = ord.getDate();
        this.ContactID= ord.getContactID();
        this.orderItems = orderItems;
    }

    public int getOrderID(){ return orderID;}

    public void setOrderID(int orderID){this.orderID=orderID;}

    public int getSupplier(){return supplierID;}

    public void setSupplierID(int supID){this.supplierID=supID;}

    public void setSupplierName(String supName){
    }

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

    public String getContactID(){ return ContactID;}

    public void setContactID(String contactID){this.ContactID=contactID;}

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }
}
