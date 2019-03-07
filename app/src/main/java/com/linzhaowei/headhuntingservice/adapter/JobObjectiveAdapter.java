package com.linzhaowei.headhuntingservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.linzhaowei.headhuntingservice.data.JobObjects;
import com.linzhaowei.headhuntingservice.R;

import java.util.List;


/**
 * 求职意向Adapter
 */

public class JobObjectiveAdapter extends RecyclerView.Adapter<JobObjectiveAdapter.ViewHolder> {
    private List<JobObjects> mjobobjectslist;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView jobtext;
        TextView addresstext;

        View jobView;

        public ViewHolder(View view){
            super(view);
            jobView=view;
            jobtext=view.findViewById(R.id.job_text);
            addresstext=view.findViewById(R.id.address_text);

        }
    }

    public JobObjectiveAdapter(List<JobObjects> jobObjectsList){
        mjobobjectslist=jobObjectsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobobjective_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.jobView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                JobObjects jobObjects=mjobobjectslist.get(position);
                Toast.makeText(v.getContext(),"you clicked view"+jobObjects.getJob(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        JobObjects jobObjects=mjobobjectslist.get(position);
        holder.jobtext.setText(jobObjects.getJob());
        holder.addresstext.setText(jobObjects.getAddress());
    }

    @Override
    public int getItemCount(){
        return mjobobjectslist.size();
    }


}
