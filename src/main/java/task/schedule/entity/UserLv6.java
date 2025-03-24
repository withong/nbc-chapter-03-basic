package task.schedule.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserLv6 {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @Email
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserLv6(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
