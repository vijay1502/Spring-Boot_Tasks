package com.stackroute.trackservice.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public class DBConfiguration {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    @ConfigurationProperties(prefix = "app")
    //@Profile is logical grouping of data and can be used on type level annotation or asa meta data 
    //dev represents the development,test represents testing and prod for production .
   @Profile("dev")
    //Instantiating an object in spring using @Bean annotation
   @Bean
    public String  devDbConfiguraton(){
       //Here we print data related to developer, the driver class name of database,url of it and return it to them
       System.out.println("DEV-DB-H2");
       System.out.println(driverClassName);
       System.out.println(url);
       return "Data Base Connection for h2";

   }
    @Profile("test")
     //Instantiating an object in spring using @Bean annotation
    @Bean
    public String  testDbConfiguraton(){
        //Here we print data related to testing, define the driver class name of database,url of it and return it to them

        System.out.println("Test using DataBase H2");
        System.out.println(driverClassName);
        System.out.println(url);
        return "Data Base Connection for h2";

    }
    @Profile("prod")
     //Instantiating an object in spring using @Bean annotation
    @Bean
    public String  prodDbConfiguraton(){
        //Here we print data related to production department, the driver class name of database,url of it and return it to them

        System.out.println("For Production through mySql");

        System.out.println(url);
        return "Data Base Connection using mySql";

    }

}
