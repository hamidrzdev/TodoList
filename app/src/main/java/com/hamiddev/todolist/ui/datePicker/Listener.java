package com.hamiddev.todolist.ui.datePicker;


import com.hamiddev.todolist.ui.datePicker.util.PersianCalendar;

public interface Listener {
    void onDateSelected(PersianCalendar persianCalendar);

    void onDismissed();
}