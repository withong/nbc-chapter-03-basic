package task.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import task.schedule.entity.Schedule;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ScheduleRepositoryLv1 implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryLv1(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id")
                .usingColumns("user_id", "author_name", "date", "content", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUserId());
        parameters.put("author_name", schedule.getAuthorName());
        parameters.put("date", schedule.getDate());
        parameters.put("content", schedule.getContent());
        parameters.put("password", schedule.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findScheduleById(key.longValue()).orElseThrow();
    }

    @Override
    public List<Schedule> findSchedules(String authorName, String updatedDate) {
        StringBuilder sql = new StringBuilder("select * from schedules where author_name = ?");

        List<Object> parameters = new ArrayList<>();
        parameters.add(authorName);

        if (updatedDate != null && !updatedDate.isBlank()) {
            sql.append(" and updated_at like ?");
            parameters.add(updatedDate + "%");
        }

        sql.append(" order by updated_at desc");

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), parameters.toArray());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        String sql = "select * from schedules where id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);

        return result.stream().findAny();
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getString("author_name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
            return schedule;
        };
    }
}
