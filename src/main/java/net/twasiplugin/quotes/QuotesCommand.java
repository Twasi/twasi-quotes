package net.twasiplugin.quotes;

import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.customcommands.TwasiCustomCommandEvent;
import net.twasi.core.plugin.api.customcommands.TwasiPluginCommand;

public class QuotesCommand extends TwasiPluginCommand {
    QuotesCommand(TwasiUserPlugin twasiUserPlugin) {
        super(twasiUserPlugin);
    }

    @Override
    public void process(TwasiCustomCommandEvent commandEvent) {
        commandEvent.reply("Not yet implemented :c");
    }

    @Override
    public String getCommandName() {
        return "quotes";
    }

    @Override
    public boolean allowsTimer() {
        return false;
    }

    @Override
    public boolean allowsListing() {
        return false;
    }
}
