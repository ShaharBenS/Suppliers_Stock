package PL;

import java.util.Scanner;

/**
 * Created by Omri on 06-Apr-17.
 */
public class Menu {


    private Scanner scanner = new Scanner(System.in);
    private final String[] MENU =
            {   "Choose an Option:",
                "1) Stocks Management",
                "2) Supplier Management",
                "3) Order Management"};

    public void start()
    { int operation;

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

                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
        }
    }

}
