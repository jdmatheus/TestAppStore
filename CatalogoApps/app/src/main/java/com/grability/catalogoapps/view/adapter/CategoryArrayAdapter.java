package com.grability.catalogoapps.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grability.catalogoapps.R;
import com.grability.catalogoapps.model.Category;

import java.util.List;

/**
 * Created by Julio on 19/01/2017.
 */

public class CategoryArrayAdapter extends ArrayAdapter<Category> {

    private static class ViewHolder {
        TextView tvName;
    }

    public CategoryArrayAdapter(Context context, List<Category> cats) {
        super(context, -1, cats);
    }

    @Override
    public long getItemId(int position) {
        Category cat = getItem(position);
        if (cat == null)
            return 0;
        return cat.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category cat = getItem(position);

        ViewHolder viewHolder;

        // check for reusable ViewHolder from a ListView item that scrolled
        // offscreen; otherwise, create a new ViewHolder
        if (convertView == null) { // no reusable ViewHolder, so create one
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView =
                    inflater.inflate(R.layout.item_category, parent, false);
            viewHolder.tvName =
                    (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        }
        else { // reuse existing ViewHolder stored as the list item's tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setings
        viewHolder.tvName.setText(cat.getLabel());
        return convertView;
    }

}
