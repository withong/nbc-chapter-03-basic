package task.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleQueryRequestDto {

    @NotNull(message = "사용자 ID는 필수 값입니다.")
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;

    private Integer page = 1;
    private Integer size = 5;

    public Integer getPage() {
        return page != null ? page : 1;
    }

    public Integer getSize() {
        return size != null ? size : 5;
    }

}
