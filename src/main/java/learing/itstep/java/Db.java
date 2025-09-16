
package learing.itstep.java;

import com.mysql.cj.jdbc.Driver;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class Db {
 private Map<String, String> loadConfig(String iniFileName) throws Exception {
        Map<String, String> config = new HashMap<>();
        try(InputStream inputStream = Objects.requireNonNull(
                this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream(iniFileName));
            Scanner scanner = new Scanner(inputStream)
        ) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                String uncommentedPart = parts[0];
                parts = uncommentedPart.split("=", 2);
                if(parts.length != 2) continue;                
                config.put(parts[0].trim(), parts[1].trim());
            }
        } 
        catch (IOException ex) {
            throw new Exception(ex);
        }
        catch (NullPointerException ex) {
            throw new Exception("Resource not found");
        }
        return config;
    }
 
 
    public void demo() {//JDBC - Java DataBase Community - аналог ADO.NET C#
       Driver mysqlDriver;                //набір інструментів для работи з бд
    
       Connection connection;
          String connectionString;//= "jdbc:mysql://localhost:3306/java_kn_p_222"
               //+ "?useUnicode=true&characterEncoding=utf8";
       try
       {
       Map<String, String> config =  loadConfig("db.ini");
//    connectionString = String.format(
//                    "%s:%s://%s:%s/%s?user=%s&password=%s&%s",
//                    config.get("protocol"),
//                    config.get("dbms"),
//                    config.get("host"),
//                    config.get("port"),
//                    config.get("scheme"),
//                    config.get("user"),
//                    config.get("password"),
//                    config.get("params")
//                );  
     connectionString = String.format(
                    "%s:%s://%s:%s/%s?user=%s&password=%s&%s",
                    Objects.requireNonNull(config.get("protocol"), "ini miss protocol"),
                    Objects.requireNonNull(config.get("dbms"    ), "ini miss dbms"    ),
                    Objects.requireNonNull(config.get("host"    ), "ini miss host"    ),
                    Objects.requireNonNull(config.get("port"    ), "ini miss port"    ),
                    Objects.requireNonNull(config.get("scheme"  ), "ini miss scheme"  ),
                    Objects.requireNonNull(config.get("user"    ), "ini miss user"    ),
                    Objects.requireNonNull(config.get("password"), "ini miss password"),
                    Objects.requireNonNull(config.get("params"  ), "ini miss params"  )                 
            );
        System.out.println(connectionString);
       }
      
      
       catch(Exception ex)
       {
        System.err.println("Config error: "+ ex.getMessage());
        return;
       }
       try {
             mysqlDriver = new com.mysql.cj.jdbc.Driver();
             DriverManager.registerDriver(mysqlDriver);
             connection = DriverManager.getConnection(connectionString);//,"user_kn_p_222","pass_222");
           
        } catch (SQLException ex) {
            System.err.println("Start error:"+ ex.getMessage());
            return;
        }
       
       
        System.out.println("Connection OK");
       
       
         try {
             connection.close();
             DriverManager.deregisterDriver(mysqlDriver);
           
        } catch (SQLException ex) {
            System.err.println("Finish error:"+ ex.getMessage());
        }
    }
}

/*
Робота з базами даних
-Встановлюємо програмне забезпечення
https://dev.mysql.com/downloads/workbench/
https://dev.mysql.com/downloads/mysql/


-Створюємо нову БД, а також користувача для неї
create database java_kn_p_222;
create user user_kn_p_222@localhost identified by 'pass_222';
grant all privileges on java_kn_p_222.* to user_kn_p_222@localhost; 



-Додаємо драйвер для даної мови програмування
На сайті репозиторії обираємо останню версію драйвера
https://mvnrepository.com/repos/central
https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
Копіюємо код залежності та додаємо його до pom.xml
 <dependencies>
        <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.4.0</version>
</dependency>
    </dependencies>
У IntellijIdea необхідно натиснути кнопку оновлення залежностей

-Реєструємо драйвер та підключаємось до БД(див код)
*/