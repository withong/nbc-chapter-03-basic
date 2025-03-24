package task.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import task.schedule.dto.ExceptionResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ExceptionResponseDto.dtoResponseEntity(e.getExceptionCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        String field = e.getBindingResult().getFieldError().getField();

        if (field.equals("updatedDate")) {
            return ExceptionResponseDto.dtoResponseEntity(ExceptionCode.INVALID_DATE_FORMAT);
        }

        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        ExceptionCode code = ExceptionCode.VALIDATION_FAILED;

        return ResponseEntity.status(code.getStatus().value())
                .body(ExceptionResponseDto.builder()
                        .status(code.getStatus().value())
                        .code(code.getCode())
                        .message(defaultMessage)
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponseDto> handleJsonFormatException(HttpMessageNotReadableException e) {
        return ExceptionResponseDto.dtoResponseEntity(ExceptionCode.INVALID_DATE_FORMAT);
    }

}
