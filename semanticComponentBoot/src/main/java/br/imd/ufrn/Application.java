package br.imd.ufrn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.imd.ufrn.util.AppContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(AppContext.class, args);
	}
	
}
