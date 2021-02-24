package pl.xpawelek.database;

import pl.xpawelek.database.basic.User;
import pl.xpawelek.database.manager.UserManager;

import java.sql.*;
import java.util.logging.Logger;

public class MySQL {

    // TODO: 23.02.2021

    private static MySQL mySQL; // podobnie jak w glownej klasie dodajemy nasza instancje
    private String host = "localhost"; // podajemy host naszej bazy danych mysql
    private int port = 3306; // port zostawiamy jest on nie zmienny
    private String username = "root"; // podajemy uzytkownika z danych od bazy mysql
    private String table = "test"; // podajemy w jakiej tabeli maja znajdowac sie nasze informacje
    private String password = ""; // podajemy haslo do naszej bazy danych
    private Connection connection; // dodajemy polaczenie


    public static MySQL getInstance(){
        if(mySQL == null)return new MySQL();
        return mySQL;
    }
    public MySQL(){
        mySQL = this;
    }

    public Connection connect(){ // laczymy sie z nasza baza danych
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + table, username, password);
            Logger.getAnonymousLogger().info("Polaczono");
        } catch (Exception e) {
            // jesli nie bedziemy mogli sie polaczyc z naszym hostem wyskoczy nam blad w konsoli
            e.printStackTrace();
        }
        return connection;
    }

    public void init(){
        if(connection == null)return; // jesli nasze polaczenie nie zostanie dobrze skonfigurowanie inincjacja zostanie przerwana
        try {
            Statement statement = getConnection().createStatement();
           StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE IF NOT EXISTS `users` ("); // jesli nasza tabela nie istnieje tworzymy ja
            stringBuilder.append("UUID varchar(90),"); // tworzymy uuid naszego gracza
            stringBuilder.append("PLAYER varchar(32),"); // tworzymy nazwe naszego gracza
            stringBuilder.append("POINTS int(16),"); // tworzymy punkty ktore zrobilismy w klasie User
            stringBuilder.append("primary key (UUID));"); // dzieki temu bedziemy mogli edytowac dane naszego gracza przez baze danych
            statement.executeUpdate(stringBuilder.toString());

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `users`"); // pobieramy wszystkich graczy z bazy danych ktore dzieki czzemu podczas urchomienia pluginu pomoze nam to do dodania ich do naszej mapy z klasy UserManager
            if(resultSet.next()){
                new User(resultSet); // tworzymy naszych userow dzieki czemu gdy wejda na serwer beda mogli korzystac z wczesniejszego zapisu
            }

        } catch (Exception e) {
            // jesli jakas opcja sie nie wykona ukarze sie nam blad w konsoli
            e.printStackTrace();
        }
    }
    public void saveUsers(){ // zapisujemy naszych graczy do bazy jesli wykonanli np zabojstwo na serwerze
        if(connection == null)return;
        try {
            Statement statement = getConnection().createStatement(); // tworzymy nasze polaczenie do wykonywania metod typu executeUpdate do bazy danych
            for(User user : UserManager.getUserConcurrentHashMap().values()){ // pobieramy wszystkich user i wyszukujemy tego ktory akurat dostal update
               if(!user.hasUpdate())continue;
               statement.executeUpdate(user.update()); // zwracamy wartosc z naszego usera
               user.setUpdate(false); // ustawiamy ze nasz user zostal juz stworzony
            }
        } catch (SQLException throttles) {
            throttles.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
