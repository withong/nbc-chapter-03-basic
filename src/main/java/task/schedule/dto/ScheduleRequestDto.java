package task.schedule.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDto {

    private Long userId;
    private LocalDate date;
    private String content;
    private String password;
}
