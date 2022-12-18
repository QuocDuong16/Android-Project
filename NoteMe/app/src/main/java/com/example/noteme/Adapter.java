package com.example.noteme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Note> notes;

    Adapter(Context context, List<Note> notes) {
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            String title = notes.get(position).getTitle();
            String detail = notes.get(position).getContent();
            String date = notes.get(position).getDate();
            String time = notes.get(position).getTime();

            holder.nTitle.setText(title);
            holder.nDetails.setText(detail);
            holder.nDate.setText(date);
            holder.nTime.setText(time);




    }

    @Override
    public int getItemCount() {

        return notes.size();
    }




    public class  ViewHolder extends RecyclerView.ViewHolder {
        TextView nTitle,nDetails ,nDate,nTime,nID;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            nTitle = itemView.findViewById(R.id.nTitle);
            nDetails = itemView.findViewById(R.id.nDetails);
            nDate = itemView.findViewById(R.id.nDate);
            nTime = itemView.findViewById(R.id.nTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),Details.class);
                    i.putExtra("ID",notes.get(getAdapterPosition()).getID());
                    view.getContext().startActivity(i);
                }
            });



        }
    }
}
