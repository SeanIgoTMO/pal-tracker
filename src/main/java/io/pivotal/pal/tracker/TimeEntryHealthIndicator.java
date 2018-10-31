package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private TimeEntryRepository repository;

    public TimeEntryHealthIndicator(TimeEntryRepository repo) {
        repository = repo;
    }

    public Health health() {
        List<TimeEntry> repolist = repository.list();
        if(repolist == null || repolist.size() >= 5) {
            return Health.down().build();
        }
        return Health.up().build();
    }

}
