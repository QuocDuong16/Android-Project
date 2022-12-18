package com.example.bt4_googleapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Official> officials;
    ArrayList<Office> offices;
    String headerTitle;

    public MyAdapter(Context ct, ArrayList<Official> o1, ArrayList<Office> o2) {
        context = ct;
        officials = o1;
        offices = o2;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycleview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setAgencyName(offices.get(position).name);
        int length = offices.get(position).officialIndices.length;
        Official[] officialNames = new Official[length];
        for (int i = 0; i < length; i++) {
            int tmp = Integer.parseInt(offices.get(position).officialIndices[i]);
            officialNames[i] = officials.get(tmp);
        }
        ListViewAdapter adapter = new ListViewAdapter(context, officialNames);
        holder.setOfficialName(adapter);
    }

    @Override
    public int getItemCount() {
        return offices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView agencyName;
        ListView officialName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            agencyName = itemView.findViewById(R.id.agencyName);
            officialName = itemView.findViewById(R.id.list);
        }

        public void setAgencyName(String agencyName) {
            this.agencyName.setText(agencyName);
        }
        public void setOfficialName(ListViewAdapter adapter) {
            View item = adapter.getView(0, null, officialName);
            item.measure(0, 0);
            ViewGroup.LayoutParams params = officialName.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = (int) (adapter.getCount() * item.getMeasuredHeight());
            officialName.setLayoutParams(params);
            officialName.setAdapter(adapter);
            officialName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(context, OfficialActivity.class);
                    Official official = adapter.getItem(i);
                    Office office = new Office();
                    boolean check = false;
                    for (Office o : offices) {
                        for (String j : o.officialIndices) {
                            if (Integer.parseInt(j) == officials.indexOf(official)) {
                                office = o;
                                check = true;
                                break;
                            }
                        }
                        if (check) break;
                    }
                    intent.putExtra("Header Title", headerTitle);
                    intent.putExtra("Official", official);
                    intent.putExtra("Office Name", office.name);
                    context.startActivity(intent);
                }
            });
        }
    }
}
