import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    private final String operatingSystem;
    private final String browser;

    private final String botName;
    private static Pattern patternAgent = null;

    private boolean isBot;

    public UserAgent(String userAgent) {
        String browser1 = "UNDEFINED";
        String operatingSystem1 = "UNDEFINED";
        String botName1 = "";
        if (userAgent == null) {
            browser = browser1;
            operatingSystem = operatingSystem1;
            botName = botName1;
            return;
        }

        if (userAgent.contains("bot")){
            isBot=true;
        }

        if (patternAgent == null) {
            patternAgent = Pattern.compile(Constant.REGEXAGENT);
        }
        Matcher matcher = patternAgent.matcher(userAgent);

        while (matcher.find()) {
            String a = matcher.group(1).trim();

            if (a.contains("Firefox")) browser1 = "Firefox";
            else if (a.contains("Chrome") && !a.contains("OPR") && !a.contains("Edg"))
                browser1 = "Chrome";
            else if (a.contains("Safari") && !a.contains("Chrome")) browser1 = "Safari";
            else if (a.contains("Opera") || a.contains("Presto") || a.contains("OPR"))
                browser1 = "Opera";
            else if (a.contains("Edg")) browser1 = "Edge";
            else if (a.contains("IEMobile")) browser1 = "Internet Explorer";

            String group2 = matcher.group(2);
            if (group2 == null) {
                continue;
            }
            String[] agent = matcher.group(2).split(";");
            if (agent.length < 2)
                continue;
            String[] agent2 = agent[1].split("/");
            if (agent2.length > 1) {
                a = agent2[0].trim();
                if (a != "") {
                    botName1 = a;
                }
            }

            if (arrContains(agent, "Windows")) operatingSystem1 = "Windows";
            else if (arrContains(agent, "Macintosh") &&
                    arrContains(agent, "Mac OS")) operatingSystem1 = "Mac OS";
            else if (arrContains(agent, "Linux")) {
                operatingSystem1 = arrContains(agent, "Android") ? "Android" : "Linux";
            } else if ((arrContains(agent, "iPad") ||
                    arrContains(agent, "iPhone")) &&
                    (arrContains(agent, "Mobile")))
                operatingSystem1 = "iOS";
        }
        browser = browser1;
        operatingSystem = operatingSystem1;
        botName = botName1;
    }

    static private boolean arrContains(String[] agent, CharSequence what) {
        for (int i = 0; i < agent.length; ++i) {
            if (agent[i].contains(what))
                return true;
        }
        return false;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }

    public String getBotName() {
        return botName;
    }

    public boolean isBot() {
        return isBot;
    }
}