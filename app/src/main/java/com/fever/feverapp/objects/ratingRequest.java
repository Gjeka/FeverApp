package com.fever.feverapp.objects;

import java.util.Date;

/* SongRating request Object.
 */
public class ratingRequest {
    public chatSongRating songRating;
    public User forUser;
    private Date created;
    private boolean completed;

    public ratingRequest(chatSongRating song, User forUser) {
        this.songRating = song;
        this.forUser = forUser;
        this.created = new Date();
        this.completed = false;
    }
    //********************************************************************************************//
    /*  Getters and Checkers.
     */
    // get the date the request was created.
    public Date getCreated() {
        return created;
    }
    // Check if the request has been completed.
    public boolean isCompleted() {
        return completed;
    }
    // Set if completed
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean equals(ratingRequest obj) {
        if( this.songRating.equals(obj.songRating) & this.forUser.equals(obj.forUser)){
            return true;
        }
        return false;
    }

    //********************************************************************************************//
    // complete this rating request.
    public boolean completeRequest(double rate){
        if(!this.isCompleted()){
            this.songRating.getInChat().getActiveRatingRequests().remove(this);
            this.setCompleted(true);
            this.songRating.addRating(rate,this.forUser);
            return true;
        }
        return false;
    }
}
