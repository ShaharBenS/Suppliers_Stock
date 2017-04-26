package PL;

import java.util.Scanner;

/**
 * Created by Omri on 06-Apr-17.
 */
public class Menu {

    private PL_Stock pl_stock;
    private PL_Supplier pl_sup;
    private Scanner scanner;

    private final String[] MENU =
            {   "Choose an Option:",
                    "1) Stocks Management",
                    "2) Supplier Management",
                    "3) Order Management"};


    public Menu(PL_Stock pl_stock, PL_Supplier pl_sup) {
        this.pl_stock = pl_stock;
        this.pl_sup = pl_sup;
        this.scanner = new Scanner(System.in);
    }


    public void start()
    {
        int operation;

        while(true) {
            for (int i = 0; i < MENU.length; i++) {
                System.out.print(MENU[i] + "\n");
            }
            try {
                operation = Integer.parseInt(scanner.nextLine());
            } catch (Exception r) {
                System.out.print("Invalid operation. Please try again\n\n");
                continue;
            }

            String prop;
            switch (operation)
            {
                case 1:
                    pl_stock.start();
                    break;
                case 2:
                    pl_sup.start();
                    break;
                case 3:

                    break;
            }
        }
    }

}
