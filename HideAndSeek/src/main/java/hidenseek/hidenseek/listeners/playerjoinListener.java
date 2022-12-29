package hidenseek.hidenseek.listeners;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class playerjoinListener implements Listener {

    private final HideNSeek plugin;
    private final GameManager gameManager;

    public playerjoinListener (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    private Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Team t = this.score.getTeam("hidenames");
        if (t == null) {
            t = score.registerNewTeam("hidenames");
            t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }

        t.addEntry(event.getPlayer().getDisplayName());

        if (this.gameManager.getGameState() == GameState.STARTED) {
            event.getPlayer().getInventory().clear();
            event.getPlayer().sendMessage(ChatColor.YELLOW + "The game have already started, so you are now a seeker!");
            this.gameManager.makePlayerSeeker(event.getPlayer());
        }
    }
}
