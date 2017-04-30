package SharedClasses;

/**
 * Created by rotem on 06/04/2017.
 */

public class Order {
    private int orderID;
    private int supplierID;
    private Date date;
    private String ContactID;
    private int Frequency;
    private OrderItem[] orderItems;

    @Override
    public String toString() {
        String ans ="";
        ans+="***** ORDER *****\n";
        ans+="ORDER ID: "+orderID+"\n";
        ans+="SUPPLIER ID: "+supplierID+"\n";
        ans+="Date: "+date.toString()+"\n";
        for(int i=0; i<orderItems.length; i++)
        {
            ans+=orderItems[i].toString();
        }
        ans+="***** ORDER *****\n";
        return  ans;
    }

    public Order(int OrderID, int supplierID, Date date, String ContactID, OrderItem[] orderItems){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.ContactID= ContactID;
        this.orderItems = orderItems;
        Frequency = 0;
    }
    public Order(int OrderID, int supplierID, Date date,String ContactID){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.ContactID= ContactID;
        Frequency = 0;
    }
    public Order(int OrderID, int supplierID, Date date,String ContactID,int frequency){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.ContactID= ContactID;
        this.Frequency = frequency;
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

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }
}
