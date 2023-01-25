import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        int yandexBot = 0, googleBot = 0;
        String line;

        System.out.println("Введите путь к файлу");
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();
        if (!fileExists) {
            System.out.println("Нет такого файла");
        }
        if (isDirectory) {
            System.out.println("Указан путь к директории, а не файлу");
        }
        count++;
        System.out.printf("Путь указан верно. Это файл номер %d %n", count);

        ArrayList<LogEntry> logEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Statistics statistics = new Statistics();
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                if (length > 1024) {
                    throw new RuntimeException("Строка больше 1024 симовлов");
                }
                LogEntry logEntry = new LogEntry(line);
                logEntries.add(logEntry);
                statistics.addEntry(logEntry);

                if (logEntry.getUserAgent().getBotName().equalsIgnoreCase("YandexBot"))
                    yandexBot++;
                if (logEntry.getUserAgent().getBotName().equalsIgnoreCase("Googlebot"))
                    googleBot++;

            }

            System.out.println(String.format("Доля яндексБот %f", (double) yandexBot / (double) logEntries.size()));
            System.out.println(String.format("Доля гуглБот %f", (double) googleBot / (double) logEntries.size()));
            System.out.println("Средний объём трафика сайта за час: " + statistics.getTrafficRate());
            System.out.println(statistics.osStat());
            System.out.println(statistics.getAllUrl());
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
        }
    }
}
