/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package learing.itstep.java;

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
public class Chaining {
    private long t;
    public void demo()
    {
           ExecutorService threadPool = Executors.newFixedThreadPool(2);
           Future<?> task1 = CompletableFuture
                .supplyAsync(stringSupplier, threadPool)
                .thenApply(processor1)
                .thenApply(processor2)
                .thenAccept(printer);
        try {
            task1.get();
            System.out.printf(
                "%.1f ms: chain finish\n", 
                (System.nanoTime() - t) / 1e6);
        } 
        catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }
            try {
              threadPool.shutdown();//не зупиняє задачі а лише перстає приймати
              threadPool.awaitTermination(3, TimeUnit.SECONDS);//блокує всі задачі поки вони не завершаться за 3 сек або сам потік не завершиться
              threadPool.shutdownNow(); //зупиняє всі задачі
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
    }
    //функціональний інтерфейс :джерело (постачальник) даних ,початкова ланка
   private final Supplier<String> stringSupplier = new Supplier<String>() {
        @Override
        public String get() {
            
               System.out.printf("%.1f ms: stringSupplier start\n", (System.nanoTime() - t) / 1e6);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
               System.out.printf("%.1f ms: stringSupplier finish\n", (System.nanoTime() - t) / 1e6);
         
            return "Origin 1";
        }
    };

        //Для функціоенальних інтерфейсів є скорочена форма оголошення 
           private final Supplier<String> stringSupplier2 = () -> {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
           
        return "Origin 2";
    };
    
           
           private final Function<String,String> processor1 = new Function<String,String>()
      {
        @Override
        public String apply(String input) {
           
               System.out.printf("%.1f ms: processor1 start\n", (System.nanoTime() - t) / 1e6);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
               System.out.printf("%.1f ms: processor1 finish\n", (System.nanoTime() - t) / 1e6);
         
            return input + "Processed1 1";
        
        }
      };
             //скорочена форма оголошення 
       private final Function<String, String> processor2 = input -> {
        System.out.printf(
                "%.1f ms: processor2 start\n", 
                (System.nanoTime() - t) / 1e6);
        try {
            Thread.sleep(500);
        } 
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.printf(
                "%.1f ms: processor2 finish\n", 
                (System.nanoTime() - t) / 1e6);
 
        return input + ". Processed 2";
    };
      
       // функціональний інтерфейс: "споживач" - фінальна ланка, є вхід, немає виходу 
    private final Consumer<String> printer = new Consumer<String>() {
        @Override
        public void accept(String input) {
            System.out.printf(
                "%.1f ms: Consumer(printer) got result '%s'\n", 
                (System.nanoTime() - t) / 1e6,
                input);
        }        
    };
}
