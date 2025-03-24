package task.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.schedule.dto.ExceptionResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ExceptionResponseDto.dtoResponseEntity(e.getExceptionCode());
    }
}
