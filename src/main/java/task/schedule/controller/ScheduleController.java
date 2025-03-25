package task.schedule.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import task.schedule.dto.ScheduleQueryRequestDto;
import task.schedule.dto.ScheduleRequestDto;
import task.schedule.dto.ScheduleResponseDto;
import task.schedule.service.ScheduleService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정 등록
     *
     * @param requestDto 등록할 일정 정보
     *         - [필수] 사용자 식별자, 일정 일자, 일정 내용, 일정 비밀번호
     * @return 생성된 일정 정보
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    /**
     * 일정 목록 조회
     *
     * @param requestDto 조회 조건
     *         - [필수] 사용자 식별자
     *         - [선택] 수정일, 페이지 번호, 페이지 당 일정 개수
     * @return 조회된 일정 목록
     *         - 수정일 기준 내림차순 정렬
     *         - 페이지네이션 적용 (기본값 page: 1, size: 5)
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findSchedulesWithUserByUserId(
            @Validated @ModelAttribute ScheduleQueryRequestDto requestDto
            ) {
        return new ResponseEntity<>(scheduleService.findSchedulesWithUserByUserId(
                requestDto.getUserId(), requestDto.getUpdatedDate(), requestDto.getPage(), requestDto.getSize()),
                HttpStatus.OK);
    }

    /**
     * 특정 일정 조회
     *
     * @param id 일정 식별자
     * @return 조회된 일정
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleWithUserById(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleWithUserById(id), HttpStatus.OK);
    }

    /**
     * 일정 변경
     *
     * @param id 일정 식별자
     * @param requestDto 변경할 일정 정보
     *         - [필수] 일정 비밀번호
     *         - [선택] 일정 일자, 일정 내용
     * @return 변경된 일정 정보
     *         - 변경된 내용이 없을 경우 204 No Content 응답
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id,
            @RequestBody @Valid ScheduleRequestDto requestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, requestDto), HttpStatus.OK);
    }

    /**
     * 일정 삭제
     *
     * @param id 일정 식별자
     * @param requestDto 삭제할 일정 정보
     *         - [필수] 비밀번호
     * @return 성공 시 204 No Content 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(
            @NotNull(message = "ID는 필수 값입니다.") @PathVariable("id") Long id,
            @RequestBody @Valid ScheduleRequestDto requestDto
    ) {
        scheduleService.deleteSchedule(id, requestDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
