import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime logDateTime = logEntry.getRequestDateTime();
        if (logDateTime.isBefore(minTime)) minTime = logDateTime;
        if (logDateTime.isAfter(maxTime)) maxTime = logDateTime;
    }

    public long getTrafficRate() {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return totalTraffic / hours;
    }
}