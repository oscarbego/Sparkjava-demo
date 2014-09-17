
package app;

import app.model.Producto;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.setPort;


public class Server {

    //Este objeto parcea a JSON
    public static Gson g = new Gson();
    
    //El mapa simula la base de datos
    public static HashMap<String, Producto> listaProductos = new HashMap<>();
    
    
    public static void main(String[] args) {

        //--------- Model  ----------------
        
            Producto papas = new Producto("Papas", "Papas Fritas", 10.0);
            Producto hotdogs = new Producto("HotDog", "HotDog con tocino", 25.0);
            Producto hamburguesa = new Producto("Hamburguesa", "Hamburguesa BBQ", 30.0);
            Producto refresco = new Producto("Refrescos", "Refrescos varios sabores", 10.0);
            Producto agua = new Producto("Agua", "Agua Natural", 8.0);
        //---------------------------------
        
        //--------- Set Values ------------
        
            listaProductos.put(papas.nombre, papas);
            listaProductos.put(hotdogs.nombre, hotdogs);
            listaProductos.put(hamburguesa.nombre, hamburguesa);
            listaProductos.put(refresco.nombre, refresco);
            listaProductos.put(agua.nombre, agua);
        //---------------------------------
        
        //--------- Conf. Section ---------
        externalStaticFileLocation(
            System.getProperty("user.dir") + File.separator +
            "public"
        );
        
        setPort(8080);
        //---------------------------------
        
        
        
        //--------- Routes ----------------
        
        get(new Route("/lista") {
          
          @Override
          public Object handle(Request request, Response response) {
              
              return g.toJson(new ArrayList<Producto>(listaProductos.values()));
          }
        });
        
        
        post(new Route("/add") {
            @Override
            public Object handle(Request request, Response response) {
                
                String nombre = request.queryParams("nombre");
                String descri = request.queryParams("descri");
                String precio = request.queryParams("precio");
                
                if(
                    (nombre != null && !nombre.equals("")) &&
                    (descri != null && !nombre.equals("")) &&
                    (precio != null && !nombre.equals(""))    
                )
                {
                    listaProductos.put(nombre, new Producto(
                            nombre, 
                            descri, 
                            Double.parseDouble(precio)
                        )
                    );
                }
                
                response.redirect("/lista");
                return "";
            }
        });

        //---------------------------------        
    }   
}