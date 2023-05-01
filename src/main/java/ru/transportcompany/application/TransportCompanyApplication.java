package ru.transportcompany.application;

import com.jpomykala.springhoc.cors.EnableCORS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCORS
public class TransportCompanyApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(TransportCompanyApplication.class, args);
    }

}
