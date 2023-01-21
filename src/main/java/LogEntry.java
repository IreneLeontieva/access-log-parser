import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {

    public enum HttpMethod {
        GET, POST, PUT, DELETE
    }

    private final String ipAddr;
    private final LocalDateTime requestDateTime;
    private final HttpMethod httpMethod;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final String userAgent;

    private final String user;

    private static Pattern patternLine = null;

    private static Pattern patternAgent = null;

    public LogEntry(String logLine) {
        if (patternLine == null) {
            patternLine = Pattern.compile(Constant.REGEXLINE);
        }
        Matcher matcher = patternLine.matcher(logLine);
        boolean found = matcher.matches();
        if (!found) {
            throw new RuntimeException("ФУ! это не логи");
        }

        ipAddr = matcher.group("remoteaddr");
        user = matcher.group("remoteusr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);
        requestDateTime = LocalDateTime.parse(matcher.group("dateandtime"), formatter);
        httpMethod = HttpMethod.valueOf(matcher.group("reqmethod"));
        path = matcher.group("requri");
        responseCode = Integer.parseInt(matcher.group("status"));
        responseSize = Integer.parseInt(matcher.group("bodybytesent"));
        referer = matcher.group("httpreferer");
        userAgent = getUserAgentFromLogLine(matcher.group("useragent"));

    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public String getUser() {
        return user;
    }

    public String getUserAgent() {
        return userAgent;
    }

    private String getUserAgentFromLogLine(String userAgent) {
        if (userAgent == null) {
            return "";
        }

        if (patternAgent == null) {
            patternAgent = Pattern.compile(Constant.REGEXAGENT);
        }
        Matcher matcher = patternAgent.matcher(userAgent);

        String result = "";

        int count = 0;
        while (matcher.find()) {
            ++count;
            String group2 = matcher.group(2);
            if (group2 == null) {
                continue;
            }
            String[] agent = matcher.group(2).split(";");
            if (agent.length < 2)
                continue;
            String[] agent2 = agent[1].split("/");
            if (agent2.length < 2)
                continue;
            if (agent2[0].trim() != "") {
                result = agent2[0].trim();
            }

        }
        return result;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddr='" + ipAddr + '\'' +
                ", requestDateTime=" + requestDateTime +
                ", httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}