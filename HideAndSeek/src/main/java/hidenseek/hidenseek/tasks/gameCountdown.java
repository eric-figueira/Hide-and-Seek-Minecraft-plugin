package hidenseek.hidenseek.tasks;

import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class gameCountdown extends BukkitRunnable {

    private GameManager gameManager;

    public gameCountdown(GameManager gm) {
        this.gameManager = gm;
    }

    private final int minutes = 10;
    private int timeLeft = 60 * minutes + 1;

    public int getTimeLeft() { return this.timeLeft; }

    public void run() {
        this.timeLeft--;
        if (this.timeLeft <= 0) {
            this.gameManager.setIsGameFinished(true);
            this.gameManager.setGameState(GameState.STOPPED);
            cancel();
            return;
        }

        if (this.timeLeft % 120 == 0) Bukkit.broadcastMessage(ChatColor.YELLOW + "The game ends in " + ChatColor.WHITE + "" + (timeLeft / 60) + ChatColor.YELLOW + " minutes");
        if (this.timeLeft == 60) Bukkit.broadcastMessage(ChatColor.YELLOW + "The game ends in " + ChatColor.WHITE + "1" + ChatColor.YELLOW + " minute");
        if (this.timeLeft <= 15) Bukkit.broadcastMessage(ChatColor.YELLOW + "The game ends in " + ChatColor.WHITE + "" + timeLeft + ChatColor.YELLOW + " seconds");
    }
}
