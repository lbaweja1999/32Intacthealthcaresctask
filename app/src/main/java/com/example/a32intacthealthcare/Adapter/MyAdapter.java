package com.example.a32intacthealthcare.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a32intacthealthcare.Model.Userdetails;
import com.example.a32intacthealthcare.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Activity obj;
    int res;
    ArrayList<Userdetails> arrayList=new ArrayList<>();
    @Override
    public int getCount() {
        return arrayList.size();
    }

    public MyAdapter(Activity obj, int res, ArrayList<Userdetails> arrayList) {
        this.obj = obj;
        this.res = res;
        this.arrayList = arrayList;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v1= LayoutInflater.from(obj).inflate(res,viewGroup,false);
        Userdetails u1=arrayList.get(i);
        TextView textView,textView1,textView2,textView3,textView4,textView5;
        textView=v1.findViewById(R.id.name);
        textView1=v1.findViewById(R.id.contact);
        textView2=v1.findViewById(R.id.address);
        textView3=v1.findViewById(R.id.date);
        textView4=v1.findViewById(R.id.time);
        textView5=v1.findViewById(R.id.prob);
        textView.setText(u1.getName());
        textView1.setText(u1.getContact());
        textView2.setText(u1.getAddress());
        textView3.setText(u1.getDate());
        textView4.setText(u1.getTime());
        textView5.setText(u1.getProblem());
        return v1;
    }
}
