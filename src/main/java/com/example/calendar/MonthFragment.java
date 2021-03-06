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

        int startday = cal.get(Calendar.DAY_OF_WEEK); //1?????? ??????
        //cal.set(year, month-1, 1); //????????? 1??? set

        for (int i = 1; i < startday; i++) {
            dayList.add("");
        }// 1??? ??? ???????????? ???????????????

        setCalDate(cal.get(Calendar.MONTH) + 1);//?????? ?????? ?????? ?????????

        for(int i=dayList.size();i<42; i++){
            dayList.add("");
        }//??????


        ArrayAdapter<String>adapter
                = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                dayList ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // ????????? ???????????? ???????????? ??????????????? ????????? ??? ????????? ?????? ??? ?????? ?????????
                // ??????????????? ?????? / 6 ?????? ??????
                TextView tv = (TextView) super.getView(position, convertView, parent);
                tv.setBackgroundColor(Color.WHITE);
                int gridviewH = gridView.getHeight() / 6;
                // ?????? ???????????? ??? ??????
                // ???????????? ??????
                tv.setHeight(gridviewH-2);
                return tv;
            }
        };
        gridView.setAdapter(adapter);//????????? ??????

// ????????? ?????????
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),
                        mParam1+"/"+mParam2+"/"+dayList.get(i),Toast.LENGTH_SHORT).show(); //??????????????? ????????? ????????????
                //view.setBackgroundColor(Color.YELLOW); // ?????? ??????

            }
        });
        return monthView;
    }

    // ?????? ?????? ????????? ??? ???
    private void setCalDate(int now_month){
        cal.set(Calendar.MONTH, now_month - 1);

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(String.valueOf(i+1));
        }
    }
}