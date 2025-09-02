package learing.itstep.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


//задача э деякий сервіс що повертає дані про рівень місячгної інфляції у відсотках. Необхідно розрахувати ручну інфляцію утворивши 12 запитів до сервісу асинхронно
public class Threading {
 static class RandomizerOnMinimums implements Callable<Character> 
 {
    private final char start;
    private final int range;

    RandomizerOnMinimums(char start, int range) {
        this.start = start;
        this.range = range;
    }

    @Override
    public Character call() throws Exception {
        Random r = new Random();
         Thread.sleep(1000);
         char c = (char) (start + r.nextInt(range));
          System.out.print(c);
       return c;
    }
}

     
      private void demoRandomizerWork()
    {
        //1 = 13016,9 ms
        //2 =
        //3 = 5012,8 ms
        //6 = 3012,4 ms
        //9 = 2010,8 ms
        //12 = 2011,0 ms
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        List <Future<Character>> tasks = new ArrayList<>();
     long t = System.nanoTime();
    for (int i =0 ;i<=12;i++)
    {
        tasks.add(threadPool.submit(new RandomizerOnMinimums('a', 26)));
    }
  StringBuilder sb = new StringBuilder();
 
            for (Future<Character> task : tasks)
            {
            try
            {
          
             sb.append(task.get());
             }
         catch (InterruptedException | ExecutionException ex) {
    System.out.println(ex.getMessage());
    }
            }
            
            System.out.println("_______________________");
        
      
       
       threadPool.shutdown();
       System.out.println("RandomizerOnMinimums--- " + sb.toString());
         System.out.printf("%.1f ms\n", (System.nanoTime() - t) / 1e6);
           System.out.println("_______________________");
    }   
     
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    static class MonthPercent implements Callable
    {
 //Передача аругмента
        MonthPercent(int month) {
            this.month = month;
        }
        private final int month;
     @Override
        public Double call() throws Exception {
            Thread.sleep(1000);
            return this.month / 10.0;
        }
    }
    private void demoPercent()
    {
        //1 = 12010,7 ms
        //2 = 6006,8 ms
        //3 = 4005,3 ms
        //4 = 3004,6 ms
        //5 = 3003,7 ms
        //6 =2003,3 ms
        //7 =2004,3 ms
        //8 =2003,3 ms
        //9 =2003,9 ms
        //10 =2004,3
        //11 =2003,0 ms
        //12 = 1003,7 ms
        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        List <Future<Double>> tasks = new ArrayList<>();
    long t = System.nanoTime();
        for (int i =1 ;i<=12;i++)
    {
        tasks.add(threadPool.submit(new MonthPercent(i)));
    }
//       Future <Double> task = threadPool.submit(new MonthPercent(11));
        try
        {
            Double sum = 100.0;
            for (Future<Double> task : tasks)
            {
                
            Double res = task.get();
            System.out.println(res);
            sum*=(1.0+res/100.0);
            }
            System.out.println("_______________________");
         System.out.printf("%.1f ms: %.3f\n", (System.nanoTime() - t) / 1e6, sum);
        }
         catch (InterruptedException | ExecutionException ex) {
    System.out.println(ex.getMessage());
    }
       
       threadPool.shutdown();
    }   
    public void demo() {
//        demo2();
      //   demoPercent();
        demoRandomizerWork();    
    }
    
    public void demo2() {
        // керовані запуски потоків - виконавці
    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    // Runnable
    Future<?>task1 = threadPool.submit(new Runnable() { // inline implementation
        @Override
        public void run() {
            System.out.println("Task 2 -Runnable executed");
        }
    });
    
     Future<String> task2 = threadPool.submit(new Callable<String>() { // inline implementation
        @Override
        public String call() throws Exception {
            System.out.println("Task 2 -Callable executing");
            return "Task 2 -Callable executed";
        }
    });
     //Виконавець пул потоків вимагає зупинення ,інакше программа не зупиняється
   String res2 ;
   try{
       res2= task2.get();//await task2
       System.out.println(res2);
   }
   catch (InterruptedException | ExecutionException ex) {
    System.out.println(ex.getMessage());
   }
   
    threadPool.shutdown(); 
   
    }
    public void demo1() {
        //базовий спосіб використання потоків
        Runnable task1 = new Runnable() {//inline implementation
            @Override
            public void run() {
                System.out.println("Task 1 executed");
            }
        };

        
        task1.run();      //синхронний запуск       
        new Thread(task1).start(); // запуск в окремому потоці
        //"+" максимальна швидкість виконання 
        //"-" некерованість - ми не можемо зупинити потік 
    }
}
