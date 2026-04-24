
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);
        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);

                Product item = new Product(id, name, price);
                inventory.add(item);
            }
            reader.close();
        } catch (Exception a) {
            System.out.println("Sorry something went wrong");
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
        for(Product item: inventory){
            System.out.println(item);
        }
        System.out.print("Would you like to add an item to your cart? Y for yes: N for no -> ");
        String choice = scanner.nextLine();
        boolean isDone = false;
        if (choice.equalsIgnoreCase("Y")) {
            while (!isDone) {
                System.out.print("Please enter Item ID: ");
                String userID = scanner.nextLine();
                Product foundProduct = findProductById(userID, inventory);
                if (foundProduct != null){
                    cart.add(foundProduct);
                    System.out.print("Fantastic! Would you like anything else? Y or N -> ");
                    String more = scanner.nextLine();
                if (more.equalsIgnoreCase("N")) {
                    isDone = true;
                }
                    System.out.println(cart);
                }
            }
        }
    }


    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)
        double total = 0;
        for(int i = 0; i < cart.size(); i++){
            System.out.println(cart.get(i));
            total += cart.get(i).getPrice();

            }
        System.out.printf("Total: %.2f", total);
        System.out.print("Would you like to check out? y for yes: n for no -> ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")){
            checkOut(cart, total, scanner);
        }
        }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
        System.out.print("please provide payment amount: ");
        Double payment = scanner.nextDouble();
        double change = payment - totalAmount;
        while(totalAmount > 0) {
            if (change < 0) {
                System.out.println("Not enough please try again");
            } else {
                System.out.println("****RECEIPT****");
                for (int i = 0; i < cart.size(); i++) {
                    System.out.println(cart.get(i));
                }
                System.out.println("$" + totalAmount + "\n-$" + payment + "\nChange: $" + change + "\nBalance Due: $0");
                totalAmount = 0;
            }
        }
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        Product item = null;
        for (int i = 0; i < inventory.size(); i++) {
            if (id.equalsIgnoreCase(inventory.get(i).getId())) {
                item = inventory.get(i);
            }
        }
        if (item == null) {
            System.out.println("No Matching Items Please Try Again");
        }
        return item;
    }
}
    
        
    


