package com.pluralsight;

import java.util.ArrayList;

public class Receipt {
    private double total;
    private double payment;
    private double change;
    private ArrayList cart;


    public Receipt(ArrayList cart, double total, double payment, double change) {
        this.cart = cart;
        this.change = change;
        this.payment = payment;
        this.total = total;
    }

    public ArrayList getCart() {
        return cart;
    }

    public double getChange() {
        return change;
    }

    public double getPayment() {
        return payment;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("\nCart Total : $%,.2f\nTax        : $0:00\n-----------------------------\nTotal Due  : $%.2f" + "\n" +
                "Payment    : $%,.2f\nChange     : $%,.2f\n=============================\nBalance    : $0.00" +
                "\nThank you for your purchase!", total, total, payment, change);
    }
}
