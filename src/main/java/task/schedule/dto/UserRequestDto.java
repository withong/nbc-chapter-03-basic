package task.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;

    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
