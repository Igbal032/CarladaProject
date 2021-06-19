package az.code.carlada.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TimerInfoDTO {
    private int totalFireCount;
    private boolean runForever;
    private long repeatIntervalMS;
    private long initialOffsetMS;
    private String callbackData;
}
