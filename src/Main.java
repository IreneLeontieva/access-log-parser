import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите текст и нажмите <Enter>: ");
        String text = new Scanner(System.in).nextLine();
        System.out.println("Длина текста " + text.length());
        int count = 0;

        while (true) {
            System.out.println("Введите путь к файлу");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!fileExists) {
                System.out.println("Нет такого файла");
                continue;
            }
            if (isDirectory) {
                System.out.println("Указан путь к директории, а не файлу");
                continue;
            }
            count++;
            System.out.println(String.format("Путь указан верно. Это файл номер %d ", count));
        }
    }
}
