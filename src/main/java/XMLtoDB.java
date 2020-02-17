import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.*;

public class XMLtoDB {

    Connection connection = null;

    public static void main(String[] args) {

        XMLtoDB xmLtoDB = new XMLtoDB();
        Connection connection = xmLtoDB.getConnection();

        Statement statement = xmLtoDB.createTable();

        xmLtoDB.insertData(statement);

    }

    public Connection getConnection(){

        try{
            connection = DriverManager.getConnection("jdbc:mysql://192.168.22.179:3306/JonatanSanchezEscola", "root", "Password1.");
            System.out.println("BBDD CONNECTED");

        }catch (SQLException e){
            System.out.println(e);
        }
        return connection;
    }

    public Statement createTable(){
        try{

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE Films(Year INT, Title VARCHAR(50), Director VARCHAR(20), Country VARCHAR(20))");

            return statement;
        }catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public void insertData(Statement statement){

        try{
            File inputFile = new File("/home/dam2a/Baixades/films.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Film");


            for (int i = 1; i < nList.getLength(); i++) {

                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE){

                    Element eElement = (Element) nNode;


                    String year =  eElement.getAttribute("produced");
                    int yearBueno = Integer.parseInt(year);
                    String title =  eElement.getElementsByTagName("Title").item(0).getTextContent();
                    String director =  eElement.getElementsByTagName("Director").item(0).getTextContent();
                    String country =  eElement.getElementsByTagName("Country").item(0).getTextContent();


                    statement.executeUpdate("INSERT INTO Films(Year, Title, Director, Country) values('"+yearBueno+"','"+title+"','"+director+"','"+country+"')");

                }

            }

            statement.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
