package learing.itstep.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Головний клас демонстрації потоків
public class Threading {

    // --------------------
    // Приклад 1: RandomizerOnMinimums
    // --------------------
    // Callable<Character> — це інтерфейс, що повертає результат і може кидати Exception
    static class RandomizerOnMinimums implements Callable<Character> {
        private final char start;
        private final int range;

        RandomizerOnMinimums(char start, int range) {
            this.start = start;
            this.range = range;
        }

        @Override
        public Character call() throws Exception {
            // Thread.sleep(1000) – "імітація затримки", наприклад, запит до сервісу
            Thread.sleep(1000);

            // Генерація випадкового символу
            Random r = new Random();
            char c = (char) (start + r.nextInt(range));
            System.out.print(c); // виводимо одразу в консоль
            return c; // повертаємо результат для основного потоку
        }
    }

    private void demoRandomizerWork() {
        // ExecutorService — пул потоків, керує запуском потоків
        ExecutorService threadPool = Executors.newFixedThreadPool(2); // 2 потоки одночасно
        List<Future<Character>> tasks = new ArrayList<>(); // Future — результат асинхронного завдання

        long t = System.nanoTime(); // вимірюємо час виконання

        // створюємо 13 задач (0-12) асинхронно
        for (int i = 0; i <= 12; i++) {
            tasks.add(threadPool.submit(new RandomizerOnMinimums('a', 26)));
        }

        StringBuilder sb = new StringBuilder();

        // отримуємо результати від задач (task.get() чекає завершення)
        for (Future<Character> task : tasks) {
            try {
                sb.append(task.get()); // блокуючий виклик — програма чекає результат
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(ex.getMessage());
            }
        }

        System.out.println("\n_______________________");

        threadPool.shutdown(); // обов'язково закриваємо пул потоків
        System.out.println("RandomizerOnMinimums--- " + sb.toString());
        System.out.printf("%.1f ms\n", (System.nanoTime() - t) / 1e6);
        System.out.println("_______________________");
    }

    // --------------------
    // Приклад 2: MonthPercent
    // --------------------
    // Callable<Double> — обчислює місячний відсоток
    static class MonthPercent implements Callable<Double> {
        private final int month;

        MonthPercent(int month) {
            this.month = month;
        }

        @Override
        public Double call() throws Exception {
            Thread.sleep(1000); // затримка для імітації запиту
            return this.month / 10.0; // повертаємо "штучний" процент
        }
    }

    private void demoPercent() {
        // 12 потоків одночасно (по 1 на місяць)
        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        List<Future<Double>> tasks = new ArrayList<>();

        long t = System.nanoTime();

        for (int i = 1; i <= 12; i++) {
            tasks.add(threadPool.submit(new MonthPercent(i)));
        }

        try {
            Double sum = 100.0; // початкове значення, наприклад, 100%
            for (Future<Double> task : tasks) {
                Double res = task.get(); // чекаємо результат
                System.out.println(res);
                sum *= (1.0 + res / 100.0); // "накопичення інфляції"
            }
            System.out.println("_______________________");
            System.out.printf("%.1f ms: %.3f\n", (System.nanoTime() - t) / 1e6, sum);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

        threadPool.shutdown();
    }

    // --------------------
    // Приклад 3: Використання ExecutorService з Runnable та Callable
    // --------------------
    public void demo2() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // Runnable — завдання без повернення результату
        Future<?> task1 = threadPool.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task 2 -Runnable executed");
            }
        });

        // Callable — завдання з поверненням результату
        Future<String> task2 = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("Task 2 -Callable executing");
                return "Task 2 -Callable executed";
            }
        });

        try {
            String res2 = task2.get(); // чекаємо результат
            System.out.println(res2);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }

        threadPool.shutdown(); // обов'язково закриваємо пул
    }

    // --------------------
    // Приклад 4: Базові потоки
    // --------------------
    public void demo1() {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task 1 executed");
            }
        };

        task1.run();             // синхронний виклик (в потоці main)
        new Thread(task1).start(); // асинхронний запуск в окремому потоці
    }

    // --------------------
    // Головний метод демонстрації
    // --------------------
    public void demo() {
        //demo2();       // демонстрація ExecutorService з Runnable/Callable
        //demoPercent(); // демонстрація асинхронних обчислень інфляції
        demoRandomizerWork(); // демонстрація генерації випадкових символів
    }
}



/*
| C#                    | Java (до нових версій)                              |
| --------------------- | --------------------------------------------------- |
| `await Task.Run(...)` | `ExecutorService.submit(Callable)` + `Future.get()` |
| Магія `async/await`   | Треба чекати вручну через `get()`                   |
| Простий синтаксис     | Більше коду, контроль ресурсів                      |



Runnable — просто виконує код, нічого не повертає, не кидає checked Exception.

Callable<T> — виконує код, повертає результат типу T і може кидати Exception.

ExecutorService - Пул потоків для керування багатьма потоками. Метод submit() повертає Future — об’єкт, через який можна дочекатися результату.

Future.get()- Блокуючий виклик, чекає завершення завдання і повертає результат.

Може кидати InterruptedException або ExecutionException.

Thread.sleep() - Імітує затримку (наприклад, довгий запит до сервісу).

threadPool.shutdown() - Обов'язково викликати після завершення роботи, інакше програма не завершиться.*/