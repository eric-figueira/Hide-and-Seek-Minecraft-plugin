package hidenseek.hidenseek.listeners;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerjoinListener implements Listener {

    private final HideNSeek plugin;
    private final GameManager gameManager;

    public playerjoinListener (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }


    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        if (this.gameManager.getGameState() == GameState.STARTED) {
            event.getPlayer().getInventory().clear();
            event.getPlayer().sendMessage(ChatColor.YELLOW + "The game have already started, so you are now a seeker!");
            this.gameManager.makePlayerSeeker(event.getPlayer());
        }
    }
}
