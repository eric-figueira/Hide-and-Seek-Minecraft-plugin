package hidenseek.hidenseek;

import hidenseek.hidenseek.commands.*;
import hidenseek.hidenseek.listeners.falldamageListener;
import hidenseek.hidenseek.listeners.playerKillListener;
import hidenseek.hidenseek.listeners.playerWalkListener;
import hidenseek.hidenseek.listeners.playerjoinListener;
import hidenseek.hidenseek.managers.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideNSeek extends JavaPlugin {

    @Override
    public void onEnable() {

        GameManager gameManager = new GameManager(this);

        getCommand("setseekersspawn").setExecutor(new setfirstspawnCommand(this));
        getCommand("sethidersspawn").setExecutor(new setspawnothersCommand(this));
        getCommand("startgame").setExecutor(new startCommand(this, gameManager));
        getCommand("stopgame").setExecutor(new stopCommand(this, gameManager));
        getCommand("timeremaining").setExecutor(new gettimeCommand(gameManager));


        getServer().getPluginManager().registerEvents(new falldamageListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerjoinListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerKillListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerWalkListener(this, gameManager), this);
    }
}
