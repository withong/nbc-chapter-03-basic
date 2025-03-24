package task.schedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT_FOUND_USER", "사용자가 존재하지 않습니다."),
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "NOT_FOUND_SCHEDULE", "일정이 존재하지 않습니다."),
    UPDATE_FAILED(HttpStatus.NOT_FOUND, "UPDATE_FAILED", "데이터 변경에 실패했습니다."),
    DELETE_FAILED(HttpStatus.NOT_FOUND, "DELETE_FAILED", "데이터 삭제에 실패했습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    NO_CHANGE_SCHEDULE(HttpStatus.NO_CONTENT, "NO_CHANGE_SCHEDULE", "변경된 내용이 없습니다."),
    RELOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "RELOAD_FAILED", "데이터를 불러오는 데 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
