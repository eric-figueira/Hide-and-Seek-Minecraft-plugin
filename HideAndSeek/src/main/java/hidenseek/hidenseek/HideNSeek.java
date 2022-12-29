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

        getCommand("setfirstspawn").setExecutor(new setfirstspawnCommand(this));
        getCommand("setspawnothers").setExecutor(new setspawnothersCommand(this));
        getCommand("start").setExecutor(new startCommand(this, gameManager));
        getCommand("stopgame").setExecutor(new stopCommand(this, gameManager));
        getCommand("restartgame").setExecutor(new restartCommand(this, gameManager));


        getServer().getPluginManager().registerEvents(new falldamageListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerjoinListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerKillListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new playerWalkListener(this, gameManager), this);
    }
}
