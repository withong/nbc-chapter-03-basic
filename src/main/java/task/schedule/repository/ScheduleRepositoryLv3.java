package task.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import task.schedule.entity.ScheduleLv3;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

@Repository
public class ScheduleRepositoryLv3 implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryLv3(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleLv3 saveSchedule(ScheduleLv3 schedule) {
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
    public List<ScheduleLv3> findSchedulesByCondition(Long userId, String authorName, String updatedDate) {
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
    public Optional<ScheduleLv3> findScheduleById(Long id) {
        String sql = "select * from schedules where id = ?";
        List<ScheduleLv3> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String authorName, LocalDate date, String content) {
        String sql = "update schedules set author_name = ?, date = ?, content = ? where id = ?";

        return jdbcTemplate.update(sql, authorName, date, content, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        String sql = "delete from schedules where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    private RowMapper<ScheduleLv3> scheduleRowMapper() {
        return (rs, rowNum) -> {
            ScheduleLv3 schedule = new ScheduleLv3(
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
