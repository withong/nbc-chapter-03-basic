package task.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 일정 등록/수정 요청 DTO
 */
@Getter
public class ScheduleRequestDto {

    /**
     * 사용자 식별자
     */
    private Long userId;

    /**
     * 일정 날짜 (yyyy-MM-dd 형식)
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * 일정 내용 (최대 200자)
     */
    @Size(max = 200, message = "200자를 초과할 수 없습니다.")
    private String content;

    /**
     * 일정 비밀번호 (필수)
     */
    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;
}
