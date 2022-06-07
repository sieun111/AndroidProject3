package com.example.calendar;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthFragment extends Fragment {
    Calendar cal;
    ArrayList<String> dayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    int year;
    int month;

    public MonthFragment(){}

    /*public MonthFragment(int year, int month) {
        // Required empty public constructor
        now_year=year;
        now_month=month;
    } */

    public static MonthFragment newInstance(int year, int month) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View monthView= inflater.inflate(R.layout.fragment_month, container, false);
        // Inflate the layout for this fragment
        GridView gridView = monthView.findViewById(R.id.gridview);
        dayList=new ArrayList<String>();
        cal = Calendar.getInstance();

        cal.set(Integer.parseInt(String.valueOf(mParam1)), Integer.parseInt(String.valueOf(mParam2))-1, 1);

        int startday = cal.get(Calendar.DAY_OF_WEEK); //1일의 요일
        //cal.set(year, month-1, 1); //이번달 1일 set

        for (int i = 1; i < startday; i++) {
            dayList.add("");
        }// 1일 전 요일들에 공백채우기

        setCalDate(cal.get(Calendar.MONTH) + 1);//현재 월에 끝일 구하기

        for(int i=dayList.size();i<42; i++){
            dayList.add("");
        }//공백


        ArrayAdapter<String>adapter
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                dayList ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 어떠한 방향이든 상관없이 그리드뷰로 화면을 꽉 채우기 위해 각 칸의 높이를
                // 그리드뷰의 높이 / 6 으로 설정
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setBackgroundColor(Color.WHITE);
                int gridviewH = gridView.getHeight() / 6;
                // 화면 넘어가는 것 방지
                // 외부코드 참고
                tv.setHeight(gridviewH-2);
                return tv;
            }
        };
        gridView.setAdapter(adapter);//어댑터 연결

// 토스트 메세지
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),
                        mParam1+"/"+mParam2+"/"+dayList.get(i),Toast.LENGTH_SHORT).show(); //파라메터로 받아온 정보활용
                //view.setBackgroundColor(Color.YELLOW); // 색깔 누적

            }
        });
        return monthView;
    }

    // 해당 월에 표시할 일 수
    private void setCalDate(int now_month){
        cal.set(Calendar.MONTH, now_month - 1);

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(String.valueOf(i+1));
        }
    }
}