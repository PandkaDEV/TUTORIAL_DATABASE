package pl.xpawelek.database.basic;

import org.bukkit.entity.Player;
import pl.xpawelek.database.cache.UserCache;
import pl.xpawelek.database.interfaces.UserInterFace;
import pl.xpawelek.database.manager.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User implements UserInterFace {


    // TODO: 23.02.2021  
    
    private UUID uuid; // uuid gracza minecraft
    private String playerName; // nazwa gracza minecraft
    private int points; // ilosc punktow gracza
    private UserCache userCache; // dzieki temu obiektowi bedziemy mogli wykonywac akutalizacje


    public User(Player player){
        this.uuid = player.getUniqueId();
        this.playerName = player.getName();
        this.points = 100;
        this.userCache = new UserCache();
        UserManager.getUserConcurrentHashMap().put(this.uuid , this);
    }
    public User(ResultSet resultSet)throws SQLException{ // ta opcja pozwoli nam pobrac dane uzytkownika z bazy danych
        this.uuid = UUID.fromString(resultSet.getString("UUID"));
        this.playerName = resultSet.getString("PLAYER");
        this.points = resultSet.getInt("POINTS");
        this.userCache = new UserCache();
        UserManager.getUserConcurrentHashMap().put(this.uuid , this);
    }



    @Override
    public void setUpdate(boolean update) { // jesli nie masz pojecia co ta opcja wykonuje prosze wrocic do naszego UserInterFace
        this.getUserCache().setStatus(update);
    }

    @Override
    public boolean hasUpdate() { // jesli nie masz pojecia co ta opcja wykonuje prosze wrocic do naszego UserInterFace
        return getUserCache().isStatus();
    }

    @Override
    public String update() { // jesli nie masz pojecia co ta opcja wykonuje prosze wrocic do naszego UserInterFace
        StringBuilder stringBuilder = new StringBuilder(); // tworzymy StringBuildera poniewaz jest szybszy od zwyklego Stringa
        stringBuilder.append("INSERT INTO `users` VALUES ("); // dodajemy append do naszego sql
        stringBuilder.append("'" + this.getUuid() + "',"); // teraz wymieniamy parametry ktore utworzylismy powyzej
        stringBuilder.append("'" + this.getPlayerName() + "',");
        stringBuilder.append("'" + this.getPoints() + "'");
        stringBuilder.append(") ON DUPLICATE KEY UPDATE "); // sprawdzamy czy podane dane nie zostaly juz stworzone wczesniej w bazie danych jesli zostaly akutalizujemy je
        stringBuilder.append("UUID='" + this.getUuid() + "',"); // robimy to samo co przed ON DUPLICATE KEY UPDATE tylko dopisujemy przed nazwe kolumny z bazy
        stringBuilder.append("PLAYER='" + this.getPlayerName() + "',");
        stringBuilder.append("POINTS='" + this.getPoints() + "'");
        return stringBuilder.toString(); // konczymy naszego stringBuildera metoda .toString() dzieki temu zwracamy nasza wartosc ktora jest w stringBuilderze
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) { // opcja ta pomoze nam ustawic punkty danemu graczu
        this.points = points;
        setUpdate(true); // ustawiamy nasz status akutalizacji na "true"- gdy chcemy aby naszemu userowi chcialo wejsc cos do bazy danych jesli ustawimy "false" - punkty gracza nie zostana zapisane
    }

    public UserCache getUserCache() {
        return userCache;
    }
}
