package task.schedule.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

/**
 * 사용자 정보 변경 요청 DTO
 */
@Getter
public class UserUpdateRequestDto {

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 이메일 (이메일 형식)
     */
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
}
