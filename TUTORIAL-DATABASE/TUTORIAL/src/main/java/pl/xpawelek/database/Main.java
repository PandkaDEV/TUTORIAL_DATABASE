package pl.xpawelek.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xpawelek.database.listeners.PlayerJoinListeners;
import pl.xpawelek.database.tasks.SaveTask;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    // TODO: 23.02.2021


    private static Main instance; // dodajemy instancje dla naszego glownej klasy

    public static Main getInstance(){ // pobieramy nasza instancje
        return instance;
    }

    @Override
    public void onLoad(){
        instance = this; // tworzymy nasza instancje podczas ladowania pluginu
        Logger.getAnonymousLogger().info("Trwa ladowanie pluginu...");
    }

    @Override
    public void onEnable() {
        Logger.getAnonymousLogger().info("Zaladowano nasz plugin");
       MySQL.getInstance().connect(); // laczymy sie z nasza baza danych
        MySQL.getInstance().init();  // tworzymy tabele lub pobieramy dane z bazy aby stworzyc wczesniejszych graczy
        new SaveTask().start();
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListeners(), this);

    }

    @Override
    public void onDisable() {
        Logger.getAnonymousLogger().info("Plugin zostal wylaczony");
    }
}
