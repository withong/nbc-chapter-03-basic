package task.schedule.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleLv6 {

    private Long id;

    @NotNull
    private Long userId;

    private LocalDate date;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    @NotBlank
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleLv6(Long userId, LocalDate date, String content, String password) {
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
