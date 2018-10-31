package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TimeEntryController {

    private CounterService counter;
    private GaugeService gauge;
    private TimeEntryRepository repository;

    /*
    public TimeEntryController() {
        repository = new InMemoryTimeEntryRepository();
    }
    */

    public TimeEntryController(TimeEntryRepository repo, CounterService counter,
                               GaugeService gauge) {

        repository = repo;
        this.counter = counter;
        this.gauge = gauge;
    }


    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry te) {
        TimeEntry newTE = repository.create(te);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", repository.list().size());
        return new ResponseEntity<TimeEntry>(newTE,HttpStatus.CREATED);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id,@RequestBody TimeEntry te) {
        TimeEntry te2 = repository.update(id.longValue(),te);
        if (te2 != null) {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<TimeEntry>(te2, HttpStatus.OK);
        }
        return new ResponseEntity<TimeEntry>(te, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        //public ResponseEntity(T body, HttpStatus status)
        if(repository == null) {
            return new ResponseEntity<List<TimeEntry>>(new ArrayList<TimeEntry>(),HttpStatus.OK); //BAD_REQUEST);
        } else{
            counter.increment("TimeEntry.listed");
            return new ResponseEntity<List<TimeEntry>>(repository.list(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", repository.list().size());
        return new ResponseEntity<TimeEntry>(repository.delete(id.longValue()),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        if(repository == null) {
            return new ResponseEntity<TimeEntry>(new TimeEntry(-1,-1,null,-1),HttpStatus.BAD_REQUEST);
        } else{
            TimeEntry entry = repository.find(id);
            if(entry == null) {
                return new ResponseEntity<TimeEntry>(new TimeEntry(-1,-1,null,-1),HttpStatus.NOT_FOUND);
            } else {
                counter.increment("TimeEntry.read");
                return new ResponseEntity<TimeEntry>(entry, HttpStatus.OK);
            }
        }
    }

}
