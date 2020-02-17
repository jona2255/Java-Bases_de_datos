import java.sql.*;
import java.util.Scanner;

public class NBA {


    public NBA(){}
    Connection connection = null;


    public static void main(String[] args) {
        // write your code here

        NBA dBconnection = new NBA();
        Connection connection = dBconnection.getConnection();

        dBconnection.getCavaliers();
        dBconnection.getSpaniards();
        dBconnection.addJugador();
        dBconnection.getPlayers();
        dBconnection.getWarriors();
    }


    public Connection getConnection(){

        try{
            connection = DriverManager.getConnection("jdbc:mysql://192.168.22.179:3306/NBA", "root", "Password1.");
            System.out.println("DB CONNECTED");

        }catch (SQLException e){
            System.out.println(e);
        }
        return connection;
    }

    public void getCavaliers(){
        try{
            System.out.println("EJERCICIO 1\n");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Procedencia, Posicion FROM jugadores where Nombre_equipo like ?;");
            preparedStatement.setString(1,"Cavaliers");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void getSpaniards(){
        try{
            System.out.println("\nEJERCICIO 2\n");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) as Nombre_Jugadores FROM jugadores where Procedencia like ?;");
            preparedStatement.setString(1,"Spain");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
            }

            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void addJugador(){
        try{
            System.out.println("\nEJERCICIO 3\n");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO jugadores(codigo, Nombre, Procedencia, Altura, Peso, Posicion, Nombre_equipo) values(?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1,666);
            preparedStatement.setString(2,"Luka Dončić");
            preparedStatement.setString(3,"Slovenia");
            preparedStatement.setString(4,"6-7");
            preparedStatement.setInt(5,230);
            preparedStatement.setString(6,"G-F");
            preparedStatement.setString(7,"Mavericks");
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void getPlayers(){
        try{
            System.out.println("\nEJERCICIO 4\n");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT j.Nombre, e.Puntos_por_partido FROM jugadores j LEFT JOIN estadisticas e on j.codigo=e.jugador WHERE e.temporada = ? AND e.Puntos_por_partido >= ? ;");
            preparedStatement.setString(1,"04/05");
            preparedStatement.setInt(2,10);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }

            resultSet.close();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void getWarriors(){
        try{
            System.out.println("\nEJERCICIO 5\n");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM partidos Where temporada = ? ;");
            preparedStatement.setString(1,"05/06");

            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 0;

            while (resultSet.next()){
                if (resultSet.getString("equipo_visitante").equals("Warriors") && (resultSet.getInt("puntos_visitante") - resultSet.getInt("puntos_local")> 15)){
                    counter++;
                } else if (resultSet.getString("equipo_local").equals("Warriors") && (resultSet.getInt("puntos_local") - resultSet.getInt("puntos_visitante")> 15)){
                    counter++;
                }

            }
            resultSet.close();
            preparedStatement.close();

            System.out.println(counter);

        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
