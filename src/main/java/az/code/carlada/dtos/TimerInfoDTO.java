package az.code.carlada.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TimerInfoDTO {
    private int totalFireCount;
    private boolean runForever;
    private long repeatIntervalMS;
    private long initialOffsetMS;
    private String callbackData;
}
