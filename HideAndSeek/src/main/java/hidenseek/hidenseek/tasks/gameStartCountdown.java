package hidenseek.hidenseek.tasks;

import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class gameStartCountdown extends BukkitRunnable {

    private final GameManager gameManager;

    public gameStartCountdown (GameManager gm) {
        this.gameManager = gm;
    }

    private int timeLeft = 11;

    @Override
    public void run() {
        if (this.gameManager.getGameState() != GameState.STOPPED)
        {
            timeLeft--;
            if (timeLeft <= 0) {
                this.gameManager.setGameState(GameState.STARTED);
                cancel();
                return;
            }

            Bukkit.broadcastMessage(ChatColor.YELLOW + "Game Starting in " + ChatColor.WHITE + "" + timeLeft + ChatColor.YELLOW + " seconds");
        } else {
            cancel();
        }
    }
}
