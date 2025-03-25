package task.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import task.schedule.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일정 응답 DTO
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    /**
     * 일정 식별자
     */
    private Long id;

    /**
     * 사용자 이름
     */
    private String userName;

    /**
     * 일정 날짜 (yyyy-MM-dd)
     */
    private LocalDate date;

    /**
     * 일정 내용
     */
    private String content;

    /**
     * 일정 등록 일시
     */
    private LocalDateTime createdAt;

    /**
     * 일정 수정 일시 (마지막 수정 시점)
     */
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule, String userName) {
        this.id = schedule.getId();
        this.userName = userName;
        this.date = schedule.getDate();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
