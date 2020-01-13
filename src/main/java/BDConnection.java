
import java.sql.*;
import java.util.Scanner;

public class BDConnection {
    public BDConnection(){}
    Connection connection = null;

    public static void main(String[] args){
        BDConnection bdConnection = new BDConnection();
        Connection connection = bdConnection.getConnection();

        System.out.println("\n*************-_-***************\n");

        bdConnection.getNameAndAge();

        System.out.println("\n*************-_-***************\n");

        bdConnection.addAlumne();

        System.out.println("\n*************-_-***************\n");

        bdConnection.getAlumneWhere();

    }

    public Connection getConnection(){

        try{
            System.out.println("EJERCICIO 1\n");

            connection = DriverManager.getConnection("jdbc:mysql://192.168.22.179:3306/JonatanSanchezEscola", "root", "Password1.");
            System.out.println("BBDD CONNECTED");

        }catch (SQLException e){
            System.out.println(e);
        }
        return connection;
    }

    public void getNameAndAge(){
        try{
            System.out.println("EJERCICIO 2\n");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Nom, Edat FROM Alumnes");

            while (resultSet.next()){
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }

            resultSet.close();
            statement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void addAlumne(){
        try{
            System.out.println("EJERCICIO 3\n");

            String Nom = "";
            String Cognoms = "";
            String Institut = "";

            Scanner scanner = new Scanner(System.in);

            System.out.println("Nom de l'alumne: ");
            Nom = scanner.nextLine();

            System.out.println("Cognoms de l'alumne: ");
            Cognoms = scanner.nextLine();

            System.out.println("Institut de l'alumne: ");
            Institut = scanner.nextLine();

            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Alumnes(Nom, Cognoms, Edat, Institut) values('"+Nom+"','"+Cognoms+"',38,'"+Institut+"')");

            statement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void getAlumneWhere(){
        try{
            System.out.println("EJERCICIO 4\n");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Alumnes WHERE Edat BETWEEN '25' AND '48'");

            while (resultSet.next()){
                System.out.println(resultSet.getString(1)  + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
            }

            resultSet.close();
            statement.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }

}