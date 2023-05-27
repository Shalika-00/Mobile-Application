package com.example.sqliteapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder> {

    private List<ModelClass> userList;

    public Adaptor(List<ModelClass> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor.ViewHolder holder, int position) {
        int resource=userList.get(position).getImageview1();
        String name=userList.get(position).getText1();

        holder.setData(resource,name);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private Button clkBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview1);
//            textView = itemView.findViewById(R.id.text1);
            clkBtn = itemView.findViewById(R.id.clkBtn);
            clkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btnTxt = clkBtn.getText().toString();
                    Toast.makeText(view.getContext(), btnTxt + " clicked", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), Income.class);

                    i.putExtra("BtnTxt", btnTxt);
                    view.getContext().startActivity(i);

                }
            });
        }

        public void setData(int resource, String name) {
            if(name != null){
                imageView.setImageResource(resource);
//                textView.setText(name);
                clkBtn.setText(name);
            }

        }

    }
}
