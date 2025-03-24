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

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRequestDto requestDto) {
        return new ResponseEntity<>(userService.saveUser(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id
    ) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id,
            @RequestBody @Valid UserUpdateRequestDto requestDto
    ) {
        return new ResponseEntity<>(userService.updateUser(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id
    ) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
