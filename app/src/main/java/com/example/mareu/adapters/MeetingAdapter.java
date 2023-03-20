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
import com.example.mareu.databinding.MeetingItemBinding;
import com.example.mareu.models.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to link the RecyclerView in the main activity with the list of meetings
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
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       MeetingItemBinding meetingItemBinding = MeetingItemBinding.inflate(layoutInflater,parent, false);
       return new MeetingHolder(meetingItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting currentMeeting = mAllMeetings.get(position);
        holder.mMeetingItemBinding.meetingsItemTvDetails.setText(currentMeeting.detailsToString());
        holder.mMeetingItemBinding.meetingsItemTvParticipants.setText(currentMeeting.participantsToString());

        //accessibility content description
        holder.mMeetingItemBinding.meetingsItemIbDelete.setContentDescription("suprimer l'élément " + currentMeeting.detailsForContentDescription());

        //checks if a meeting is soon and changes the circle depending on the result
        if(currentMeeting.checkIfSoon(LocalDateTime.now().plusDays(1)))holder.mMeetingItemBinding.meetingsItemIvAvatar.setImageResource(R.drawable.baseline_red_circle_24);//soon
        else holder.mMeetingItemBinding.meetingsItemIvAvatar.setImageResource(R.drawable.baseline_circle_24);//not soon

        holder.mMeetingItemBinding.meetingsItemIbDelete.setOnClickListener(new View.OnClickListener() {
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
     * is a ViewHolder. This class is used to do the binding of the view
     */
    class MeetingHolder extends RecyclerView.ViewHolder{
        private MeetingItemBinding mMeetingItemBinding;

        public MeetingHolder(@NonNull MeetingItemBinding meetingItemBinding){
            super(meetingItemBinding.getRoot());
            mMeetingItemBinding = meetingItemBinding;
        }
    }

    /**
     * Interface to handle the clicks on a recycler view item
     */
    public interface MeetingClickListener{
        void meetingClick(long id);//this feature is not made yet, but could be used for opening a page containing the details of a meeting and being able to edit them
        void removeMeeting(long id);
    }
}
