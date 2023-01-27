import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class Statistics {

    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    private HashSet<String> allUrl;

    private HashSet<String> notFoundUrl;

    HashMap<String, Integer> oS;

    HashMap<String, Integer> browser;

    private double amountVisit;

    private double errorResponseCount;

    HashMap<String, Integer> realUsers;

    public Statistics() {
        oS = new HashMap<>();
        browser = new HashMap<>();
        allUrl = new HashSet<>();
        notFoundUrl = new HashSet<>();
        realUsers = new HashMap<>();
        totalTraffic = 0;
        minTime = LocalDateTime.MAX;
        maxTime = LocalDateTime.MIN;
        amountVisit = 0;
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime logDateTime = logEntry.getRequestDateTime();
        if (logDateTime.isBefore(minTime)) minTime = logDateTime;
        if (logDateTime.isAfter(maxTime)) maxTime = logDateTime;
        if (logEntry.getResponseCode() == 200 && logEntry.getPath() != null) {
            allUrl.add(logEntry.getPath());
        }
        if (logEntry.getResponseCode() == 404 && logEntry.getPath() != null) {
            notFoundUrl.add(logEntry.getPath());
        }
        if (oS.containsKey(logEntry.getUserAgent().getOperatingSystem())) {
            oS.put(logEntry.getUserAgent().getOperatingSystem(), oS.get(logEntry.getUserAgent().getOperatingSystem()) + 1);
        } else {
            oS.put(logEntry.getUserAgent().getOperatingSystem(), 1);
        }
        if (browser.containsKey(logEntry.getUserAgent().getBrowser())) {
            browser.put(logEntry.getUserAgent().getBrowser(), browser.get(logEntry.getUserAgent().getBrowser()) + 1);
        } else {
            browser.put(logEntry.getUserAgent().getBrowser(), 1);
        }
        if (!logEntry.getUserAgent().isBot()) {
            amountVisit++;
        }
        if ((Integer.toString(logEntry.getResponseCode()).startsWith("4") || Integer.toString(logEntry.getResponseCode()).startsWith("5")) && logEntry.getPath() != null) {
            errorResponseCount++;
        }
        if (!logEntry.getUserAgent().isBot() && realUsers.containsKey(logEntry.getIpAddr()) && logEntry.getIpAddr() != null) {
            realUsers.put(logEntry.getIpAddr(), realUsers.get(logEntry.getIpAddr()) + 1);
        } else if (!logEntry.getUserAgent().isBot() && !realUsers.containsKey(logEntry.getIpAddr()) && logEntry.getIpAddr() != null) {
            realUsers.put(logEntry.getIpAddr(), 1);
        }
    }

    public long getTrafficRate() {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return totalTraffic / hours;
    }

    public HashSet<String> getAllUrl() {
        return allUrl;
    }

    public HashSet<String> getNotFoundUrl() {
        return notFoundUrl;
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

    public HashMap<String, Double> browserStat() {
        HashMap<String, Double> hashMap = new HashMap<>();
        double size = 0;
        for (Integer integer : browser.values()) {
            size += integer;
        }
        for (String string : browser.keySet()) {
            hashMap.put(string, browser.get(string) / size);
        }
        return hashMap;
    }

    public double statVisit() {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return hours / amountVisit;
    }

    public double statErrorCode() {
        long hours = ChronoUnit.HOURS.between(minTime, maxTime);
        return hours / errorResponseCount;
    }

    public double statVisitUniqueUser() {
        int mapSize = realUsers.size();
        int kolvoPosech = 0;
        for (int kolvo : realUsers.values()) {
            kolvoPosech += kolvo;
        }
        return kolvoPosech / mapSize;
    }

}