package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    @Override
    public int hashCode() {
        int hashValue = Objects.hash(id,projectId,userId,date.getDayOfMonth(),date.getMonthValue(),date.getYear(),hours);
        return hashValue;
    }

    @Override
    public boolean equals(Object obj) {
        TimeEntry te = (TimeEntry)obj;
        return(te.getId() == this.id &&
            te.getProjectId() == this.projectId &&
            te.getUserId() == this.userId &&
            te.getDate().getDayOfMonth() == this.date.getDayOfMonth() &&
            te.getDate().getMonthValue() == this.date.getMonthValue() &&
            te.getDate().getYear() == this.date.getYear() &&
            te.getHours() == this.hours
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }


    public TimeEntry() {
        this.projectId = -1;
        this.userId = -1;
        this.date = LocalDate.MIN;
        this.hours = -1;
        this.id = -1;
    }


    public TimeEntry(long timeEntryId, long projectId, long userId, LocalDate date, int i) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = i;
        this.id = timeEntryId;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int i) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = i;
        this.id = -1;
    }



}
