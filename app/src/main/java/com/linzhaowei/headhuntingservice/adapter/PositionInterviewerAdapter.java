package com.linzhaowei.headhuntingservice.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.linzhaowei.headhuntingservice.ECChat;
import com.linzhaowei.headhuntingservice.JobObjectiveCard;
import com.linzhaowei.headhuntingservice.data.JobObjects;
import com.linzhaowei.headhuntingservice.R;

import java.util.List;


/**
 * 招聘者职位Adapter
 */
public class PositionInterviewerAdapter extends RecyclerView.Adapter<PositionInterviewerAdapter.ViewHolder> {
    private List<JobObjects> mjobobjectslist;


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nametext;
        TextView jobtext;
        TextView addresstext;

        View jobView;


        public ViewHolder(View view) {
            super(view);
            jobView = view;
            nametext = view.findViewById(R.id.nametext);
            jobtext = view.findViewById(R.id.jobtext);
            addresstext = view.findViewById(R.id.addresstext);

        }
    }

    public PositionInterviewerAdapter(List<JobObjects> jobObjectsList) {
        mjobobjectslist = jobObjectsList;
    }

    @Override
    public PositionInterviewerAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.position_item, parent, false);
        final PositionInterviewerAdapter.ViewHolder holder = new PositionInterviewerAdapter.ViewHolder(view);

        holder.jobView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                JobObjects jobObjects = mjobobjectslist.get(position);
                parent.getContext().startActivity(new Intent(v.getContext(), JobObjectiveCard.class)
                        .putExtra("username", jobObjects.getUsername())
                        .putExtra("jobObjects", jobObjects)
                );
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(PositionInterviewerAdapter.ViewHolder holder, int position) {
        JobObjects jobObjects = mjobobjectslist.get(position);
        holder.nametext.setText((jobObjects.getRealname()));
        holder.jobtext.setText(jobObjects.getJob());
        holder.addresstext.setText(jobObjects.getAddress());
    }

    @Override
    public int getItemCount() {
        return mjobobjectslist.size();
    }

}
