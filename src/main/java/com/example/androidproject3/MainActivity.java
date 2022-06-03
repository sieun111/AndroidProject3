package com.example.androidproject3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;

    private Calendar mCal;

    Fragment mfragment;
    Fragment wfragment;
    FragmentTransaction tran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR); //화면 회전

        setContentView(R.layout.activity_main);

        mfragment = new MonthViewFragment();
        wfragment = new WeekViewFragment();

//        ViewPager2 vpPager = findViewById(R.id.vpPager);
//        FragmentStateAdapter adapter = new PagerAdapter(this);
//        vpPager.setAdapter(adapter);

   /*     Intent intent= getIntent(); //시작 정보

        gridView = (GridView)findViewById(R.id.gridview);
        mCal = Calendar.getInstance();

        //오늘 날짜 세팅
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA); //년 저장
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA); //월 저장
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA); //일 저장

        dayList = new ArrayList<String>(); //gridview 요일 표시

        mCal = Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add(" ");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);


        ActionBar ab = getSupportActionBar() ;
        ab.setTitle(mCal.get(Calendar.YEAR) + "년" +(mCal.get(Calendar.MONTH)+1)+ "월") ;
        //앱바에 현재 표시된 달력의 연월 표시


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,
                        mCal.get(Calendar.YEAR)+"."+ (mCal.get(Calendar.MONTH)+1)+"."+ mCal.get(Calendar.DATE)+"일",Toast.LENGTH_SHORT).show();
            }
        }); //toast 메시지 띄움


        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);
*/

    }

    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(" " + (i + 1));
        }
    }//월에 표시할 일 수 구하기


    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;

        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            //GridView gridView = (GridView)findViewById(R.id.gridview);
            //int gridviewH = (gridView.getHeight()/6);
            //그리드뷰안에 날짜를 출력할 TextView의 높이를 그리드뷰의 높이/6 로 잡아준다.
            //TextView textView = (TextView)findViewById(R.id.day);
            //
            // textView.setHeight(gridviewH);
            //실행 오류

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.day, parent, false);
                holder = new ViewHolder();

                holder.tvItemGridView = (TextView) convertView.findViewById(R.id.day);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.tvItemGridView.setText("" + getItem(position));

            mCal = Calendar.getInstance(); //오늘 day 가져옴

            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);

            mCal = Calendar.getInstance();

            //gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //    ViewHolder holder = null;
            //    @Override
            //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //        holder.tvItemGridView.setBackgroundColor(Color.CYAN);
            //    }
            //}); //날짜 누르면 색 바뀜

            Integer DAY = mCal.get(Calendar.DAY_OF_MONTH); //오늘 날짜 가져옴
            String Day = String.valueOf(DAY);
            if (Day.equals(getItem(position))) {
                holder.tvItemGridView.setBackgroundColor(Color.CYAN);
            }//오늘 날짜 색 바꿈

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }//앱바에 오버플로우 메뉴 추가


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_month:
                tran = getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.fragment_container, mfragment);
                tran.commit();
                return true;
            case R.id.action_week:
                tran = getSupportFragmentManager().beginTransaction();
                tran.replace(R.id.fragment_container, wfragment);
                tran.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//메뉴를 누르면 해당 페이지로 이동
}