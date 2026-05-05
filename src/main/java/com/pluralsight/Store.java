
package com.pluralsight;

import java.io.*;
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

                Product item = new Product(id, name, price, 0);
                inventory.add(item);
            }
            reader.close();
        } catch (Exception a) {
            System.out.println("Sorry something went wrong");
        }
    }

    public static void addToCart(Product item, Scanner scanner, ArrayList<Product> cart){
        System.out.println(item + "\n");
        System.out.println("Add to cart (Y/N)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("Y")){
            if (item.getQuantity() >= 1) {
                item.setQuantity(item.getQuantity() + 1);
            } else {
                cart.add(item);
                item.setQuantity(1);
            }
        }
    }

    public static void lookingAtProducts(ArrayList<Product> inventory, Scanner scanner, ArrayList<Product> cart) {
        Product item = null;
        boolean isDone = false;
        System.out.print("Search Items/Add Items (Y/N): ");
        String input = scanner.nextLine();

        while(!isDone) {
            if (input.equalsIgnoreCase("Y")) {
                System.out.print("Enter Product ID to view/add, or X to return: ");
                String id = scanner.nextLine();
                if(!id.equalsIgnoreCase("X")) {
                    item = findProductById(id, inventory);
                    if (item != null) {
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
        for (Product item : inventory) {
            System.out.println(item);
        }
        System.out.println("=====================================================================");
        lookingAtProducts(inventory, scanner, cart);
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        double total = 0;
        for (Product product : cart) {
            System.out.println(product);
            product.setPrice((product.getPrice() * product.getQuantity()));
            total += product.getPrice();


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
            double payment;
            while (true) {
                    System.out.print("please provide payment amount: ");
                    if (scanner.hasNextDouble()) {
                        payment = scanner.nextDouble();
                        scanner.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                    }
                }
            double change = payment - totalAmount;
                if (change < 0) {
                    System.out.println("Not enough please try again");
                } else {
                    Receipt receipt = new Receipt(cart, totalAmount, payment, change);
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("Transactions.csv", true));
                        writer.write("\n\n****RECEIPT****\n");
                        System.out.println("\n****RECEIPT****");
                        for (Product product : cart) {
                            writer.write(product + "\n");
                            System.out.println(product);
                        }
                        writer.write(String.valueOf(receipt));
                        System.out.println(receipt);
                        writer.close();
                    } catch (Exception e){
                        System.out.println("failed to add transaction");
                    }
                    totalAmount = 0;
                }
        }
        cart.clear();
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        Product item = null;
        for (Product product : inventory) {
            if (id.equalsIgnoreCase(product.getId())) {
                item = product;
                break;
            }
        }
        if (item == null) {
            System.out.println("No Matching Items Please Try Again");
        }
        return item;
    }
}
    
        
    


