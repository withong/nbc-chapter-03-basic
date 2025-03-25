package task.schedule.dto;

import lombok.Getter;
import task.schedule.entity.User;

import java.time.LocalDateTime;

/**
 * 사용자 응답 DTO
 */
@Getter
public class UserResponseDto {

    /**
     * 사용자 식별자
     */
    private Long id;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 사용자 정보 등록 일시
     */
    private LocalDateTime createdAt;

    /**
     * 사용자 정보 수정 일시 (마지막 수정 시점)
     */
    private LocalDateTime updatedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
