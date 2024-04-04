

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SQLDatabaseConnection {
    public static void main(String[] args) {
//        System.out.println("Введите имя пользователя");
//        String username = System.in.toString();
        String connectionUrl =
                "jdbc:sqlserver://LAPTOP-D1QCTRIA:1433;database=HotelNetwork;"
                        + "user=client;"
                        + "password=enter;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = null;
//            String command = "";
//            Boolean repeat = true;
//            while (repeat) {
//                System.out.print("Command: ");
//                command = System.in.toString();
//                switch (command.toUpperCase()) {
//                    case ("POST"):
//                        System.out.print("hotelUid, startDate, endDate: ");
//                        break;
//                    case ("DELETE"):
//                        System.out.println();
//                        break;
//                    case ("STOP"):
//                        repeat = false;
//                        break;
//                }
//            }
            String insertSql = "INSERT INTO Reservations (username, hotelUid, startDate, endDate) "
                    + "VALUES ('Геворг', '98FA348A-FAE5-4FA8-AC43-147457A3B898', '2024-04-05', '2024-04-11');";
            PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepsInsertProduct.execute();
            String reservationUuid = "5D1DF70B-A69B-46E2-ADCE-D2BF49CC57C2";
            String updateSql = "UPDATE Reservations SET resStatus = 'CANCELED' WHERE reservationUid = '"+reservationUuid+"'";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql, Statement.RETURN_GENERATED_KEYS);
            updateStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
