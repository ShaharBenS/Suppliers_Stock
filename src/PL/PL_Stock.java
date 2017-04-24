package PL;

import java.util.Scanner;

import BL.CategoryManagement;
import BL.PriceManagement;
import BL.ProductManagement;


public class PL_Stock
{
    private Scanner scanner = new Scanner(System.in);
    private ProductManagement ProductM;
    private PriceManagement PriceM;
    private CategoryManagement CategoryM;

    private final String[] MENU = {"Choose an option:" ,
            "1) Add new Item" ,
            "2) Add new Category" ,
            "** CATEGORY SECTION **" ,
            "3) Update category ID" ,
            "4) Update category Name" ,
            "5) Update category Father ID" ,
            "** ITEM SECTION **" ,
            "6) Update item ID" ,
            "7) Update item Location" ,
            "8) Update item Manufacture" ,
            "9) Update item Amount in Warehouse" ,
            "10) Update item Amount in Store",
            "11) Update item Minimal Amount" ,
            "12) Update item Amount of Defects" ,
            "13) Update item Category Code" ,
            "14) Update item Sell Price" ,
            "** OTHER USEFUL OPERATIONS **",
            "15) Add new Discount",
            "16) Stock report by item ID",
            "17) Stock report by category/ies",
            "18) Defect Items report",
            "19) Show all Items",
            "20) Back"};

    public PL_Stock(ProductManagement pm, PriceManagement price_m, CategoryManagement cm)
    {
        this.CategoryM = cm;
        this.PriceM = price_m;
        this.ProductM = pm;
    }

    /*
        This method starts reading input and
        stops when user wishes to stop.
     */
    public void start()
    {
        int operation;

        while(true)
        {
            for(int i=0; i<MENU.length; i++)
            {
                System.out.print(MENU[i]+"\n");
            }
            try { operation = Integer.parseInt(scanner.nextLine()); }
            catch(Exception r) { System.out.print("Invalid operation. Please try again\n\n"); continue; }

            String prop;
            switch(operation)
            {
                case 1:
                    System.out.print("Enter the Items properties (separated by 1 space!) in the following structure:\n" +
                            "[ID] [LOCATION] [MANUFACTURE] [MINIMAL AMOUNT] [CATEGORY CODE] [NAME] [SELL PRICE]\n");
                    String ItemLine = scanner.nextLine();
                    if(ProductM.addItem(ItemLine))
                        System.out.print(" >> item added successfully\n");
                    else System.out.print(" >> Invalid arguments. Try again\n");
                    break;
                case 2:
                    System.out.print("Enter the Category properties (separated by 1 space!) in the following structure:\n" +
                            "[ID] [NAME] [ID FATHER - optional]\n");
                    String categoryLine = scanner.nextLine();
                    if(CategoryM.addCategory(categoryLine))
                        System.out.print(" >> Category added successfully\n");
                    else System.out.print(" >> Invalid arguments. Try again\n");
                    break;
                case 3:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryId(prop));
                    break;
                case 4:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW NAME]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryName(prop));
                    break;
                case 5:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID FATHER]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryIdFather(prop));
                    break;
                case 6:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemId(prop));
                    break;
                case 7:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW LOCATION]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemLocation(prop));
                    break;
                case 8:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MANUFACTURE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemManufacture(prop));
                    break;
                case 9:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT IN WAREHOUSE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemAmountInWarehouse(prop));
                    break;
                case 10:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT IN STORE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemAmountInStore(prop));
                    break;
                case 11:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MINIMAL AMOUNT]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemMinimalAmount(prop));
                    break;
                case 12:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT OF DEFECTS]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemDefectAmount(prop));
                    break;
                case 13:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW CATEGORY CODE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemCategoryCode(prop));
                    break;
                case 14:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW SELL PRICE]\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.updateSellPrice(prop));
                    break;
                case 15:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID-of item or category] [DISCOUNT(in %)] [START DATE] [END DATE] **DATE FORM: DD.MM.YYYY\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.addDiscount(prop));
                    break;
                case 16:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID]\n");
                    prop = scanner.nextLine();
                    System.out.print(ProductM.ItemReport(prop));
                    break;
                case 17:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID CATEGORY 1] [ID CATEGORY 2] ...... [ID CATEGORY n]\n");
                    prop = scanner.nextLine();
                    String[] productsbyCat = CategoryM.productReportbyCategory(prop);
                    for(int i=0; i<productsbyCat.length; i++)
                        System.out.print(productsbyCat[i]);
                    break;
                case 18:
                    String[] prodDef = ProductM.getAllDefectProducts();
                    for(int i=0; i<prodDef.length; i++)
                        System.out.print(prodDef[i]);
                    break;
                case 19:
                    String[] prod = ProductM.getAllItems();
                    for(int i=0; i<prod.length; i++)
                        System.out.print(prod[i]);
                    break;
                case 20:
                    return; // GOING BACK!
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;

            }
            //Read Input:
        }
    }

    private void printUpdate(boolean arg)
    {
        if(arg) System.out.print(" >> Updated successfully\n");
        else System.out.print(" >> Invalid arguments. Try again\n");
    }
}
