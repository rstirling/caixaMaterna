package org.caixaMaterna;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    private static final String CAIXA_PID = "./caixa.pid";

	public static void main(String[] args) throws IOException {
    	
    	String pid = getPid();
    	writeToFile(pid, CAIXA_PID);
    	
        SpringApplication.run(Application.class, args);
    }
    
    private static String getPid(){
    	String pid = ManagementFactory.getRuntimeMXBean().getName();
    	return pid;
    }
    
    private static void writeToFile(String value, String file) throws IOException{
    	Files.write(Paths.get(CAIXA_PID), value.getBytes());
    }
}
