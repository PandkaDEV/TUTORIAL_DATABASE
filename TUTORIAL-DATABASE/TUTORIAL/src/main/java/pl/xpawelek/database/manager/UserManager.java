package pl.xpawelek.database.manager;

import org.bukkit.entity.Player;
import pl.xpawelek.database.basic.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    public static ConcurrentHashMap<UUID, User> userConcurrentHashMap = new ConcurrentHashMap<>(); // tworzymy mape dzieki ktorej bedziemy mogli przechowywac nasze dane


    public static User getUser(Player player){
        for(User user : getUserConcurrentHashMap().values()) // pobieramy naszego gracza z wszystkich userow
            if(user.getUuid().equals(player.getUniqueId())) // sprawdzamy czy byl kiedys juz w naszej mapie jesli byl zwrocimy mu jego dane
                return user;
            return new User(player); // jesli gracz nigdy nie byl na serwerze tworzymy go
    }


    public static ConcurrentHashMap<UUID, User> getUserConcurrentHashMap() {
        return userConcurrentHashMap;
    }
}
