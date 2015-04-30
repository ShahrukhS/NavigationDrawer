package com.example.android.navigationdrawerexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MainAdapter extends ArrayAdapter<MainModel>{
	private final Context context;
	private final MainModel[] values;
	
	public MainAdapter(Context context, MainModel[] values){
		super(context, R.layout.main_list_item, values);
		this.context = context;
		this.values = values;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = convertView;
        if (rowView == null)
        	rowView = inflater.inflate(R.layout.main_list_item, parent, false);
		TextView title, desc, size;
		title = (TextView) rowView.findViewById(R.id.firstLine);
		desc = (TextView) rowView.findViewById(R.id.secondLine);
		size = (TextView) rowView.findViewById(R.id.size);
		ImageView img = (ImageView) rowView.findViewById(R.id.icon);
		
		title.setText(values[position].getTitle());
		desc.setText(values[position].getDesc());
		size.setText(values[position].getSize());
		img.setImageResource(values[position].getImgRes());
		return rowView;
	}
}
