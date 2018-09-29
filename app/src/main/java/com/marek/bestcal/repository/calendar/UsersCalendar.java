package com.marek.bestcal.repository.calendar;

public class UsersCalendar {

    private long id;
    private String name;
    private String displayedName;
    private String accountName;
    private String ownerAccount;

    public UsersCalendar(long id, String name, String displayedName, String accountName, String ownerAccount) {
        this.id = id;
        this.name = name;
        this.displayedName = displayedName;
        this.accountName = accountName;
        this.ownerAccount = ownerAccount;
    }


    public String getDisplayedName() {
        return displayedName;
    }

    public long getId() {
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
}
