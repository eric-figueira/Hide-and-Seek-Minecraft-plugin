package hidenseek.hidenseek.commands;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class startCommand implements CommandExecutor {

    private final GameManager gameManager;
    private final HideNSeek plugin;

    public startCommand (HideNSeek pl, GameManager gm) {
        this.plugin = pl;
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Testing if the spawns have been set
        Location f = this.plugin.getConfig().getLocation("firstspawn");
        Location o = this.plugin.getConfig().getLocation("othersspawn");

        if (f == null) Bukkit.broadcastMessage(ChatColor.RED + "The seekers's spawn has not been set yet!");
        else if (o == null) Bukkit.broadcastMessage(ChatColor.RED + "The hiders's spawn has not been set yet!");
        else {
            if (this.gameManager.getGameState() == GameState.STARTED || this.gameManager.getGameState() == GameState.STARTING)
                sender.sendMessage(ChatColor.RED + "This game is already started! Please stop the game before starting another one");
            else {
                if (!this.gameManager.getCanFirstSeekerMove()) // this means is seeker is still frozen, so the game cannot restart
                this.gameManager.setGameState(GameState.STARTING);
            }
        }

        return true;
    }
}
