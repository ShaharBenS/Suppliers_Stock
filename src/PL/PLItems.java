package PL;

import BL.SupplierBL;
import SharedClasses.Item;

import java.util.Scanner;

public class PLItems {

    SupplierBL bl;
    Scanner sc;

    public PLItems(SupplierBL bl, Scanner sc) {
        this.bl = bl;
        this.sc = sc;
    }

///////////////// ITEMS ///////////////////////////////////////////////////

    private void printOptions5() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new item");
        System.out.println("2- Update item's name");
        System.out.println("3- Get item");
        System.out.println("4- Delete item");
        System.out.println("5- Back");
    }

    public void case5() {
        int option;
        printOptions5();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //add
                    case51();
                    break;
                case 2:
                    //update name
                    case52();
                    break;
                case 3:
                    //get
                    case53();
                    break;
                case 4:
                    //delete
                    case54();
                    break;
                case 5:
                    //back
                    return;


                default:
                    System.out.println("ERROR! wrong operation");
                    case5();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    private int getItemID() {
        int itemID = 0;
        System.out.println("Please enter item ID");
        try {
            itemID = Integer.parseInt(sc.nextLine());
            if (itemID <= 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkExistItemID(itemID);
        if (Exist)
            return itemID;
        else {
            System.out.println("ERROR! invalid item ID");
            return 0;
        }
    }

    //add item
    public void case51() {
        int itemID, category;
        String name,manufacture;
        System.out.println("Please enter item's ID");
        itemID = Integer.parseInt(sc.nextLine());
        if (!bl.checkExistItemID(itemID)) {
            System.out.println("Please enter item's name");
            name = sc.nextLine();
            System.out.println("Please enter item's category");
            category = Integer.parseInt(sc.nextLine());
            System.out.println("Please enter item's manufacture");
            manufacture = sc.nextLine();
            if (bl.addItem(itemID, name,category,manufacture))
                System.out.println("Item has been added successfully");
            else {
                System.out.println("something went wrong");
                case51();
            }
        } else {
            System.out.println("something went wrong");
            case51();
        }

    }

    private void printOptions52() {
        System.out.println("Please choose an option:");
        System.out.println("1- Update item's name");
        System.out.println("2- Update item's category number");
        System.out.println("3- Update item's manufacture");
        System.out.println("4- Back");
    }

    //update item
    public void case52() {
        int option;
        printOptions52();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                //update email
                case 1:
                    case521();
                    break;
                //update phone
                case 2:
                    case522();
                    break;
                //update name
                case 3:
                    case523();
                    break;
                //back
                case 4:
                    case5();
                    break;

                default:
                    System.out.println("ERROR! wrong operation");
                    case52();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
        int itemID = getItemID();
        String name;
        if (itemID != 0) {
            System.out.println("Please enter new item's name");
            name = sc.nextLine();
            if (bl.setItem(itemID, name, 2))
                System.out.println("update successfully");
            else {
                System.out.println("something went wrong");
                case52();
            }
        } else
            case52();
    }




    //update email contact
    public void case521() {
        int itemID = getItemID();
        String name;
        if (itemID != 0) {
            System.out.println("Please enter new item's name");
            name = sc.nextLine();
            if (bl.setItem(itemID, name, 2))
                System.out.println("update successfully");
            else {
                System.out.println("something went wrong");
                case52();
            }
        } else
            case52();
    }

    //update category number
    public void case522() {
        int itemID = getItemID();
        int category=0;
        if (itemID != 0) {
            System.out.println("Please enter new category number");
            try{
                 category = Integer.parseInt(sc.nextLine());}
            catch(Exception e){
                System.out.println("something went wrong");
                case52();
            }
            if (bl.setItem(itemID, category, 3))
                System.out.println("update successfully");
            else {
                System.out.println("something went wrong");
                case52();
            }
        } else
            case52();
    }

    //update manufacture
    public void case523() {
        int itemID = getItemID();
        String manufacture;
        if (itemID != 0) {
            System.out.println("Please enter new item's manufacture");
            manufacture = sc.nextLine();
            if (bl.setItem(itemID, manufacture, 4))
                System.out.println("update successfully");
            else {
                System.out.println("something went wrong");
                case52();
            }
        } else
            case52();
    }


    //get item details.
    public void case53() {
        int itemID = getItemID();
        if (itemID != 0) {
            Item item =bl.getItem(itemID);
            String ans ="";
            ans+="ID: " + item.getItemID()+ "\n";
            ans+= "Name: " + item.getName() + "\n";
            ans += "Category number: " + item.getCategoryNumber() + "\n";
            ans += "Manufacture: " + item.getManufacture() + "\n";
            System.out.println(ans);
        } else
            case53();
    }
    //delete item.
    public void case54() {
        int itemID = getItemID();
        if (itemID != 0) {
            if (bl.removeItem(itemID))
                System.out.println("deleted successfully");
            else {
                System.out.println("something went wrong");
                case54();
            }
        } else
            case54();
    }

}
