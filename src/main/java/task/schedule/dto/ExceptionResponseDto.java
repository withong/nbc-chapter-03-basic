package task.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import task.schedule.exception.ExceptionCode;

@Getter
@Builder
public class ExceptionResponseDto {

    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ExceptionResponseDto> dtoResponseEntity(ExceptionCode e) {
        return ResponseEntity.status(e.getStatus().value())
                .body(ExceptionResponseDto.builder()
                        .status(e.getStatus().value())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
