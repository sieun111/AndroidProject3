package com.example.project2_test1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthFragment extends Fragment {
    Calendar cal;
    ArrayList<String> dayList;
    ArrayList<String> titleList;
    ArrayList<GridItem> calList;
    ArrayList<ItemMonth> monthList;
    //GridAdapter gridAdapter;
    static ItemAdapter adapt;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_TEXT = "text";

    private int mParam1;
    private int mParam2;
    private DBHelper mDbHelper;

    int year;
    int month;

    AlertDialog.Builder builder; //다이얼로그
    String[] title; //다이얼로그 row
    List<String> dialogItem ;

    public MonthFragment(){}

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
        // 그리드뷰 객체 얻어옴
        GridView gridView = monthView.findViewById(R.id.gridview);
        mDbHelper = new DBHelper(this.getContext());
        monthList = new ArrayList<ItemMonth>();

        calList=new ArrayList<GridItem>();
        dayList=new ArrayList<String>();
        titleList=new ArrayList<String>();
        cal = Calendar.getInstance();

//파라미터에 저장된 값을 넣어 cal설정
        cal.set(Integer.parseInt(String.valueOf(mParam1)), Integer.parseInt(String.valueOf(mParam2))-1, 1);

        //cal.set(year, month-1, 1); //이번달 1일 set
        int startday = cal.get(Calendar.DAY_OF_WEEK); //1일의 요일
// 1일 전 요일들에 공백채우기
        for (int i = 1; i < startday; i++) {
            dayList.add("");
            titleList.add("");
            monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2), null, null, null));
            //calList.add(new GridItem(" "," "));
        }
//현재 월에 끝일 구하기
        setCalDate(cal.get(Calendar.MONTH) + 1);
//6행 맞추기위한 공백채우기
        for(int i=dayList.size();i<42; i++){ // 6행7열 (42)- 리스트항목 개수 빼면..
            dayList.add("");
            //calList.add(new GridItem(" "," "));
            titleList.add("");
        }
        for(int i=0;i<42;i++){
            //GridItem gridItem = new GridItem(String.valueOf(dayList.get(i)),String.valueOf(titleList.get(i)));
            calList.add(new GridItem(" "," "));
            calList.get(i).setDay(dayList.get(i));
            monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2), null, null, null));
            //   calList.get(i).setTitle(titleList.get(i));
            //calList.add(gridItem);
        }

        Cursor cursor = mDbHelper.getAllUsersBySQL();

//어댑터 연결
        adapt = new ItemAdapter(getActivity(), R.layout.gridview_item, monthList ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position,convertView,parent);
                View tv_cell = (View) view.findViewById(R.id.month_item);
                tv_cell.getLayoutParams().height = gridView.getHeight()/6;
                return tv_cell;

            }
        };
        gridView.setAdapter(adapt);

// 그리드뷰 선택시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridItem gridItem = calList.get(i);


                //(monthList.get(String.valueOf(mParam1), String.valueOf(mParam2), String.valueOf(i + 1), null, null)
//                if(adapt.getText1(i)= null) {
//                    showDialog(getView());
//                }
//                else {
                //아무것도 없으면 :그리드뷰 선택 후 이동 , 일정있으면 : 다이어로그
                //if(titleList.get(i)== null)
                FloatingActionButton fab = monthView.findViewById(R.id.fab);
                fab.setOnClickListener(nview -> {//  ??
                    //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MonthCalAddActivity.class);
                    intent.putExtra("year", mParam1);
                    intent.putExtra("month", mParam2);
                    intent.putExtra("day", dayList.get(i));
                    startActivity(intent);

                });
                // }
                Toast.makeText(getActivity(),
                        mParam1+"/"+mParam2+"/"+dayList.get(i),Toast.LENGTH_SHORT).show(); //파라메터로 받아온 정보활용

            }
        });

        //DBHelper helper = new DBHelper(this);
        return monthView;
    }
    //그리드뷰 i번째 항목 선택 알려주는 인터페이스
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

    // 해당 월에 표시할 일 수
    private void setCalDate(int now_month) {
        cal.set(Calendar.MONTH, now_month - 1);
        mDbHelper = new DBHelper(getActivity());
        //Cursor cursor = mDbHelper.getAllUsersBySQL();
        //String puttitle = getIntent().getStringExtra("title");

        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add(String.valueOf(i + 1));

            Cursor cursor = mDbHelper.getDayUsersBySQL(String.valueOf(mParam1), String.valueOf(mParam2), String.valueOf(i));
            //int titlenum = cursor.getColumnIndex(UserContract.Users.KEY_TITLE);
            if(cursor.moveToNext()){
                int titlenum = cursor.getColumnIndex(UserContract.Users.KEY_TITLE);
                monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2),
                        String.valueOf(i+1),cursor.getString(titlenum),null));
            }else{
                monthList.add(new ItemMonth(String.valueOf(mParam1), String.valueOf(mParam2),
                        String.valueOf(i+1), null, null));
            }

        }

    }

}