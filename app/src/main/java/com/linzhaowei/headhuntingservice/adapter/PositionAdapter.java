package com.linzhaowei.headhuntingservice.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hyphenate.easeui.EaseConstant;
import com.linzhaowei.headhuntingservice.ECChat;
import com.linzhaowei.headhuntingservice.data.JobInformation;
import com.linzhaowei.headhuntingservice.R;

import java.util.List;


/**
 * 职位Adapter
 */
public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.ViewHolder> {
    private List<JobInformation> mjobinformationlist;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nametext;
        TextView jobtext;
        TextView salarytext;
        TextView addresstext;

        View jobView;


        public ViewHolder(View view){
            super(view);
            jobView=view;
            nametext=view.findViewById(R.id.nametext);
            jobtext=view.findViewById(R.id.jobtext);
            salarytext=view.findViewById(R.id.salarytext);
            addresstext=view.findViewById(R.id.addresstext);

        }
    }

    public PositionAdapter(List<JobInformation> jobInformations){
        mjobinformationlist=jobInformations;
    }

    @Override
    public PositionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_item,parent,false);
        final PositionAdapter.ViewHolder holder=new PositionAdapter.ViewHolder(view);

        holder.jobView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                JobInformation jobInformation=mjobinformationlist.get(position);
                parent.getContext().startActivity(new Intent(v.getContext(), ECChat.class)
                        .putExtra(EaseConstant.EXTRA_USER_ID, jobInformation.getUsername())
                        .putExtra("realname",jobInformation.getRealname())
                        );
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PositionAdapter.ViewHolder holder, int position){
        JobInformation jobInformation=mjobinformationlist.get(position);
        holder.nametext.setText((jobInformation.getRealname()));
        holder.jobtext.setText(jobInformation.getJob());
        holder.addresstext.setText(jobInformation.getAddress());
        holder.salarytext.setText(jobInformation.getSalary());
    }

    @Override
    public int getItemCount(){
        return mjobinformationlist.size();
    }

}