package com.marek.bestcal.repository.calendar;

public class UsersCalendar {

    private int id;
    private String name;
    private String displayedName;
    private String accountName;
    private String ownerAccount;
    private int color;

    public UsersCalendar(int id, String name, String displayedName, String accountName, String ownerAccount, int color) {
        this.id = id;
        this.name = name;
        this.displayedName = displayedName;
        this.accountName = accountName;
        this.ownerAccount = ownerAccount;
        this.color = color;
    }


    public String getDisplayedName() {
        return displayedName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public int getColor() {
        return color;
    }
}
