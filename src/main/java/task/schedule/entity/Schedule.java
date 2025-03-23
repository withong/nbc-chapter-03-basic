package task.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

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

    public void update(String authorName, LocalDate date, String content) {
        this.authorName = authorName;
        this.date = date;
        this.content = content;
    }

    public void updateAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
