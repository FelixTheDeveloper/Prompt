package nz.prompt.model;

import java.util.Date;

public class TaskModel {
    private int ID;
    private String Title;
    private String Description;
    private Date StartDate;
    private Date EndDate;
    private boolean Status;

    public TaskModel(String title, String description, Date startDate, Date endDate, boolean status) {
        setID(ID);
        setTitle(title);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setStatus(status);
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public boolean isStatus() {
        return Status;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}