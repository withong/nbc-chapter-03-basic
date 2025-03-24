package task.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageResponseDto {

    private int page;
    private int size;
    private int offset;
}
