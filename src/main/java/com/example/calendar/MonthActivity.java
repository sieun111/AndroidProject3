package com.example.calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Calendar;

public class MonthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int now_year = Calendar.getInstance().get(Calendar.YEAR);
        int now_month = Calendar.getInstance().get(Calendar.MONTH)+1 ;

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthViewAdapter(this,4);
        vpPager.setAdapter(adapter);

        vpPager.setCurrentItem(12*(now_year-2018)+(now_month-1));
        //숫자를 정해놓고 연산통해 날짜를 일반화함

        // 앱바, 첫 페이지에 현재 날짜 표시
        ActionBar ab = getSupportActionBar();
        ab.setTitle((now_year)+ "년 " + now_month + "월");

        //넘길때마다 앱바 날짜 바뀌게 출력
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                int year = (position/12)+2018;
                int month = position%12+1;
                ActionBar ab = getSupportActionBar();
                ab.setTitle((year)+ "년 " + month + "월");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }//앱바에 오버플로우 메뉴 추가

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month:
                MonthFragment monthFragment = new MonthFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.month_fragment,monthFragment).commit();
                return true;
            case R.id.week:
                Intent intent = new Intent(this, WeekActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//메뉴를 누르면 해당 페이지로 이동
    }
}