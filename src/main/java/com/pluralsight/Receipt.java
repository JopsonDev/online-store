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
        return "****RECEIPT****\n" + cart + "\nCart Total : $" + total + "\nTax        : $0:00\n-----------------------------\nTotal Due  : $" + total + "\n" +
                "Payment    : $" + payment + "\nChange     : $" + change + "\n=============================\nBalance    : $0.00" +
                "\nThank you for your purchase!";
    }
}
