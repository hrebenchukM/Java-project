/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learing.itstep.java;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
public class Chaining2 {
      private long t;
      private final Random random = new Random();
    public void demo()
    {
           ExecutorService threadPool = Executors.newFixedThreadPool(2);
           t = System.nanoTime();
       
         Future<?> task =  CompletableFuture
                 .supplyAsync(randomInt, threadPool)
                 .thenApply(signAlyzer)
                 .thenAccept(printer);
           
            try {
              threadPool.shutdown();//не зупиняє задачі а лише перстає приймати
              threadPool.awaitTermination(3, TimeUnit.SECONDS);//блокує всі задачі поки вони не завершаться за 3 сек або сам потік не завершиться
              threadPool.shutdownNow(); //зупиняє всі задачі
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    private final Supplier<Integer>randomInt = () ->
    {
    printWithTime("Supplier start");
    sleep();
    int result =  random.nextInt();
    printWithTime("Supplier finish WITH RESULT: "+ result);
    return result;
    };

    private final Function<Integer,String> signAlyzer = num ->
    {
      printWithTime("signAlyzer start WITH INPUT :"+ num);
    sleep();
     String result = String.format("Number %d %s", num, num > 0 ? "Positive" : "Negative or 0");
    printWithTime("signAlyzer finish WITH RESULT:"+ result);
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
