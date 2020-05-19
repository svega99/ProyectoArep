package edu.escuelaing.arep.realtimebuses.ClienteSpark;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import spark.*;
import static spark.Spark.*;
/**
 *
 * @author santiago.vega-r
 */
public class SparkWeb {
    public static void main( String[] args )
    {
        port(getPort());
          
        get("/", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
   
    }
    
     private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Planeación de rutas Transmilenio</h2>"
                + "<form action=\"/results\">"
                + "  Ingrese numero identificación del bus <br>"
                + "  <input type=\"text\" name=\"number\" >"
                + "  <br><br>"
				+ "  Ingrese numero identificación de la ruta <br>"
	            + "  <input type=\"text\" name=\"number1\" >"
	            + "  <input type=\"submit\" value=\"Submit\"> "
                + "  <br><br>"
                + "</form>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static String resultsPage(Request req, Response res) throws IOException {
        
        BufferedReader in = null;

       String request= req.queryParams("number");
       String request1= req.queryParams("number1");
       URL API = new URL("https://9i6rk1q1dl.execute-api.us-east-1.amazonaws.com/beta?IDBus="+request+"&Latitud="+4.755457+"&Longitud="+(-74.046128));
		
        URLConnection con = API.openConnection();
        
        String result = "";
        in = new BufferedReader(new InputStreamReader( con.getInputStream()));
		
		System.err.println("Conectado");
		BufferedReader stdIn = new BufferedReader(
		new InputStreamReader(System.in));
		String line;
		
		while ((line = in.readLine()) != null) { 
                        result=result+line;
			System.out.println(line); 
		}
        
        
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h3>Ruta: "+ request1+" </h3>"
                +"<p>Iniciada Correctamente</p>"
                + "</body>"
                + "</html>";
        
        return pageContent;
    }
    
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }        
           
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
    }
}
