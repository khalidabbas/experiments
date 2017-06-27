package com.myApp.EjbServer;

import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup 
@Singleton
public class GreeterBean implements Greeter {
 
 	
	@Override
    public String greet(String user) {
        return "Hola,  " + user + ", como estas...quieres ir a Potes este Fin de Semana?si";
    }
}
