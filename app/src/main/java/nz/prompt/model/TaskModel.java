package nz.prompt.model;

import java.util.Date;

public class TaskModel {
    private int ID;
    private String Title;
    private String Description;
    private String Location;
    private Date StartDate;
    private Date EndDate;
    private boolean Status;

    public TaskModel(int id, String title, String description, String location, Date startDate, Date endDate, boolean status) {
        setID(id);
        setTitle(title);
        setDescription(description);
        setLocation(location);
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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