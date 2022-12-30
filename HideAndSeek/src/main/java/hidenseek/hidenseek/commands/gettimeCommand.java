package hidenseek.hidenseek.commands;

import hidenseek.hidenseek.HideNSeek;
import hidenseek.hidenseek.managers.GameManager;
import hidenseek.hidenseek.managers.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class gettimeCommand implements CommandExecutor {

    private final GameManager gameManager;

    public gettimeCommand (GameManager gm) {
        this.gameManager = gm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (this.gameManager.getGameCountdown() != null && this.gameManager.getGameState() == GameState.STARTED) {
            int minutes = this.gameManager.getGameCountdown().getTimeLeft() / 60;
            int seconds = this.gameManager.getGameCountdown().getTimeLeft() % 60;
            sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "There are " + ChatColor.RESET + "" +
                    ChatColor.WHITE + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds + ChatColor.YELLOW + "" + ChatColor.BOLD + " left!");
        }
        else sender.sendMessage(ChatColor.RED + "There is no game started");

        return true;
    }
}
