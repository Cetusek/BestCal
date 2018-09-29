package com.marek.bestcal.config.calendarlist;


import com.marek.bestcal.repository.calendar.UsersCalendar;

public class CalendarListItem  {

    private long id;
    private String displayedName;
    private String accountName;
    private boolean isVisible;


    public CalendarListItem(UsersCalendar usersCalendar) {
        id = usersCalendar.getId();
        displayedName = usersCalendar.getDisplayedName();
        accountName = usersCalendar.getAccountName();
        isVisible = false;
    }

    public long getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public String getAccountName() {
        return accountName;
    }
}
