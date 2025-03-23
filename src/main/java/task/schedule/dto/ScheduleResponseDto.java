package task.schedule.dto;

import lombok.Getter;
import task.schedule.entity.ScheduleLv3;
import task.schedule.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String userName;
    private LocalDate date;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(ScheduleLv3 schedule) {
        this.id = schedule.getId();
        this.userName = schedule.getUserName().getName();
        this.date = schedule.getDate();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
