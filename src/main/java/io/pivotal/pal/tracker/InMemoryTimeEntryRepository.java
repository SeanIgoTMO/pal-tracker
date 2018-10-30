package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private ArrayList<TimeEntry> entryList;
    private long nextID;

    public InMemoryTimeEntryRepository() {
        entryList = new ArrayList<TimeEntry>();
        nextID = 1;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        if(entryList == null) {
            return null;
        } else {
            for(int i = 0;i< entryList.size(); i++) {
                if(entryList.get(i).getId() == timeEntryId) {
                    return entryList.get(i);
                }
            }
            return null;
        }
    }

    @Override
    public List list() {
        if(entryList == null) {
            return null;
        } else {
            return entryList;
        }
    }

    @Override
    public TimeEntry create(TimeEntry te) {
        if(te.getId() == -1) {
            te.setId(nextID);
            nextID++;
        }
        entryList.add(te);
        return te;
    }

    @Override
    public TimeEntry update(long id, TimeEntry te) {
        if(entryList == null) {
            return null;
        } else {
            for(int i = 0;i< entryList.size(); i++) {
                if(entryList.get(i).getId() == id) {
                    te.setId(id);
                    entryList.set(i, te);
                    return entryList.get(i);
                }
            }
            return null;
        }
    }

    @Override
    public TimeEntry delete(long id) {
        if(entryList == null) {
            return new TimeEntry();
        } else {
            for(int i = 0;i< entryList.size(); i++) {
                if(entryList.get(i).getId() == id) {
                    return entryList.remove(i);
                }
            }
        }
        return new TimeEntry();
    }


}
