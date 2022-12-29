package hidenseek.hidenseek.listeners;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class falldamageListener implements Listener {

    private final HideNSeek plugin;
    private final GameManager gameManager;

    public falldamageListener(HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @EventHandler
    public void onFallDamage (EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (this.gameManager.getGameState() == GameState.STARTED || this.gameManager.getGameState() == GameState.STARTING) {
                    event.getEntity().setFallDistance(0);
                    event.setCancelled(true);
                }
            }
        }
    }
}
