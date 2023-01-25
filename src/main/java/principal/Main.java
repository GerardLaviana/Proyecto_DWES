package principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import principal.persistencia.CrearTablas;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//CrearTablas ct= new CrearTablas();
		//ct.creacion();
		SpringApplication.run(Main.class, args);

	}

}
