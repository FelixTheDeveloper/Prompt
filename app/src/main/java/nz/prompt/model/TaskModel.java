package nz.prompt.model;

import java.util.Calendar;

public class TaskModel {
    private int ID;
    private String Title;
    private String Description;
    private double Location_LAT;
    private double Location_LNG;
    private Calendar StartDate;
    private Calendar EndDate;
    private boolean Status;

    public TaskModel(int ID, String title, String description, double location_LAT, double location_LNG, Calendar startDate, Calendar endDate, boolean status) {
        this.ID = ID;
        Title = title;
        Description = description;
        Location_LAT = location_LAT;
        Location_LNG = location_LNG;
        StartDate = startDate;
        EndDate = endDate;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getLocation_LAT() {
        return Location_LAT;
    }

    public void setLocation_LAT(double location_LAT) {
        Location_LAT = location_LAT;
    }

    public double getLocation_LNG() {
        return Location_LNG;
    }

    public void setLocation_LNG(double location_LNG) {
        Location_LNG = location_LNG;
    }

    public Calendar getStartDate() {
        return StartDate;
    }

    public void setStartDate(Calendar startDate) {
        StartDate = startDate;
    }

    public boolean isStatus() {
        return Status;
    }

    public Calendar getEndDate() {
        return EndDate;
    }

    public void setEndDate(Calendar endDate) {
        EndDate = endDate;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    @Override
    public boolean equals(Object target)
    {
        return this.getID() == ((TaskModel)target).getID();
    }

    @Override
    public int hashCode()
    {
        return this.getID();
    }
}