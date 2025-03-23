package task.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleLv3 {

    private Long id;
    private User userId;
    private User userName;
    private LocalDate date;
    private String content;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleLv3(User userId, User userName, LocalDate date, String content, String password) {
        this.userId = userId;
        this.userName = userName;
        this.date = date;
        this.content = content;
        this.password = password;
    }

    public void update(User userName, LocalDate date, String content) {
        this.userName = userName;
        this.date = date;
        this.content = content;
    }

    public void updateAuthorName(User userName) {
        this.userName = userName;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
