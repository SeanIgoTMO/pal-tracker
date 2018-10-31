package io.pivotal.pal.tracker;


import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate JDBC_template;

    private class TimeEntryMapper implements RowMapper<TimeEntry> {

        public TimeEntry mapRow(ResultSet resultSet, int i) throws SQLException {

            try {
                TimeEntry te = new TimeEntry();
                te.setId(resultSet.getLong("id"));
                te.setProjectId(resultSet.getLong("project_id"));
                te.setUserId(resultSet.getLong("user_id"));
                te.setHours(resultSet.getInt("hours"));
                te.setDate(resultSet.getDate("date").toLocalDate());
                return te;
            } catch (EmptyResultDataAccessException e){
                return null;
            }

        }
    }

    public JdbcTimeEntryRepository(DataSource ds) {
        this.JDBC_template = new JdbcTemplate(ds);
    }

    @Override
    public TimeEntry create(TimeEntry te) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        JDBC_template.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, te.getProjectId());
            statement.setLong(2, te.getUserId());
            statement.setDate(3, Date.valueOf(te.getDate()));
            statement.setInt(4, te.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry te) {
        te.setId(timeEntryId);
        JDBC_template.update(
                "update time_entries set id = ?, project_id = ?, user_id = ?, hours = ?, date = ? where id = ?",
                timeEntryId, te.getProjectId(), te.getUserId(), te.getHours(), te.getDate(), timeEntryId);
        return te;
    }

    @Override
    public List<TimeEntry> list() {
        String sql = "SELECT * FROM time_entries";

        List<TimeEntry> timeEntries  = JDBC_template.query(sql,
                new BeanPropertyRowMapper(TimeEntry.class));

        return timeEntries;
    }

    @Override
    public TimeEntry delete(long timeEntryId) {
        TimeEntry te = find(timeEntryId);
        if(te == null) {
            return null;
        }

        JDBC_template.update("DELETE FROM time_entries WHERE id = ?", timeEntryId);

        return te;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        String statement = "SELECT * from time_entries WHERE id = ?";
        try {
            TimeEntry te = JDBC_template.queryForObject(statement, new Object[] { timeEntryId }, new TimeEntryMapper());
            return te;
        } catch (EmptyResultDataAccessException de){
            return null;
        }
    }
}
