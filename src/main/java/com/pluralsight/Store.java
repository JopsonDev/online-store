
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {

        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        //adds items to inventory from file provided
        loadInventory("products.csv", inventory);

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
                scanner.nextLine();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner); //shows store inventory and allows users to add items to cart
                case 2 -> displayCart(cart, scanner); //displays contents of cart and gives user option to check out
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
    public static void addToCart(Product item, Scanner scanner, ArrayList<Product> cart){
        System.out.println("Add to cart (Y/N)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Y")){
            cart.add(item);
        }
    }
    public static void searchProducts(ArrayList<Product> inventory, Scanner scanner, ArrayList<Product> cart) {
        Product item = null;
        boolean isDone = false;

        System.out.println("Search Items? (Y/N): ");
        String input = scanner.nextLine();
        while(!isDone) {
            if (input.equalsIgnoreCase("Y")) {
                System.out.print("X to exit, Enter Id: ");
                String id = scanner.nextLine();
                if(!id.equalsIgnoreCase("X")) {
                    item = findProductById(id, inventory);
                    if (item != null) {
                        System.out.println(item);
                        addToCart(item, scanner, cart);
                    }
                }else {
                    isDone = true;
                }
            } else {
                isDone = true;
            }
        }
    }
    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        for(Product item: inventory){
            System.out.println(item);
        }
        System.out.println("=====================================================================");

        searchProducts(inventory, scanner, cart);

        System.out.println("=====================================================================");

        System.out.print("Would you like to add an item to your cart? C to continue: X to return to menu -> ");
        String choice = scanner.nextLine();
        boolean isDone = false;
        if (choice.equalsIgnoreCase("C")) {
            while (!isDone) {
                System.out.print("Please enter Item ID: ");
                String userID = scanner.nextLine();
                Product foundProduct = findProductById(userID, inventory);
                if (foundProduct != null){
                    cart.add(foundProduct);
                    System.out.print("Fantastic! Would you like anything else? C or X to return to menu -> ");
                    String more = scanner.nextLine();
                if (more.equalsIgnoreCase("X")) {
                    isDone = true;
                }
                }
            }
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        double total = 0;
        for(int i = 0; i < cart.size(); i++){
            System.out.println(cart.get(i));
            total += cart.get(i).getPrice();

            }
        System.out.printf("Total: %.2f%n", total);
        System.out.print("Would you like to check out? C to continue: X to return -> ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("C")){
            checkOut(cart, total, scanner);
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        while(totalAmount > 0) {
        System.out.print("please provide payment amount: ");
        Double payment = scanner.nextDouble();
        scanner.nextLine();

        double change = payment - totalAmount;
            if (change < 0) {
                System.out.println("Not enough please try again");
            } else {
                System.out.println("****RECEIPT****");
                for (int i = 0; i < cart.size(); i++) {
                    System.out.println(cart.get(i));
                }
                System.out.printf("Payment: $%.2f%nTotal Due: $%.2f%nChange: $%.2f%nBalance Due = $0", payment, totalAmount, change);
                totalAmount = 0;
            }
        }
        cart.clear();
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
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
    
        
    


