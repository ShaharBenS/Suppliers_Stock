package BL;

import DAL.*;
import SharedClasses.*;


public class SupplierBL {
    Contacts contacts;
    Discounts dis;
    Items item;
    SupplierItems si;
    Suppliers sup;
    Orders order;
    OrdersItems OI;
    public static  int OrderID=0;

    public SupplierBL(Contacts contacts, Discounts dis, Items item, SupplierItems si, Suppliers sup, Orders order, OrdersItems ordersItems) {
        this.contacts = contacts;
        this.dis = dis;
        this.item = item;
        this.si = si;
        this.sup = sup;
        this.order = order;
        this.OI = ordersItems;
    }


    public boolean addContact(int SupplierId, String Id, String name, String phoneNum, String Email) {
        Contact con = new Contact(Id, SupplierId, name, phoneNum, Email);
        return contacts.addContact(con);
    }

    public boolean setContact(String id, Object change, int code) {// 1- name, 2- phoneNum, 3-Email
        switch (code) {
            case 1:
                String name = (String) change;
                return contacts.setName(id, name);
            case 2:
                String phoneNum = (String) change;
                return contacts.setPhoneNum(id, phoneNum);
            case 3:
                String Email = (String) change;
                return contacts.setEmail(id, Email);
            default:
                return false;
        }
    }

    public Contact getContact(String id) {
        return contacts.getContact(id);
    }

    public String getContactName(String id) {
        return contacts.getContactName(id);
    }

    public String getContactPhone(String id) {
        return contacts.getContactPhone(id);
    }

    public String getContactEmail(String id) {
        return contacts.getContactEmail(id);
    }

    public String getSupllierContact(int id) {
        return contacts.getSupllierContact(id);
    }

    public boolean removeContact(String id, int supId) {
        return contacts.removeContact(id, supId);
    }

    public boolean addSupplier(int ID, String name, int BankNum, int BranchNum, int AccountNum, String payment, String DeliveryMethod, String SupplyTime) {
        Supplier supplier = new Supplier(ID, name, BankNum, BranchNum, AccountNum, payment, DeliveryMethod, SupplyTime);
        return sup.addSupplier(supplier);
    }

    public boolean setSupplier(int id, Object change, int code) {// 1- ID,2-name, 3- BankNum, 4- BranchNum, 5-AccountNum, 6-payment, 7-DeliveryMethod,8-SupplyTime
        switch (code) {
            case 1:
                int newID = ((Integer) change).intValue();
                return sup.setID(id, newID);
            case 2:
                String name = (String) change;
                return sup.setName(id, name);
            case 3:
                int BankNum = ((Integer) change).intValue();
                return sup.setBankNum(id, BankNum);
            case 4:
                int BranchNum = ((Integer) change).intValue();
                return sup.setBranchNum(id, BranchNum);
            case 5:
                int AccountNum = ((Integer) change).intValue();
                return sup.setAccountNum(id, AccountNum);
            case 6:
                String payment = (String) change;
                return sup.setPayment(id, payment);
            case 7:
                String DeliveryMethod = (String) change;
                return sup.setDelivery(id, DeliveryMethod);
            case 8:
                String SupplyTime = (String) change;
                return sup.setSupplyTime(id, SupplyTime);
            default:
                return false;
        }
    }


    public Supplier getSupplier(int id) {
        return sup.getSupplier(id);
    }

    public String getDeliveryMethod(int id) {
        return sup.getDeliveryMethod(id);
    }

    public boolean removeSupplier(int id) {
        return sup.removeSupplier(id);
    }

    public boolean addSupplierItem(int SupplierID, int ItemID, int CatalogNumber, double Cost) {
        SupplierItem supItem = new SupplierItem(SupplierID, ItemID, CatalogNumber, Cost);
        return si.addSupplierItem(supItem);
    }

    public boolean setSupplierItem(int supId, int itemId, Object change, int code) {// 1- CatalogNumber, 2-Cost
        switch (code) {
            case 1:
                int CatalogNumber = ((Integer) change).intValue();
                return si.setCatalogNumber(supId, itemId, CatalogNumber);
            case 2:
                double Cost = ((Double) change).doubleValue();
                return si.setCost(supId, itemId, Cost);
            default:
                return false;
        }
    }

    public SupplierItem getSupplierItem(int itemId, int supId) {
        return si.getSupplierItem(itemId, supId);
    }

    public int getCatalogNumber(int itemId, int supId){return si.getCatalogNumber(itemId, supId);}

    public double getCost(int itemId, int supId){return si.getCost(itemId, supId);}

    public String getSupplierItems(int id) {
        return si.getSupplierItems(id);
    }

    public boolean removeSupplierItem(int id, int supId) {
        return si.removeSupplierItem(id, supId);
    }

    public boolean addItem(int ID,  String Name, int Category,String manufacture) {
        Item itemToAdd = new Item(ID,  Name,Category, manufacture);
        return item.addItem(itemToAdd);
    }

    public boolean setItem(int id, Object change, int code) {// 1- ID, 2- Name, 3- category, 4- manufacture
        switch (code) {
            case 1:
                int newID = ((Integer) change).intValue();
                return item.setID(id, newID);
            case 2:
                String Name = (String) change;
                return item.setName(id, Name);
            case 3:
                int newCategory = ((Integer) change).intValue();
                return item.setCategory(id, newCategory);
            case 4:
                String manufacture = (String) change;
                return item.setmanufacture(id, manufacture);
            default:
                return false;
        }
    }

    public Item getItem(int itemId) {
        return item.getItem(itemId);
    }

    public boolean removeItem(int id) {
        return item.removeItem(id);
    }

    public boolean addDiscount(int SupplierID, int ItemID, int Quantity, int DiscountPercentage) {
        Discount discount = new Discount(SupplierID, ItemID, Quantity, DiscountPercentage);
        return dis.addDiscount(discount);
    }

    public boolean setDiscount(int supId, int itemId, Object change, int code) {// 1- Quantity, 2-DiscountPercentage
        switch (code) {
            case 1:
                int Quantity = ((Integer) change).intValue();
                return dis.setQuantity(supId, itemId, Quantity);
            case 2:
                int DiscountPercentage = ((Integer) change).intValue();
                return dis.setDiscountPercentage(supId, itemId, DiscountPercentage);
            default:
                return false;
        }
    }

    public Discount getDiscount(int supplirId, int itemId, int quantity) {
        return dis.getDiscount(supplirId, itemId, quantity);
    }

    public int getDiscountPer(int itemId, int supId, int quantity){return  dis.getDiscountPer(supId, itemId, quantity);}

    public String getDiscounts(int supId, int ItemId) {
        return dis.getDiscounts(supId, ItemId);
    }

    public boolean removeDiscount(int supplirId, int itemId, int quantity) {
        return dis.removeDiscount(supplirId, itemId, quantity);
    }


    public boolean checkSupExist(int id) {

        return sup.ifExist(id);
    }

    public boolean checkItemExist(int supID, int itemId) {
        return si.ifExist(supID, itemId);
    }

    public boolean checkExistConID(String id) {
        return contacts.ifExist(id);
    }

    public boolean checkExistConSup(int supID, String conID) {
        return contacts.ifExist(supID, conID);
    }

    public boolean checkExistItemID(int itemId) {
        return item.ifExist(itemId);
    }

    public boolean checkExistDis(int supID, int itemID, int Quantity) {
        return dis.ifExist(supID, itemID, Quantity);
    }

    public int addOrder(int supplierId, Date date){
        Order ord = new Order(OrderID++,supplierId, sup.getSupplierName(supplierId),date, contacts.getContactNum(supplierId));
        if(!order.addOrder(ord))
        {
            return -1;
        }
        return ord.getOrderID();
    }

    public boolean addOrderItem(int orderID,int supplierID,int itemID, int quantity){
        int disco = dis.getDiscountPer(supplierID, itemID, quantity);
        double cost =si.getCost(itemID,supplierID);
        double finalCost = (disco*cost)/100;
        OrderItem orderItem = new OrderItem(orderID,si.getCatalogNumber(itemID,supplierID), item.getItemName(itemID), quantity,cost, disco, finalCost);
        return OI.addOrderItem(orderItem);
    }
    //TODO implement this
    int getSupplierID(int itemID)
    {
        return -1;
    }


}
