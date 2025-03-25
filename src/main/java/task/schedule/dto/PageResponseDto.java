package task.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 페이지네이션 정보 DTO
 */
@Getter
@AllArgsConstructor
public class PageResponseDto {

    /**
     * 현재 페이지 번호
     */
    private int page;

    /**
     * 페이지 당 항목 개수
     */
    private int size;

    /**
     * 조회 시작 위치 = (page - 1) * size
     */
    private int offset;
}
