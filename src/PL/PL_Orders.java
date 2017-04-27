package PL;
import BL.SupplierBL;
import SharedClasses.Date;
import SharedClasses.Order;
import SharedClasses.Supplier;

import java.util.Scanner;
/**
 * Created by Shahar on 19/04/17.
 */
public class PL_Orders
{
    public final int SLEEP_TIME = 1000; // In seconds
    SupplierBL bl;
    private Scanner sc = new Scanner(System.in);

    public PL_Orders(SupplierBL bl) {
        this.bl = bl;
    }

    private void printOptionsOrder() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new Order/item to order");
        System.out.println("2- Update order details");
        System.out.println("3- Get exist Order");
        System.out.println("4- Delete Details/Order");
        System.out.println("5- Get Supplier's Orders");
        System.out.println("6- Back");
    }

    private void printOptionsOrderUpdate() {
        System.out.println("Please choose an option:");
        System.out.println("1- Update contact's number");
        System.out.println("2- Update item quantity");
        System.out.println("3- Back");
    }

    private void printOptionsOrderDelete() {
        System.out.println("Please choose an option:");
        System.out.println("1- Delete exist order");
        System.out.println("2- Delete item from order");
        System.out.println("3- Back");
    }

    private void printOptionsOrderAdd() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new order");
        System.out.println("2- Add new item to order");
        System.out.println("3- Back");
    }

    private int getSupID() {
        int supID = 0;
        System.out.println("Please enter supplier ID");
        try {
            supID = Integer.parseInt(sc.nextLine());
            if (supID <= 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkSupExist(supID);
        if (Exist)
            return supID;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getOrderID() {
        int order = 0;
        System.out.println("Please enter Order Number");
        try {
            order = Integer.parseInt(sc.nextLine());
            if (order < 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkOrderExist(order);
        if (Exist)
            return order;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getItem(){
        int item = 0;
        System.out.println("Please enter item ID");
        try {
            item = Integer.parseInt(sc.nextLine());
            if (item < 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkExistItemID(item);
        if (Exist)
            return item;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getQuantity() {
        int quantity = 0;
        System.out.println("Please enter Quantity");
        try {
            quantity = Integer.parseInt(sc.nextLine());
            if (quantity < 0) {
                System.out.println("ERROR! invalid Quantity");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid Quantity");
            return -1;
        }
        return quantity;
    }
    private String getPhoneNum(int supID) {
        String conNum = "";
        System.out.println("the contacts who works with supplier "+ supID + "are:");
        System.out.println(bl.getSupllierContact(supID));
        System.out.println("Please choose one contact number");

        try {
            if (conNum.length()<10) {
                System.out.println("ERROR! invalid Quantity");
                return "";
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid Quantity");
            return "";
        }
        return conNum;
    }

    //OrderOptions
    public void orderCase() {
        int option;
        printOptionsOrder();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //add
                    case1();
                    break;
                case 2:
                    //update
                    case2();
                    break;
                case 3:
                    //get
                    case3();
                    break;
                case 4:
                    //delete
                    case4();
                    break;
                case 5:
                    //getSupplierOrders
                    case31();
                    return;
                case 6:
                    //back
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //add
    public void case1() {
        int option;
        printOptionsOrderAdd();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //new order
                    case1();
                    break;
                case 2:
                    //new item to order
                    case2();
                    break;
                case 3:
                    orderCase();
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //addNewOrder
    public void case11() {
        int supID = getSupID();
        //check if the supplier exist
        if (supID != 0) {
            int ans = bl.addOrder(supID, new Date());
            if(ans == -1 ) {
                System.out.println("ERROR! something went wrong");
                case1();
            }
            else {
                System.out.println("Order has been added successfully");
                System.out.println("Order Number: " + ans);
                System.out.println("to add items to this order, please choose option number 1");
                case1();
            }
        }
    }

    //addItemToOrder
    public void case12() {
        int order = getOrderID();
        int item, quantity, ans;
        //check if the order exist
        if (order != 0) {
            item = getItem();
            if (item != 0) {
                quantity = getQuantity();
                if (quantity != -1) {
                    bl.addOrderItem(order, bl.getOrder(order).getSupplier(), item, quantity);
                    System.out.println("Item has been added successfully");
                } else {
                    System.out.println("ERROR! something went wrong");
                    case1();
                }
            } else {
                System.out.println("ERROR! something went wrong");
                case1();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case1();
        }
    }

    //updateOrder
    public void case2() {
        int option;
        printOptionsOrderUpdate();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //update contact num
                    case21();
                    break;
                case 2:
                    //update item quantity
                    case22();
                    break;
                case 3:
                    //back
                    orderCase();
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //updateContactNumber
    public void case21() {
        int order = getOrderID();
        String conNum;
        //check if the order exist
        if (order != 0) {
            conNum = getPhoneNum(bl.getOrder(order).getSupplier());
            if (conNum != "") {
                bl.setOrder(order,0,conNum, 1);
                System.out.println("Item has been added successfully");
            } else {
                System.out.println("ERROR! something went wrong");
                case2();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case2();
        }

    }
    //updateItemQuantity
    public void case22() {
        int order = getOrderID();
        int itemNum, quantity;
        //check if the order exist
        if (order != 0) {
            itemNum = getItem();
            if (itemNum != 0 && bl.checkItemExistInOrder(order, itemNum)) {
                quantity = getQuantity();
                if (quantity != -1) {
                    bl.setOrder(order, itemNum, quantity, 2);
                    System.out.println("Item Quantity has been updated successfully");
                } else {
                    System.out.println("ERROR! something went wrong");
                    case2();
                }
            } else {
                System.out.println("ERROR! something went wrong");
                case2();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case2();
        }
    }


    //remove
    public void case4(){
        int option;
        printOptionsOrderDelete();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //delete exit order
                    case41();
                    break;
                case 2:
                    //delete item from order
                    case42();
                    break;
                case 3:
                    //back
                    orderCase();
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }

    }
    //removeOrder
    public void case41() {
        int order = getOrderID();
        //check if the order exist
        if (order != 0) {
            bl.removeOrder(order);
            System.out.println("Order has been removed successfully");
        } else {
            System.out.println("ERROR! something went wrong");
            case4();
        }
    }

    //removeOrderItem
    public void case42() {
        int order = getOrderID();
        int itemNum;
        //check if the order exist
        if (order != 0) {
            itemNum = getItem();
            if (itemNum != 0 && bl.checkItemExistInOrder(order, itemNum)) {
                bl.removeOrderItem(order, itemNum);
                System.out.println("Item from order has been removed successfully");
            } else {
                System.out.println("ERROR! something went wrong");
                case4();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case4();
        }
    }

    //getOrder
    public void case3() {
        int orderNum = getOrderID();
        //check if the order exist
        if (orderNum != 0) {
            Order order = bl.getOrder(orderNum);
            Supplier sup = bl.getSupplier(order.getSupplier());
            String ans ="";
            ans+="Order Number: " + order.getOrderID()+ "\n";
            ans+= "Supplier Name: " + order.getSupplierName() + "\n";
            ans+= "Supplier ID: " + order.getSupplier() + "\n";
            ans += "Address: " + sup.getAddress() + "\n";
            ans += "Order Date: " + order.getDate() + "\n";
            ans += "Contact Number: " + order.getContactNum() + "\n";
            System.out.println(ans);
        } else {
            System.out.println("ERROR! something went wrong");
            orderCase();
        }
    }

    //getSupplierOrder
    public void case31() {
        int supID = getSupID();
        //check if the supplier exist
        if (supID != 0) {
            System.out.println("the order's id of the supplier "+ supID+ "are:");
            Order [] order  = bl.getOrderOfSup(supID);
            for(int i=0; i<order.length; i++){
                System.out.println(order[i].getOrderID());
            }
        }
        else {
            System.out.println("ERROR! something went wrong");
            orderCase();
        }
    }

}
