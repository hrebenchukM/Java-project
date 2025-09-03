/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learing.itstep.java;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Иринка
 */
public class ChainingHW {
         private long t;
      private final Random random = new Random();
   public void demo()
    {
           ExecutorService threadPool = Executors.newFixedThreadPool(2);
           t = System.nanoTime();
       
         Future<?> task =  CompletableFuture
                 .supplyAsync(randomArray, threadPool)
                 .thenApply(arrayToPoint)
                 .thenApply(pointQuadrant)
                 .thenAccept(printer);
           
            try {
              threadPool.shutdown();//не зупиняє задачі а лише перстає приймати
              threadPool.awaitTermination(3, TimeUnit.SECONDS);//блокує всі задачі поки вони не завершаться за 3 сек або сам потік не завершиться
              threadPool.shutdownNow(); //зупиняє всі задачі
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    private final Supplier<int[]>randomArray = () ->
    {
    printWithTime("Supplier randomArray start");
    sleep();
    int[] result = {random.nextInt(-100, 101), random.nextInt(-100, 101)};
    printWithTime("Supplier randomArray finish WITH RESULT:  [" + result[0] + "," + result[1] + "]");
    return result;
    };
    
  
    private final Function<int[], Point> arrayToPoint  = arr  ->
    {
      printWithTime(" arrayToPoint  start WITH INPUT [" + arr[0] + "," + arr[1] + "]");
    sleep();
     Point p = new Point(arr[0], arr[1]);
     printWithTime(" arrayToPoint  finish WITH RESULT:"+ p);
     return  p;
   
    };
    
    
    private final Function<Point,String> pointQuadrant  = point  ->
    {
      printWithTime("pointQuadrant  start WITH INPUT :"+ point );
    sleep();
     String result ;
     if (point.x > 0 && point.y > 0) result = "Point " + point + " is in  I";
        else if (point.x < 0 && point.y > 0) result = "Point " + point + " is in  II";
        else if (point.x < 0 && point.y < 0) result = "Point " + point + " is in  III";
        else if (point.x > 0 && point.y < 0) result = "Point " + point + " is in  IV";
        else if (point.x == 0 && point.y != 0) result = "Point " + point + " is on Y ";
        else if (point.y == 0 && point.x != 0) result = "Point " + point + " is on X ";
        else result = "Point " + point + " is at the origin";
     printWithTime("pointQuadrant  finish WITH RESULT:"+ result);
     return  result;
   
    };
    
    private final Consumer <String> printer = str -> {
       printWithTime("printer start");
    sleep();
    printWithTime("printer finish with result " + str);
    };
    private void printWithTime(String message) {

        System.out.printf("%.1f ms: %s\n", time(), message);

    }
 
    private double time()
    {
        return (System.nanoTime() - t) / 1e6;
    }
    private void sleep()
    {
      try {
            Thread.sleep(random.nextInt(100, 500)); 
           } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
    }

   
}
