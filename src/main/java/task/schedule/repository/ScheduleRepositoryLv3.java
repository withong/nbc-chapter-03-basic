package task.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import task.schedule.dto.ScheduleResponseDto;
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
                .usingColumns("user_id", "date", "content", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", schedule.getUserId());
        parameters.put("date", schedule.getDate());
        parameters.put("content", schedule.getContent());
        parameters.put("password", schedule.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findScheduleEntityById(key.longValue()).orElseThrow();
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByUserId(Long userId, String updatedDate) {
        StringBuilder sql = new StringBuilder(
                "select s.id, u.name as user_name, s.date, s.content, s.created_at, s.updated_at " +
                "from schedules s " +
                "join users u on u.id = s.user_id " +
                "where s.user_id = ?"
        );

        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        if (updatedDate != null && !updatedDate.isBlank()) {
            sql.append(" and s.updated_at like ?");
            parameters.add(updatedDate + "%");
        }

        sql.append(" order by s.updated_at desc");

        return jdbcTemplate.query(sql.toString(), responseDtoRowMapper(), parameters.toArray());
    }

    @Override
    public Optional<ScheduleResponseDto> findScheduleWithUserById(Long id) {
        String sql = "select s.id, u.name as user_name, s.date, s.content, s.craeted_at, s.updated_at " +
                "from schedules s join users u on u.id = s.user_id " +
                "where s.id = ?";

        List<ScheduleResponseDto> result = jdbcTemplate.query(sql, responseDtoRowMapper(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, LocalDate date, String content) {
        String sql = "update schedules set date = ?, content = ? where id = ?";

        return jdbcTemplate.update(sql, date, content, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        String sql = "delete from schedules where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<ScheduleLv3> findScheduleEntityById(Long id) {
        String sql = "select * from schedules where id = ?";
        List<ScheduleLv3> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);

        return result.stream().findAny();
    }

    private RowMapper<ScheduleResponseDto> responseDtoRowMapper() {
        return (rs, rowNum) -> {
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(
                    rs.getLong("id"),
                    rs.getString("user_name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
            return scheduleResponseDto;
        };
    }

    private RowMapper<ScheduleLv3> scheduleRowMapper() {
        return (rs, rowNum) -> {
            ScheduleLv3 schedule = new ScheduleLv3(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
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
