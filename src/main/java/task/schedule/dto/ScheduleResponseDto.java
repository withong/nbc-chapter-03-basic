package task.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import task.schedule.entity.ScheduleLv3;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String userName;
    private LocalDate date;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(ScheduleLv3 schedule, String userName) {
        this.id = schedule.getId();
        this.userName = userName;
        this.date = schedule.getDate();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
