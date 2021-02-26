package com.example.specialcalendartest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;
/*** *** **
*  點擊 Event
* *** **/

public class OneDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public OneDayDecorator() {
        date = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));          //text font's size
        view.addSpan(new RelativeSizeSpan(1.5f)); //text's size
        view.addSpan(new ForegroundColorSpan(Color.WHITE));   //text's color
    }

    public void setDate(LocalDate date) {
        this.date = CalendarDay.from(date);
    }
}
