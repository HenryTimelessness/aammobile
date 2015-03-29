package com.henrychua.mydailyassessment.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.henrychua.mydailyassessment.R;

import com.henrychua.mydailyassessment.models.DrawerItem;

/**
 * Created by henrychua on 06/11/2014.
 */
public class DrawerItemCustomAdapter extends ArrayAdapter<DrawerItem> {

    Context mContext;
    int layoutResourceId;
    DrawerItem data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        DrawerItem drawerItem = data[position];

        // if item is selected, use different font color and icon color
        if(drawerItem.getIsItemSelected()) {
            imageViewIcon.setImageResource(drawerItem.getIconSelected());
            textViewName.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            imageViewIcon.setImageResource(drawerItem.getIcon());
        }
        textViewName.setText(drawerItem.getName());

        return listItem;
    }

}