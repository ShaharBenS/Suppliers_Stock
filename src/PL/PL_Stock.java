package PL;

import BL.CategoryManagement;
import BL.PriceManagement;
import BL.ProductManagement;
import java.util.Scanner;


public class PL_Stock
{
    private Scanner scanner = new Scanner(System.in);
    private ProductManagement ProductM;
    private PriceManagement PriceM;
    private CategoryManagement CategoryM;

    //TODO fix this shit - exit to back
    private final String[] MENU = {"Choose an option:" ,
            "1) Add new Product" ,
            "2) Add new Category" ,
            "** CATEGORY SECTION **" ,
            "3) Update category ID" ,
            "4) Update category Name" ,
            "5) Update category Father ID" ,
            "** PRODUCT SECTION **" ,
            "6) Update product ID" ,
            "7) Update product Location" ,
            "8) Update product Manufacture" ,
            "9) Update product Amount in Warehouse" ,
            "10) Update product Amount in Store",
            "11) Update product Minimal Amount" ,
            "12) Update product Amount of Defects" ,
            "13) Update product Category Code" ,
            "14) Update product Buy Price" ,
            "15) Update product Sell Price" ,
            "** OTHER USEFUL OPERATIONS **",
            "16) Add new Discount",
            "17) Stock report by product ID",
            "18) Stock report by category/ies",
            "19) Defect products report",
            "20) Show all products",
            "21) Exit"};

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
                    System.out.print("Enter the Products properties (separated by 1 space!) in the following structure:\n" +
                            "[ID] [LOCATION] [MANUFACTURE] [CURRENT AMOUNT] [MINIMAL AMOUNT] [CATEGORY CODE] [BUY PRICE] [SELL PRICE]\n");
                    String productLine = scanner.nextLine();
                    if(ProductM.addProduct(productLine))
                        System.out.print(" >> Product added successfully\n");
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
                    printUpdate(ProductM.updateProductId(prop));
                    break;
                case 7:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW LOCATION]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductLocation(prop));
                    break;
                case 8:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MANUFACTURE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductManufacture(prop));
                    break;
                case 9:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT IN WAREHOUSE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductAmountInWarehouse(prop));
                    break;
                case 10:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT IN STORE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductAmountInStore(prop));
                    break;
                case 11:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MINIMAL AMOUNT]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductMinimalAmount(prop));
                    break;
                case 12:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT OF DEFECTS]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductDefectAmount(prop));
                    break;
                case 13:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW CATEGORY CODE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateProductCategoryCode(prop));
                    break;
                case 14:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW BUY PRICE]\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.updateBuyPrice(prop));
                    break;
                case 15:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW SELL PRICE]\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.updateSellPrice(prop));
                    break;
                case 16:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID-of product or category] [DISCOUNT(in %)] [START DATE] [END DATE] **DATE FORM: DD.MM.YYYY\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.addDiscount(prop));
                    break;
                case 17:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID]\n");
                    prop = scanner.nextLine();
                    System.out.print(ProductM.productReport(prop));
                    break;
                case 18:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID CATEGORY 1] [ID CATEGORY 2] ...... [ID CATEGORY n]\n");
                    prop = scanner.nextLine();
                    String[] productsbyCat = CategoryM.productReportbyCategory(prop);
                    for(int i=0; i<productsbyCat.length; i++)
                        System.out.print(productsbyCat[i]);
                    break;
                case 19:
                    String[] prodDef = ProductM.getAllDefectProducts();
                    for(int i=0; i<prodDef.length; i++)
                        System.out.print(prodDef[i]);
                    break;
                case 20:
                    String[] prod = ProductM.getAllProducts();
                    for(int i=0; i<prod.length; i++)
                        System.out.print(prod[i]);
                    break;
                case 21:
                    return; // EXITING!
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
