package com.fever.feverapp.handlers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fever.feverapp.objects.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {

    private static final String TAG = "UserListAdapter";

    private Context mContext;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.mContext = mContext;
    }

   // @Override
    //public View getView (int position, View convertView, ViewGroup parent) {
     //   String name = getItem(position).getName();
     //   String phoneNum = getItem(position).getPhoneNum();
     //   return null;
    //}

}
