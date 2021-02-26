package com.example.specialcalendartest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements OnDateSelectedListener{

    private static final String TAG = "MainActivity";

    private MaterialCalendarView widget;

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide ActionBar
        setContentView(R.layout.activity_main);

        widget = findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        final LocalDate instance = LocalDate.now();
        widget.setSelectedDate(instance);

//     //限制無法跨年滑動
//        final LocalDate min = LocalDate.of(instance.getYear(), Month.JANUARY, 1);
//        final LocalDate max = LocalDate.of(instance.getYear(), Month.DECEMBER, 31);
//        widget.state().edit().setMinimumDate(min).setMaximumDate(max).commit();

        widget.addDecorators(
                new MySelectorDecorator(this), //點擊日期後的背景
//                new HighlightWeekendsDecorator(), //周六日整排背景顏色變更
                oneDayDecorator
        );

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        LocalDate choseDay = LocalDate.from(date.getDate());
        Toast.makeText(MainActivity.this, "你選擇:" + choseDay, Toast.LENGTH_SHORT).show();
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();  //重新繪製
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LocalDate temp = LocalDate.now().minusMonths(2);
            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                final CalendarDay day = CalendarDay.from(temp);
                dates.add(day);
                temp = temp.plusDays(5);

            }



            return dates;
        }

        @SuppressLint({"NewApi", "UseCompatLoadingForDrawables"})
        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            widget.addDecorator(new MyEventDecorator(getDrawable(R.mipmap.ic_2_5), calendarDays));

        }
    }

}