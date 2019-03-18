package net.twasiplugin.quotes;

import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.customcommands.TwasiPluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuotesUserPlugin extends TwasiUserPlugin {

    private List<TwasiPluginCommand> commands = new ArrayList<>();

    public QuotesUserPlugin() {
        commands.add(new QuotesCommand(this));
    }

    @NotNull
    @Override
    public List<TwasiPluginCommand> getCommands() {
        return commands;
    }
}
