package hidenseek.hidenseek.listeners;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class playerWalkListener implements Listener {

    private final HideNSeek plugin;
    private final GameManager gameManager;

    public playerWalkListener(HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @EventHandler
    public void onPlayerWalk (PlayerMoveEvent event) {
        Player p = event.getPlayer();

        // if the player is the first seeker, the event must be cancelled
        if (this.gameManager.getGameState() == GameState.STARTED) {
            if (this.gameManager.getSeekers().size() == 1 && this.gameManager.getSeekers().contains(p) && !this.gameManager.getCanFirstSeekerMove()) {
                event.setCancelled(true);
            }
        }
    }

}
