package com.example.mareu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.models.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingHolder>{
    private List<Meeting> mAllMeetings = new ArrayList<>();
    private MeetingClickListener clickListener;
    public MeetingAdapter(MeetingClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new MeetingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting currentMeeting = mAllMeetings.get(position);
        holder.meetingDetailsTextView.setText(currentMeeting.detailsToString());
        holder.participantsTextView.setText(currentMeeting.participantsToString());
        holder.meetingItemImageView.setImageResource(R.drawable.baseline_circle_24);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeMeeting(currentMeeting.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAllMeetings.size();
    }

    public void setMeetings(List<Meeting> meetings){
        this.mAllMeetings = meetings;
        notifyDataSetChanged();
    }

    class MeetingHolder extends RecyclerView.ViewHolder{
        private TextView meetingDetailsTextView;
        private TextView participantsTextView;
        private ImageView meetingItemImageView;
        private ImageButton deleteImageButton;

        public MeetingHolder(@NonNull View itemView) {
            super(itemView);
            meetingDetailsTextView = itemView.findViewById(R.id.meetings_item_tv_details);
            participantsTextView = itemView.findViewById(R.id.meetings_item_tv_participants);
            meetingItemImageView = itemView.findViewById(R.id.meetings_item_iv_avatar);
            deleteImageButton = itemView.findViewById(R.id.meetings_item_ib_delete);

        }
    }
    public interface MeetingClickListener{
        void meetingClick(long id);
        void removeMeeting(long id);
    }
}
