package task.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;

    private Long userId;
    private String authorName;
    private LocalDate date;
    private String content;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(Long userId, String authorName, LocalDate date, String content, String password) {
        this.userId = userId;
        this.authorName = authorName;
        this.date = date;
        this.content = content;
        this.password = password;
    }

    public void update(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }
}
