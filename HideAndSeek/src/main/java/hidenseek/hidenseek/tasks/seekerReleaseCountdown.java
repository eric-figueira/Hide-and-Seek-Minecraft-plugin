package hidenseek.hidenseek.tasks;

import hidenseek.hidenseek.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class seekerReleaseCountdown extends BukkitRunnable {

    private final GameManager gameManager;

    public seekerReleaseCountdown(GameManager gm) {
        this.gameManager = gm;
    }

    private int timeLeft = 31;

    public void run() {
        this.timeLeft--;
        if (this.timeLeft <= 0) {
            this.gameManager.setCanFirstSeekerMove(true);
            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "The seeker was released!");
            this.gameManager.startGameCountdown();
            cancel();
            return;
        }

        if (this.timeLeft % 5 == 0) Bukkit.broadcastMessage(ChatColor.YELLOW + "The seeker is being released in " + ChatColor.WHITE + "" + timeLeft + ChatColor.YELLOW + " seconds");
        if (this.timeLeft < 5) Bukkit.broadcastMessage(ChatColor.YELLOW + "The seeker is being released in " + ChatColor.WHITE + "" + timeLeft + ChatColor.YELLOW + " seconds");
    }
}
