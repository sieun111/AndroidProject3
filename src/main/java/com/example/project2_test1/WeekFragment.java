package com.example.project2_test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class WeekFragment extends Fragment {

    Calendar cal;
    ArrayList<String> dayList;
    ArrayList<String> dayList2;
    ArrayList<String> dayList3;
    TextView tv1, tv2, tv11;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private int mParam3;

    int now_year;
    int now_month;
    int now_week;
    int week;
    public WeekFragment() {
        // Required empty public constructor
    }
    public WeekFragment(int year, int month, int week) {
        // Required empty public constructor;
        now_year=year; now_month=month;now_week=week;
        //Log.d("test", "WeekFragment: "+week);
    }


    // TODO: Rename and change types and number of parameters
    public static WeekFragment newInstance(int now_year, int now_month, int now_week) {
        WeekFragment fragment = new WeekFragment(now_year,now_month,now_week);
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, now_year);
        args.putInt(ARG_PARAM2, now_month);
        args.putInt(ARG_PARAM3, now_week);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment ?????????????????? ??????
        View weekView= inflater.inflate(R.layout.fragment_week, container, false);
        // ???????????? ?????? ?????????
        GridView gridView1 = weekView.findViewById(R.id.gridview1);
        GridView gridView2 = weekView.findViewById(R.id.gridview2);


        dayList=new ArrayList<>();  //??????
        dayList2=new ArrayList<>(7);//??????
        dayList3=new ArrayList<>(184);//?????????2

        cal = Calendar.getInstance();


        // ?????? ??????, ???, ??? ??????

        //int now_year = Calendar.getInstance().get(Calendar.YEAR);
        //int now_month = Calendar.getInstance().get(Calendar.MONTH)+1;
        //int now_week = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH)-1;



        cal.set(now_year,now_month-1, 1);//????????? 1??? set
        int startday = cal.get(Calendar.DAY_OF_WEEK); //1?????? ??????
// 1??? ??? ???????????? ???????????????
        for (int i = 1; i < startday; i++) {
            dayList.add("");
        }
//?????? ?????? ?????? ?????????   5????????? ?????????(3)??? ??????????????? 2+1=3
        setCalDate(cal.get(Calendar.MONTH) + 1);
//6??? ??????????????? ???????????????
        for(int i=dayList.size();i<42; i++){ // 6???7??? (42)- ??????????????? ?????? ??????..
            dayList.add("");
        }
        //setCalWeek(cal.get());

//????????? 7??? ?????????
        week=now_week;
        Log.d("test", "onCreateView: "+week);
        int startweek = week*7; //daylist??? ????????? ?????????(0-41) ex) 14=2x7
        for(int i=0; i < 7 ;i++){
            dayList2.add(dayList.get(startweek+ i));
        }

        for(int i=0; i <24  ;i++){
            dayList3.add(""+i);
            dayList3.add(" ");
            dayList3.add(" ");
            dayList3.add(" ");
            dayList3.add(" ");
            dayList3.add(" ");
            dayList3.add(" ");
            dayList3.add(" ");
        }
//?????? ????????? ??? ??????.
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position=i;
                tv1 = (TextView) gridView1.getChildAt(position);
                //7??? ???????????? ??????
                for (int k = 0; k < 7; k++) {
                    gridView1.getChildAt(k).setBackgroundColor(Color.parseColor("#ffffff"));}
                //?????? ????????? ?????? ?????????
                tv1.setBackgroundColor(Color.CYAN);
                //?????? ?????? ????????? ????????? ??????
                Toast.makeText(getActivity(),now_year+"/"+now_month+"/"+dayList2.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position=i;

                FloatingActionButton fab =  weekView.findViewById(R.id.fab);
                fab.setOnClickListener(nview -> {
                    Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
                    intent.putExtra("year",now_year);
                    intent.putExtra("month",now_month);
                    int hour = position/8;
                    String dday = dayList2.get(position%8-1);
                    intent.putExtra("day", dday);

                    startActivity(intent);
                });

                /*tv2 = (TextView) gridView2.getChildAt(position);
                for (int j= 0; j < 112; j++) {
                    gridView2.getChildAt(j).setBackgroundColor(Color.parseColor("#ffffff"));}
                tv2.setBackgroundColor(Color.CYAN);
                //tv2??? ???????????? ????????? 7??? ?????? ????????? ???????????? ????????? ??? ??????.
                tv1 = (TextView) gridView1.getChildAt(position%8-1);
                for (int k = 0; k < 7; k++) {
                    gridView1.getChildAt(k).setBackgroundColor(Color.parseColor("#ffffff"));}
                tv1.setBackgroundColor(Color.CYAN);*/

                //????????? ????????? ?????? ????????? ????????? ??????
                Toast.makeText(getActivity(),"position="+position, Toast.LENGTH_SHORT).show();

            }
        });

        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, dayList2 ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                int pos=1;
                tv1 = (TextView) super.getView(position, convertView, parent);
                tv1.setBackgroundColor(Color.WHITE);
                tv1.setTextSize(15);

                //?????? ?????? ???????????? 1????????? ?????? ?????? ???.
                View view = super.getView(0,convertView,parent);
                view.setBackgroundColor(Color.CYAN);

                return tv1;
            }
        };
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_activated_1, dayList3 ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                tv2 = (TextView) super.getView(position, convertView, parent);
                tv2.setBackgroundColor(Color.WHITE);
                tv2.setTextSize(13);

                //?????? ?????? ???????????? 1????????? ?????? ?????? ???.
                //View view2 = super.getView(1,convertView,parent);
                //View view2 = super.getView((0),convertView,parent);
                // view2.setBackgroundColor(Color.CYAN);

                return tv2;
            }
        };

        gridView1.setAdapter(adapter1);
        gridView2.setAdapter(adapter2);
        //gridView1.setAdapter(new ArrayAdapter<String>(getActivity(),
        //android.R.layout.simple_list_item_activated_1, dayList2 ));
        //gridView2.setAdapter(new ArrayAdapter(getActivity(),
        // android.R.layout.simple_list_item_activated_1, dayList3));

        return weekView;
    }


    // ?????? ?????? ????????? ??? ???
    private void setCalDate(int now_month){//?????? ?????? ?????? ????????? ??????.
        cal.set(Calendar.MONTH, now_month - 1);

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add ("" + (i+1));  //getActualMaximum??? ???????????? ????????? ??????.
        }
    }

}