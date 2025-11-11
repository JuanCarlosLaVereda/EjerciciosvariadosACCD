package EjercicioPerformanceTest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class TestRendimientoPool {

    private static final String DB_URL =  "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    private static final int NUM_PETICIONES = 5000;
    private static final String CONSULTA = "SELECT 1";


    static void main() {
        //Metodo 1
        System.out.println("Metodo 1");
        long tiempo = testConDriverManager();
        System.out.println("Tiempo de ejecucion: " + tiempo + "ms");



        //Metodo 2

        System.out.println("Metodo 2");
        tiempo = testConDataSource();
        System.out.println("Tiempo de ejecucion: " + tiempo + "ms");

    }

    public static long testConDriverManager(){
        long tiempo_inicio = System.nanoTime();
        System.out.println("Ejecutando...");
        for(int i = 0; i < NUM_PETICIONES; i++){
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps =  con.prepareStatement(CONSULTA);
            ResultSet rs = ps.executeQuery();){

            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos");
                e.printStackTrace();
            }
        }
        long tiempo_final = System.nanoTime();
        return ((tiempo_final - tiempo_inicio) / 1_000_000);

    }
    public static long testConDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000); //30 segundos
        long tiempo_inicio = System.nanoTime();
        try(HikariDataSource ds = new HikariDataSource(config);){
            System.out.println("Ejecutando...");
            for (int i = 1; i <= NUM_PETICIONES; i++) {
                try (Connection connection = ds.getConnection();){

                    try (PreparedStatement ps =  connection.prepareStatement(CONSULTA);
                         ResultSet rs = ps.executeQuery()) {

                    }


                } catch (SQLException e) {
                    System.err.println("Error al conectar con la base de datos");
                    e.printStackTrace();
                }
            }

        }

        long tiempo_final = System.nanoTime();
        return ((tiempo_final - tiempo_inicio) / 1_000_000);
    }
}
