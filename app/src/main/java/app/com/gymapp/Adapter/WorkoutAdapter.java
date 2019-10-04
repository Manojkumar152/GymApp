package app.com.gymapp.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import app.com.gymapp.HomeActivity;
import app.com.gymapp.R;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    ArrayList<String> data;
    Activity activity;

    public WorkoutAdapter(HomeActivity homeActivity, ArrayList<String> workDays) {
        this.data=workDays;
        this.activity=homeActivity;
    }

    @NonNull
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.dashboard_workouts_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        holder.workDays.setText(data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView workDays,checked_items;

        public ViewHolder(View itemView) {
            super(itemView);
            workDays=itemView.findViewById(R.id.workout_days);
            checked_items=itemView.findViewById(R.id.checked_items);
        }
    }
}
