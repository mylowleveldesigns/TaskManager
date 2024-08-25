package taskutilities;

import model.TimePeriod;

import java.time.LocalDateTime;

public class Helper {
    public boolean isTimeWithinPeriod(LocalDateTime time, TimePeriod timePeriod) {
        LocalDateTime startTime = timePeriod.getStartTime();
        LocalDateTime endTime = timePeriod.getEndTime();
        return (time.isEqual(startTime) || time.isAfter(startTime)) && (time.isEqual(endTime) || time.isBefore(endTime));
    }
}
