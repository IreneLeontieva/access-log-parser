import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        String line;
        int amountLine = 0;
        int shortestLine = 2147483647;
        int longestLine = 0;

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


        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                int length = line.length();
                if (length > 1024) {
                    throw new RuntimeException("Строка больше 1024 симовлов");
                }
                amountLine++;
                if (length > longestLine) {
                    longestLine = length;
                }
                if (length < shortestLine) {
                    shortestLine = length;
                }
            }
        } catch (IOException|RuntimeException e) {
            e.printStackTrace();
        }

        System.out.printf("Количество строк в файле %d %n", amountLine);
        System.out.printf("У самой длинной строки длина %d %n", longestLine);
        System.out.printf("У самой короткой строки длина %d %n", shortestLine);
    }
}
