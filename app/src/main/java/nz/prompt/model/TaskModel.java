package nz.prompt.model;

import java.util.Date;

public class TaskModel {
    private int ID;
    private String Title;
    private Date Date;
    private boolean Status;

    public TaskModel(int ID, String title, Date date, boolean status) {
        this.ID = ID;
        Title = title;
        Date = date;
        Status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}