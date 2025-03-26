package task.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 일정 목록 조회 요청 DTO
 */
@Setter
@Getter
@NoArgsConstructor
public class ScheduleQueryRequestDto {

    /**
     * 사용자 식별자 (필수)
     */
    @NotNull(message = "사용자 ID는 필수 값입니다.")
    private Long userId;

    /**
     * 일정 수정일 (선택, yyyy-MM-dd 형식)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;

    /**
     * 페이지 번호 (기본값: 1)
     */
    private Integer page = 1;

    /**
     * 페이지 당 항목 개수 (기본값: 5)
     */
    private Integer size = 5;

    public Integer getPage() {
        return page != null ? page : 1;
    }

    public Integer getSize() {
        return size != null ? size : 5;
    }

}
