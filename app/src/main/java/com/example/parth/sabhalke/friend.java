package com.example.parth.sabhalke;

/**
 * Created by Parth on 3/9/2017.
 */

public class friend {
    int id;
    String name;
    int balance;

    public friend(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
    public friend()
    {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return name;
    }
}
