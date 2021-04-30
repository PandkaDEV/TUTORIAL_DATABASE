package pl.xpawelek.database.tasks;

import org.bukkit.Bukkit;
import pl.xpawelek.database.Main;
import pl.xpawelek.database.MySQL;


public class SaveTask  implements Runnable{


    @Override
    public void run() {
        MySQL.getInstance().saveUsers(); // zapisujemy naszego usera po danym czasie
    }

    public void start(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this, 1L, 1200L); // wywolujemy naszego asynchronicznego taska ktory jest optymalny
    }
}
