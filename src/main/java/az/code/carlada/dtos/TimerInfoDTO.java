package az.code.carlada.dtos;

import az.code.carlada.models.Listing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TimerInfoDTO <T>{
    private int totalFireCount;
    private boolean runForever;
    private long repeatIntervalMS;
    private long initialOffsetMS;
    private T callbackData;
}
