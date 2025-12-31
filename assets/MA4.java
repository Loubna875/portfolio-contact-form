import java.util.Scanner;
import java.util.Date;
/**
 * Cash Register Program
 * ---------------------
 * Steps:
 * 1. Initialize arrays for items, sales, and sale dates.
 * 2. Show menu until user quits.
 * 3. Insert items (random quantity and price, auto‑increment IDs).
 * 4. Remove items (set row to 0).
 * 5. Display items sorted by ID.
 * 6. Register sales (reduce stock, record sale, store date).
 * 7. Display sales history.
 * 8. Display sorted sales table.
 *
 * @author Loubna Othmani (louoth-5)
 */
public class MA4 {

    // Constants for the item array
    public static final int ITEM_ID = 0;
    public static final int ITEM_COUNT = 1;
    public static final int ITEM_PRICE = 2;
    public static final int ITEM_COLUMN_SIZE = 3;
    public static final int INITIAL_ITEM_SIZE = 10;

    // Constants for the sales array
    public static final int SALE_ITEM_ID = 0;
    public static final int SALE_ITEM_COUNT = 1;
    public static final int SALE_ITEM_PRICE = 2;
    public static final int SALE_COLUMN_SIZE = 3;
    public static final int MAX_SALES = 1000;

    // Item constraints
    public static final int MIN_ITEM_QUANTITY = 1;
    public static final int MAX_ITEM_QUANTITY = 10;
    public static final int MIN_ITEM_PRICE = 100;
    public static final int MAX_ITEM_PRICE = 1000;

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static final int INITIAL_ITEM_NUMBER = 999;

    private static Scanner userInputScanner = new Scanner(System.in);

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     *
     * @param inputScanner  test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    /**
     * Main entry point of the program.
     * Initializes arrays and runs the menu loop until user quits.
     * @param args command-line arguments (unused)
     */
    public static void main(final String[] args) {
        int[][] items = new int[INITIAL_ITEM_SIZE][ITEM_COLUMN_SIZE]; // Data structure to store items
        int[][] sales = new int[MAX_SALES][SALE_COLUMN_SIZE]; // Data structure to store sales
        Date[] saleDates = new Date[MAX_SALES]; // Data structure to store sale dates
        int lastItemNumber = INITIAL_ITEM_NUMBER; // Keep track of last added ItemNumber

        boolean running = true;
        while (running) {
            int choice = menu();
            switch (choice) {
                case MENU_ITEM_1:
                    System.out.println("How many items to insert?");
                    int n = input();
                    if (n <= 0) {
                        System.out.println("Invalid");
                        break;
                    }
                    items = insertItems(items, lastItemNumber, n);
                    lastItemNumber += n;
                    break;
                case MENU_ITEM_2:
                    System.out.println("Enter item ID to remove:");
                    int idToRemove = input();
                    if (idToRemove <= 0) {
                        System.out.println("Invalid");
                        break;
                    }
                    int res = removeItem(items, idToRemove);
                    if (res == 0) {
                        System.out.println("Item removed.");
                    } else {
                        System.out.println("Could not find");
                    }
                    break;
                case MENU_ITEM_3:
                    printItems(items);
                    break;
                case MENU_ITEM_4:
                    System.out.println("Enter item ID to sell:");
                    int idToSell = input();
                    if (idToSell <= 0) {
                        System.out.println("Invalid");
                        break;
                    }
                    System.out.println("Enter quantity to sell:");
                    int qty = input();
                    if (qty <= 0) {
                        System.out.println("Invalid");
                        break;
                    }
                    int sellRes = sellItem(sales, saleDates, items, idToSell, qty);
                    if (sellRes == 0) {
                        System.out.println("Sale registered.");
                    } else if (sellRes == -1) {
                        System.out.println("Could not find.");
                    } else {
                        System.out.println("Failed to sell specified amount");
                    }
                    break;
                case MENU_ITEM_5:
                    printSales(sales, saleDates);
                    break;
                case MENU_ITEM_6:
                    sortedTable(sales, saleDates);
                    break;
                case MENU_ITEM_Q:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid");
            }
        }
        System.out.println("Program terminated.");
    }
     /**
     * Prints the menu and returns the user's selection.
     *
     * @return selected menu item number, or -1 if user enters 'q'
     */
    public static int menu() {
        System.out.println("\n--- Cash Register Menu ---");
        System.out.println("1. Insert items");
        System.out.println("2. Remove an item");
        System.out.println("3. Display a list of items");
        System.out.println("4. Register a sale");
        System.out.println("5. Display sales history");
        System.out.println("6. Sort and display sales history table");
        System.out.println("q. Quit");
        System.out.print("Your Selection: ");
        return input();
    }
    /**
     * Reads user input: integer or 'q'.
     * Prints "Invalid" if the token is neither integer nor 'q'.
     *
     * @return integer value or -1 for 'q'
     */

    public static int input() {
        while (true) {
            if (userInputScanner.hasNextInt()) {
                int value = userInputScanner.nextInt();
                return value;
            } else {
                String s = userInputScanner.next();
                if (s.equalsIgnoreCase("q")) {
                    return MENU_ITEM_Q;
                }
                System.out.println("Invalid input. Enter an integer or q to quit:");
            }
        }
    }

    /**
     * Checks whether items array has enough capacity to add noOfItems.
     *
     * @param items     current items array
     * @param noOfItems number of items to add
     * @return true if array is full for the addition, false otherwise
     */

    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int count = 0;
        for (int[] item : items) {
            if (item[ITEM_ID] != 0) {
                count++;
            }
        }
        return (count + noOfItems > items.length);
    }

     /**
     * Extends the items array by adding capacity for noOfItems more entries.
     *
     * @param items     current items array
     * @param noOfItems number of items to add
     * @return new array with extended capacity and copied content
     */

    public static int[][] extendArray(final int[][] items, final int noOfItems) {
        int[][] newArr = new int[items.length + noOfItems][ITEM_COLUMN_SIZE];
        for (int i = 0; i < items.length; i++) {
            newArr[i] = items[i].clone();
        }
        return newArr;
    }

    /**
     * Inserts noOfItems new items with auto-incremented IDs, random quantity and price.
     *
     * @param items       items array (may be extended)
     * @param lastItemId  last used item ID
     * @param noOfItems   number of items to insert
     * @return possibly extended items array containing new items
     */
    public static int[][] insertItems(final int[][] items, final int lastItemId, final int noOfItems) {
        int[][] arr = items;
        if (checkFull(items, noOfItems)) {
            arr = extendArray(items, noOfItems);
        }
        int id = lastItemId;
        int remaining = noOfItems;
        for (int i = 0; i < arr.length && remaining > 0; i++) {
            if (arr[i][ITEM_ID] == 0) {
                id++;
                arr[i][ITEM_ID] = id;
                arr[i][ITEM_COUNT] = (int) (Math.random() * MAX_ITEM_QUANTITY) + MIN_ITEM_QUANTITY;
                arr[i][ITEM_PRICE] = (int) (Math.random() * (MAX_ITEM_PRICE - MIN_ITEM_PRICE + 1)) + MIN_ITEM_PRICE;
                remaining--;
            }
        }
        return arr;
    }
    /**
     * Removes an item by its ID, zeroing the row if found.
     *
     * @param items  items array
     * @param itemId ID to remove
     * @return 0 if removed, -1 if not found
     */
    public static int removeItem(final int[][] items, final int itemId) {
        for (int[] item : items) {
            if (item[ITEM_ID] == itemId) {
                item[ITEM_ID] = 0;
                item[ITEM_COUNT] = 0;
                item[ITEM_PRICE] = 0;
                return 0;
            }
        }
        return -1;
    }
    /**
     * Prints items sorted by ITEM_ID ascending.
     *
     * @param items items array
     */
    public static void printItems(final int[][] items) {
        int[][] copy = items.clone();
        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy.length - 1; j++) {
                if (copy[j][ITEM_ID] > copy[j + 1][ITEM_ID]) {
                    int[] tmp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = tmp;
                }
            }
        }
        System.out.println("ItemID | Quantity | Price");
        for (int[] item : copy) {
            if (item[ITEM_ID] != 0) {
                System.out.println(item[ITEM_ID] + " | " + item[ITEM_COUNT] + " | " + item[ITEM_PRICE]);
            }
        }
    }
     /**
     * Registers a sale: checks item existence and stock, reduces stock,
     * records sale and date into the sales arrays.
     *
     * @param sales      sales array (rows: itemId, qty, sum)
     * @param salesDate  sale dates aligned to sales rows
     * @param items      items array
     * @param itemIdToSell item ID to sell
     * @param amountToSell quantity to sell
     * @return 0 on success, -1 if item not found, or available quantity if not enough stock
     */
    public static int sellItem(final int[][] sales, final Date[] salesDate, final int[][] items, final int itemIdToSell, final int amountToSell) {
        // Find the item in the items array
        int itemIndex = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemIdToSell) {
                itemIndex = i;
                break;
            }
        }
        
        // Item not found
        if (itemIndex == -1) {
            return -1;
        }
        
        // Not enough items in stock
        if (items[itemIndex][ITEM_COUNT] < amountToSell) {
            return items[itemIndex][ITEM_COUNT];
        }

        // Record the sale before reducing stock (to use current price)
        int salePrice = items[itemIndex][ITEM_PRICE];
        
        // Reduce stock
        items[itemIndex][ITEM_COUNT] -= amountToSell;

        // Find empty slot in sales array
        int salesIndex = -1;
        for (int i = 0; i < sales.length; i++) {
            if (sales[i][SALE_ITEM_ID] == 0) {
                salesIndex = i;
                break;
            }
        }
        
        // Record the sale
        if (salesIndex != -1) {
            sales[salesIndex][SALE_ITEM_ID] = itemIdToSell;
            sales[salesIndex][SALE_ITEM_COUNT] = amountToSell;
            sales[salesIndex][SALE_ITEM_PRICE] = amountToSell * salePrice;
            salesDate[salesIndex] = new Date();
        }
        
        return 0;
    }

    /**
     * Prints the sales history in the format:
     * Date | ItemID | Quantity | Sum
     *
     * @param sales     sales array
     * @param salesDate sales date array aligned with sales
     */
    public static void printSales(final int[][] sales, final Date[] salesDate) {
        System.out.println("Date | ItemID | Quantity | Sum");
        for (int i = 0; i < sales.length; i++) {
            if (sales[i][SALE_ITEM_ID] != 0) {
                System.out.println(
                    salesDate[i] + " | "
                    + sales[i][SALE_ITEM_ID] + " | "
                    + sales[i][SALE_ITEM_COUNT] + " | "
                    + sales[i][SALE_ITEM_PRICE]
                );
            }
        }
    }

    /**
     * Sorts a copy of the sales table by item ID ascending,
     * keeps dates aligned, then prints the sorted table.
     *
     * @param sales     sales array
     * @param salesDate sales date array aligned with sales
     */
    public static void sortedTable(final int[][] sales, final Date[] salesDate) {
        // Create copies
        int[][] tempSales = new int[sales.length][SALE_COLUMN_SIZE];
        Date[] tempSalesDate = new Date[salesDate.length];

        for (int i = 0; i < sales.length; i++) {
            tempSales[i] = sales[i].clone();
            tempSalesDate[i] = salesDate[i];
        }

        // Bubble sort by item ID
        for (int i = 0; i < tempSales.length; i++) {
            for (int j = 0; j < tempSales.length - 1; j++) {
                if (tempSales[j][SALE_ITEM_ID] > tempSales[j + 1][SALE_ITEM_ID]) {
                    int[] tempSale = tempSales[j];
                    Date tempDate = tempSalesDate[j];

                    tempSales[j] = tempSales[j + 1];
                    tempSalesDate[j] = tempSalesDate[j + 1];

                    tempSales[j + 1] = tempSale;
                    tempSalesDate[j + 1] = tempDate;
                }
            }
        }

        // Print sorted sales
        printSales(tempSales, tempSalesDate);
    }
}