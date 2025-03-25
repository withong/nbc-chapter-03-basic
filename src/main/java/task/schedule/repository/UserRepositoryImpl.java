package task.schedule.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import task.schedule.entity.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 사용자 관련 DB 처리 (JDBC)
 */
@Repository
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 사용자 등록
     *
     * @param user 등록할 사용자 정보
     * @return 생성된 사용자 엔티티
     */
    @Override
    public User saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("users").usingGeneratedKeyColumns("id")
                .usingColumns("name", "email");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findUserById(key.longValue()).orElseThrow();
    }

    /**
     * 사용자 조회
     *
     * @param id 사용자 식별자
     * @return 조회된 사용자 엔티티
     */
    @Override
    public Optional<User> findUserById(Long id) {
        String sql = "select * from users where id = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), id);

        return result.stream().findAny();
    }

    /**
     * 사용자 정보 변경
     *
     * @param id 사용자 식별자
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @return 변경된 row 개수
     */
    @Override
    public int updateUser(Long id, String name, String email) {
        String sql = "update users set name = ?, email = ? where id = ?";

        return jdbcTemplate.update(sql, name, email, id);
    }

    /**
     * 사용자 삭제
     *
     * @param id 사용자 식별자
     * @return 변경된 row 개수
     */
    @Override
    public int deleteUser(Long id) {
        String sql = "delete from users where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    /**
     * User 매핑
     */
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
            return user;
        };
    }
}
