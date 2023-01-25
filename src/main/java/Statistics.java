import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private HashSet<String> allUrl;

    HashMap<String, Integer> oS;

    public Statistics() {
        oS = new HashMap<>();
        allUrl = new HashSet<>();
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime logDateTime = logEntry.getRequestDateTime();
        if (logDateTime.isBefore(minTime)) minTime = logDateTime;
        if (logDateTime.isAfter(maxTime)) maxTime = logDateTime;
        if (logEntry.getResponseCode() == 200 && logEntry.getPath() != null) {
            allUrl.add(logEntry.getPath());
        }
        if (oS.containsKey(logEntry.getUserAgent().getOperatingSystem())) {
            oS.put(logEntry.getUserAgent().getOperatingSystem(), oS.get(logEntry.getUserAgent().getOperatingSystem()) + 1);
        } else {
            oS.put(logEntry.getUserAgent().getOperatingSystem(), 1);
        }
    }

    public long getTrafficRate() {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return totalTraffic / hours;
    }

    public HashSet<String> getAllUrl() {
        return allUrl;
    }

    public HashMap<String, Double> osStat() {
        HashMap<String, Double> hashMap = new HashMap<>();
        double size = 0;
        for (Integer integer : oS.values()) {
            size += integer;
        }
        for (String string : oS.keySet()) {
            hashMap.put(string, oS.get(string) / size);
        }
        return hashMap;
    }

}