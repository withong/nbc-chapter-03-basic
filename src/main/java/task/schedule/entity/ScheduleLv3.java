package task.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleLv3 {

    private Long id;
    private Long userId;
    private LocalDate date;
    private String content;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleLv3(Long userId, LocalDate date, String content, String password) {
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.password = password;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
