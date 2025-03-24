package task.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDto {

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Size(max = 200, message = "200자를 초과할 수 없습니다.")
    private String content;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;
}
