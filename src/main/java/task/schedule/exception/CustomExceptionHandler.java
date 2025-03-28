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

/**
 * 전역 예외 처리
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 사용자 정의 예외 처리
     *
     * @param e CustomException (ExceptionCode에 정의된 예외 정보)
     * @return 예외 응답 (ExceptionCode에 해당하는 예외 코드 + 메시지)
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ExceptionResponseDto.dtoResponseEntity(e.getExceptionCode());
    }

    /**
     * 유효성 검사 예외 처리
     * - updatedDate는 예외 코드 분기 처리 (INVALID_DATE_FORMAT 뱐환)
     *
     * @param e MethodArgumentNotValidException (바인딩 실패)
     * @return 예외 응답 (VALIDATION_FAILED 반환)
     */
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

    /**
     * JsonFormat 오류 처리
     *
     * @param e HttpMessageNotReadableException
     * @return 예외 응답 (INVALID_DATE_FORMAT 반환)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionResponseDto> handleJsonFormatException(HttpMessageNotReadableException e) {
        return ExceptionResponseDto.dtoResponseEntity(ExceptionCode.INVALID_DATE_FORMAT);
    }

}
