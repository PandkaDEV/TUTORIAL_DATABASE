package pl.xpawelek.database.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.xpawelek.database.basic.User;
import pl.xpawelek.database.manager.UserManager;

public class PlayerJoinListeners implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player); // pobieramy naszego usera lub go tworzymy
        user.setPoints(user.getPoints() + 10); // ustawiamy liczbe punktow
        player.sendMessage("Posiadasz: " + user.getPoints() + " punktow"); // sprawdzamy czy wszystko sie zgadza
    }
}
