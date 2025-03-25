package task.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.entity.Schedule;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 일정 관련 DB 처리 (JDBC)
 */
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 일정 등록
     *
     * @param schedule 등록할 일정 정보
     * @return 생성된 일정 엔티티
     */
    @Override
    public Schedule saveSchedule(Schedule schedule) {
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

    /**
     * 일정 목록 조회
     *  - 사용자 테이블과 join 사용자 이름 포함 조회
     *
     * @param userId 사용자 식별자
     * @param start 수정일 시작 범위
     * @param end 수정일 끝 범위
     * @param limit 페이지 크기
     * @param offset 조회 시작 위치
     * @return ScheduleResponseDto 리스트 (사용자 이름 포함)
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesWithUserByUserId(Long userId, LocalDateTime start, LocalDateTime end, Integer limit, Integer offset) {
        StringBuilder sql = new StringBuilder(
                "select s.id, u.name as user_name, s.date, s.content, s.created_at, s.updated_at " +
                "from schedules s " +
                "join users u on u.id = s.user_id " +
                "where s.user_id = ?"
        );

        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        if (start != null && end != null) {
            sql.append(" and s.updated_at >= ? and s.updated_at < ?");
            parameters.add(start);
            parameters.add(end);
        }

        sql.append(" order by s.updated_at desc");
        sql.append(" limit ? offset ?");
        parameters.add(limit);
        parameters.add(offset);

        return jdbcTemplate.query(sql.toString(), responseDtoRowMapper(), parameters.toArray());
    }

    /**
     * 일정 조회 (1건)
     *  - 사용자 테이블과 join 사용자 이름 포함 조회
     *
     * @param id 일정 식별자
     * @return ScheduleResponseDto (사용자 이름 포함)
     */
    @Override
    public Optional<ScheduleResponseDto> findScheduleWithUserById(Long id) {
        String sql = "select s.id, u.name as user_name, s.date, s.content, s.created_at, s.updated_at " +
                "from schedules s join users u on u.id = s.user_id " +
                "where s.id = ?";

        List<ScheduleResponseDto> result = jdbcTemplate.query(sql, responseDtoRowMapper(), id);

        return result.stream().findAny();
    }

    /**
     * 일정 변경
     *
     * @param id 일정 식별자
     * @param date 일정 날짜
     * @param content 일정 내용
     * @return 변경된 row 개수
     */
    @Override
    public int updateSchedule(Long id, LocalDate date, String content) {
        String sql = "update schedules set date = ?, content = ? where id = ?";

        return jdbcTemplate.update(sql, date, content, id);
    }

    /**
     * 일정 삭제
     *
     * @param id 일정 식별자
     * @return 변경된 row 개수
     */
    @Override
    public int deleteSchedule(Long id) {
        String sql = "delete from schedules where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    /**
     * 특정 사용자의 일정 삭제
     *  - 사용자 삭제 시 호출됨
     *
     * @param userId 사용자 식별자
     */
    @Override
    public void deleteSchedulesByUserId(Long userId) {
        String sql = "delete from schedules where user_id = ?";

        jdbcTemplate.update(sql, userId);
    }

    /**
     * 일정 엔티티 조회
     *
     * @param id 일정 식별자
     * @return 조회된 일정 엔티티
     */
    @Override
    public Optional<Schedule> findScheduleEntityById(Long id) {
        String sql = "select * from schedules where id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);

        return result.stream().findAny();
    }

    /**
     * ScheduleResponseDto 매핑
     */
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

    /**
     * Schedule 엔티티 매핑
     */
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule(
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
