/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package learing.itstep.java;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Иринка
 */
public class Java222 {
/*
Entry Point 
    @param args cmd-line arguments
*/
    public static void main(String[] args) {
        
    Threading threading = new Threading();
    
    threading.demo();
    
    
    
        //primitives - єдині value-типи , інші value-типи створити не можна
        // reference-типи дозволені
        
        byte b; //0 bit
        //b = 250;-помилка-всі типи знакові
        b = -125;// ,беззнакових варіацій не існує
        short s; // 16 bit
        int i; //32 bit
        long l; //64 bit
        
        float f = 0.001f; //32 bit
        double d = 1.5e-2; //64 bit
        
        boolean bl = true;
        char c = 'I'; // 16 bit ,UTF-16
        //робота з примітивами сильно обмежена через те що вони не є об'єкт
        //зокрема їх не можна вкладати у колекції
        //List <int> collection = new ArrayList<>();-gjvbkrf
        //для таких задач кожен тип маэ покажчиковий аналог 
       
        List <Integer> collection = new ArrayList<>();
        collection.add(10);
        collection.add(20);
        collection.add(30);
        collection.add(40);
        
        Integer x =10;
        Integer y = x;
        x=20;
        
        System.out.println(y);
        int[]arr = new int[10];
        for(int j=0;j<arr.length;j++)
        {
            arr[j] = j+1;
        }
        for(int k : collection)
        {
            System.out.printf("%d ",k);
        }
          System.out.println();
        
        for(int j=0;j<arr.length;j++)
        {
          if (j > 0) 
          {
            System.out.print(", ");
          }
           System.out.print(arr[j]); 
        }
         System.out.println();
         
         
          int[][] matrix = {{1, 2},{3, 4}};
          int[] vector = {5, 6};
          int[] res  = new int[2];
          
          for (int j = 0; j < matrix.length; j++) {
             res[j] = 0;
             for (int k = 0; k < vector.length; k++) {
             res[j] += matrix[j][k] * vector[k];
            }
        }
            System.out.println("Our result=");
            for (int j = 0; j < res.length; j++) {
                System.out.printf("|%d|\n", res[j]);
            }

          
          
        String str1="Hello";
        String str2 = new String("Hello");
        if (str1==str2)//оператор == - референс-порівняння
        {
            System.out.println("str1==str2");
        }
        else
        { 
            System.out.println("str1=/=str2");
        }
        if(str1.equals(str2))// якщо певні, що str1 !=null
        {
             System.out.println("str1 equals str2");
        }
        else 
        {
         System.out.println("str1 not equals str2");
        }
        if(Objects.equals(str1,str2))//якщо не певні що хтось !=null
        {
          System.out.println("str1 equals str2");
        }
        else
        {
          System.out.println("str1 not equals str2");
        }
         System.out.println("_________________");
        str2="Hello"; //string pooling
        //якщо компілятор зустрічає такий же рядок що був раніше то він не створює новий а бере старий
         if (str1==str2)//оператор == - референс-порівняння
        {
            System.out.println("str1==str2");
        }
        else
        { 
            System.out.println("str1=/=str2");
        }
    }
}

/*
Java
-Транслятор: компілює у проміжний код (для платформи JRE)
-Парадигма: ООП
- Поколыння мов: 4GL
- Вихідний код: файл.java , проміжний код : файл.class
- ! прив'язка до файлової системи 
- пакет (package) має теж ім'я що і шлях до нього (директорії)
- назва класу збігається з назвою файлу
-
*/