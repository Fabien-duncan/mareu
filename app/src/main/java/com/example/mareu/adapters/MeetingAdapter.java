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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to link the RecycerView in the main activity witht the list of meetings
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingHolder>{
    private List<Meeting> mAllMeetings = new ArrayList<>();
    private MeetingClickListener clickListener;

    /**
     * Constructor
     * @param clickListener
     */
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

        //accessibility content description
        holder.deleteImageButton.setContentDescription("suprimer l'élément " + currentMeeting.detailsForContentDescription());

        //checks if a meeting is soon and changes the circle depending on the result
        if(currentMeeting.checkIfSoon(LocalDateTime.now().plusDays(1)))holder.meetingItemImageView.setImageResource(R.drawable.baseline_red_circle_24);//soon
        else holder.meetingItemImageView.setImageResource(R.drawable.baseline_circle_24);//not soon

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeMeeting(currentMeeting.getId());
            }
        });

    }

    /**
     * @return the number of meetings
     */
    @Override
    public int getItemCount() {
        return mAllMeetings.size();
    }

    /**
     * retrieves the meetings needed to be displayed to the RecyclerView
     * @param meetings is the List of meetings to be displayed
     */
    public void setMeetings(List<Meeting> meetings){
        this.mAllMeetings = meetings;
        notifyDataSetChanged();
    }

    /**
     * is a ViewHolder. This class is used to do the binding of the views
     */
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

    /**
     * Interface to handle the clicks on a recycler view item
     */
    public interface MeetingClickListener{
        void meetingClick(long id);//this feature is not made yet, but could be used for open a page containing the details of a meeting and being able to edit them
        void removeMeeting(long id);
    }
}
