package task.schedule.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.schedule.dto.UserRequestDto;
import task.schedule.dto.UserResponseDto;
import task.schedule.dto.UserUpdateRequestDto;
import task.schedule.service.UserService;

@RestController
@Validated
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 등록
     *
     * @param requestDto 등록할 사용자 정보
     *         - [필수] 사용자 이름, 사용자 이메일
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.CREATED);
    }

    /**
     * 특정 사용자 조회
     *
     * @param id 사용자 식별자
     * @return 조회된 사용자 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @NotNull @PathVariable("id") Long id
    ) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    /**
     * 사용자 정보 변경
     *
     * @param id 사용자 식별자
     * @param requestDto 변경할 사용자 정보
     *         - [선택] 사용자 이름, 사용자 이메일
     * @return 변경된 사용자 정보
     *         - 변경된 내용이 없을 경우 204 No Content 응답
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @NotNull @PathVariable("id") Long id,
            @RequestBody @Valid UserUpdateRequestDto requestDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id, requestDto), HttpStatus.OK);
    }

    /**
     * 사용자 삭제
     *
     * @param id 사용자 식별자
     * @return 성공 시 204 No Content 응답
     *         - 연관된 일정도 함께 삭제됨
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(
            @NotNull @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
