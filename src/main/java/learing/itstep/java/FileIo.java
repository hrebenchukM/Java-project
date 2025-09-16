// package – это способ группировать классы в Java, примерно как namespace в C#
package learing.itstep.java;

// Импорты – подключение библиотек/классов
import java.io.File;           // Работа с файловой системой
import java.io.FileReader;     // Чтение текстовых файлов
import java.io.FileWriter;     // Запись в текстовые файлы
import java.io.IOException;    // Исключения ввода-вывода
import java.io.InputStream;    // Поток данных (например, из ресурсов)
import java.text.SimpleDateFormat; // Форматирование даты
import java.util.Date;         // Класс для работы с датами
import java.util.HashMap;      // Неупорядоченный словарь (C# Dictionary)
import java.util.LinkedHashMap;// Словарь с сохранением порядка добавления элементов
import java.util.Map;          // Интерфейс словаря
import java.util.Objects;      // Статические утилиты, аналог System.Object в C# но с доп. методами
import java.util.Scanner;      // Удобное чтение строк из файла или консоли

/**
 * Основной класс для демонстрации работы с файлами
 * @author Lenovo
 */
public class FileIo {

    // Экземпляр SimpleDateFormat для удобного форматирования дат
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    // Метод demo – демонстрация всех операций
    public void demo() 
    {
        // Создание объекта File для текущей директории (".") 
        // В Java объект File и реальный файл/директория не одно и то же, аналог FileInfo в C#
        File current = new File(".");

        // Проверка существования объекта
        if(current.exists()) {
            System.out.println("Object exists");
            System.out.println(current.getAbsolutePath()); // Путь к директории
        }

        // Проверка, является ли объект файлом
        if(current.isFile()) {
            System.out.println("Object is file");
        }

        // Проверка, является ли объект директорией
        if(current.isDirectory()) {
            System.out.println("Object is directory:");
            File[] content = current.listFiles(); // Получение списка файлов и папок
            for (File file : content) { 
                // file.lastModified() возвращает время последнего изменения в миллисекундах
                System.out.print(dateFormatter.format(new Date(file.lastModified()))+ " ");
                
                if (file.isDirectory()) {
                    System.out.print("<DIR>");
                } else {
                    System.out.print(file.length()); // Размер файла в байтах
                }
                System.out.println(" "+file.getName());
            }

            // Создание/удаление подпапки (аналог Directory.CreateDirectory/Delete)
            File sub = new File("./subdir");
            if(sub.exists()) {
                sub.delete();
                System.out.println("Sub deleted");
            } else {
                sub.mkdir(); // mkdir создаёт папку, createNewFile() создаёт файл
                System.out.println("Sub created");
            }
        }

        //---------------------------------------------------
        // Работа с содержимым файлов
        // try-with-resources аналог using в C# (автоматическое закрытие ресурсов)
        try(FileWriter fw = new FileWriter("test.txt")) {
            fw.write("Line 1\nLine 2\nThe line 3");
            fw.flush(); // Принудительная запись буфера на диск
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------------------");

        // Чтение файла построчно с помощью Scanner
        try(FileReader fr = new FileReader("test.txt");
            Scanner scanner = new Scanner(fr)) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        } catch(NullPointerException ex) {
            System.err.println("Resource not found"); // Аналог ArgumentNullException в C#
        }

        // Работа с ресурсами сборки
        System.out.println("-------------------------------------------");
        Map<String,String> config = new LinkedHashMap<>(); // Порядок добавления сохраняется

        try(InputStream inputStream = Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream("db.ini")); // Получение ресурса из сборки
            Scanner scanner = new Scanner(inputStream)) 
        {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                System.out.println(line);

                if(line.isEmpty() || line.startsWith(";")) {
                    continue; // Пропуск пустых строк и комментариев
                }

                // Разделитель "="
                int eqI = line.indexOf('=');
                if(eqI > 0) {
                    String key = line.substring(0, eqI).trim();
                    String value = line.substring(eqI+1).trim();
                    config.put(key, value);
                }
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------config------------------------------");
        for(Map.Entry<String,String> entry : config.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }
}

// Основные моменты для C# разработчика:
// 1. Java File – объект, который представляет путь, но не обязательно существующий файл.
// 2. try-with-resources = using, автоматически закрывает потоки.
// 3. Scanner используется для чтения строк, аналог StreamReader + ReadLine.
// 4. LinkedHashMap – словарь, сохраняющий порядок добавления (HashMap = обычный словарь, порядок не гарантируется).
// 5. getResourceAsStream – чтение файлов из ресурсов сборки, аналог Assembly.GetManifestResourceStream.
