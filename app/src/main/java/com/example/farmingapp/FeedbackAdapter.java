package com.example.farmingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private final List<FeedbackModel> feedbackList;

    public FeedbackAdapter(List<FeedbackModel> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackModel feedback = feedbackList.get(position);
        holder.tvName.setText("नाव: " + feedback.getName());
        holder.tvFeedback.setText("फीडबॅक: " + feedback.getFeedback());
    }

    @Override
    public int getItemCount() {
        return feedbackList != null ? feedbackList.size() : 0;
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFeedback;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvFeedback = itemView.findViewById(R.id.etFeedback);
        }
    }
}
