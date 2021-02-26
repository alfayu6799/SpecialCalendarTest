package com.example.specialcalendartest;

import android.graphics.drawable.Drawable;
import android.text.style.RelativeSizeSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class MyEventDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;

    public MyEventDecorator(Drawable drawable, Collection<CalendarDay> calendarDays) {
        this.drawable = drawable;
        this.dates = new HashSet<>(calendarDays);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new RelativeSizeSpan(1.2f)); //text's size
    }
}
