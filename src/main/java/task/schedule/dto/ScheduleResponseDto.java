package task.schedule.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String authorName;
    private LocalDate date;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
