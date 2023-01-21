public class Constant {

    public static final String REGEXLINE = "(?<remoteaddr>(?:^|\\b(?<!\\.))(?:1?\\d\\d?|2[0-4]\\d|25[0-5])(?:\\.(?:1?\\d\\d?|2[0-4]\\d|25[0-5])){3}(?=$|[^\\w.]))\\s-\\s(?<remoteusr>-|[a-z_][a-z0-9_]{0,30})\\s\\[(?<dateandtime>\\d{2}\\/[\\w]{3}\\/\\d{4}:\\d{2}:\\d{2}:\\d{2}\\s[\\+|\\-]\\d{4})\\]\\s(?<request>\\\"(?<reqmethod>[\\w]+)\\s(?<requri>\\/[^\\s]*)\\s(?<httpver>HTTP/\\d\\.\\d)\\\")\\s(?<status>\\d{3})\\s(?<bodybytesent>\\d+)\\s\\\"(?<httpreferer>[^\\s]+)\\\"\\s\\\"(?<useragent>[^\\\"]+)\\\"";
    public static final String REGEXAGENT = "([A-Za-z\\s]+)\\/[\\d\\.]+(\\s\\(.+?\\)){0,1}";
}
