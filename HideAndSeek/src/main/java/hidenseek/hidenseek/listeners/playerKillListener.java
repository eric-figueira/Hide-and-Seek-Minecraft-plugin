package hidenseek.hidenseek.listeners;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class playerKillListener implements Listener {

    private final HideNSeek plugin;
    private final GameManager gameManager;

    public playerKillListener (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @EventHandler
    public void onPlayerKill (EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                Player killer = (Player) event.getDamager();
                Player killed = (Player) event.getEntity();

                if (this.gameManager.getGameState() == GameState.STARTED) {
                    if (this.gameManager.getSeekers().contains(killer)) { // A seeker killed:
                        if (this.gameManager.getSeekers().contains(killed)) // Another seeker
                            event.setCancelled(true);
                        else { // A hider
                            this.gameManager.makePlayerSeeker(killed);
                            killer.sendMessage(ChatColor.YELLOW + "You made " + killed.getDisplayName() + " a seeker!");
                        }
                    }
                }
            }
        }
    }
}
