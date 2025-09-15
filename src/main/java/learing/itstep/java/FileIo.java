
package learing.itstep.java;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */

public class FileIo {
    private SimpleDateFormat dateFormatter = 
            new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public void demo() 
    {
    File current = new File(".");
    //У java є принципове відокремлення обекту File та реального файлу.
    //Створення new File не впливає на файлову систему , це лише створення java-обьекту
   
    //Робота з файловою системою
    if( current.exists())
   {
       System.out.println("Object exists");
       System.out.println(current.getAbsolutePath());
   }
    if( current.isFile())
   {
       System.out.println("Object is file");
   }
     if( current.isDirectory())
   {
       System.out.println("Object is directory:");
       File[] content = current.listFiles();
       for (File file : content)
       { 
           //System.out.print(file.lastModified() + " ");
           
           System.out.print(dateFormatter.format(
                   new Date(file.lastModified())
           )+ " ");
           
           if (file.isDirectory())
           {
                System.out.print("<DIR>");
           }
           else
           {
                System.out.print(file.length());
           }
            System.out.println(" "+file.getName());
            
       
       }
           // створення файлів та дерикторій 
           File sub = new File("./subdir");
         if( sub.exists())
         {
         sub.delete();
            System.out.println("Sub deleted");
         }
         else
         {
             sub.mkdir();// створення дерикторій 
            // sub.createNewFile();   // створення файлів
                System.out.println("Sub created");
         }
    }
     //---------------------------------------------------
     //Робота з вмістом файлів 
     //try() - аналог  using у C# (автоматичне вивільнення ресурсів)
     try( FileWriter fw = new FileWriter("test.txt");){
      fw.write("Line 1\nLine 2\nThe line 3");
      fw.flush();//примусовий запис буфера на носій
     }
     catch(IOException ex)
     {
        System.out.println(ex.getMessage());
     }
     
       System.out.println("-------------------------------------------");
     try( 
         FileReader fr = new FileReader("test.txt");
         Scanner scanner = new Scanner(fr);
        ){
     while (scanner.hasNextLine())
     {
      String line = scanner.nextLine();
      System.out.println(line);
     }
     }
     catch(IOException ex)
     {
        System.out.println(ex.getMessage());
     }
       catch(NullPointerException ex)
     {
        System.err.println("Resource not found");
     }
     
     
    //requireNonNull = ! C#
        System.out.println("-------------------------------------------");
   // Map<String,String> config = new HashMap<>();
        Map<String,String> config = new LinkedHashMap<>();
   try(    InputStream inputStream = Objects.requireNonNull(this
             .getClass()
             .getClassLoader()
             .getResourceAsStream("db.ini"));//getType reflection
       
              Scanner scanner = new Scanner(inputStream);
       )
   {
       int i = 1;
       while (scanner.hasNextLine())
     {
      String line = scanner.nextLine().trim();
      System.out.println(line);
      if(line.isEmpty() || line.startsWith(";"))
      {
        continue;
      }
      // разделитель "="
      int eqI = line.indexOf('=');
      if(eqI > 0)
      {
          String key = line.substring(0, eqI).trim();
          String value = line.substring(eqI+1).trim();
          config.put(key, value);
      }
    //   config.put(i+ "",line);
    //  i+=1;
     }
   }
        catch(IOException ex)
     {
        System.out.println(ex.getMessage());
     }
           System.out.println("-------------config------------------------------");
          for(Map.Entry<String,String> entry : config.entrySet())
          {
                 System.out.printf("%s: %s\n",
                         entry.getKey(),
                         entry.getValue());
          }
       }
}

//Робота з файлами та файлової системи
//Викорисання файлів для збереження даних
//створення переміщення видалення файлів та дерикторій 
// переміщення файлів разом зі збіркою доступ до ресурсів збірки

//Для того щоб ресурні файли стали частиною збірки їх необхідно покласти у спеціальну директорію src\main\resources
//(якщо її немаєЮ то слід створити саме з такою назвою)