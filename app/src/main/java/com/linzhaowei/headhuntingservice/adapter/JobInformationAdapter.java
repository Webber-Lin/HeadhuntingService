package com.linzhaowei.headhuntingservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.linzhaowei.headhuntingservice.data.JobInformation;
import com.linzhaowei.headhuntingservice.R;

import java.util.List;

public class JobInformationAdapter extends RecyclerView.Adapter<JobInformationAdapter.ViewHolder> {
    private List<JobInformation> mjobinformationlist;


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

    public JobInformationAdapter(List<JobInformation> jobinformation){

        mjobinformationlist=jobinformation;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobobjective_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

        holder.jobView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                JobInformation jobInformation=mjobinformationlist.get(position);

                Toast.makeText(v.getContext(),"you clicked view:"+jobInformation.getJob(),Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        JobInformation jobInformation=mjobinformationlist.get(position);
        holder.jobtext.setText(jobInformation.getJob());
        holder.addresstext.setText(jobInformation.getAddress());
    }

    @Override
    public int getItemCount(){
        return mjobinformationlist.size();
    }





}
