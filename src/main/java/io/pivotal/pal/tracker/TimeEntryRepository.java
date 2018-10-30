package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry te);

    public TimeEntry update(long eq, TimeEntry any);

    public List<TimeEntry> list();

    public TimeEntry delete(long timeEntryId);

    public TimeEntry find(long timeEntryId);
}
