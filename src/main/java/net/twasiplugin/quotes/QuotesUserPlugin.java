package net.twasiplugin.quotes;

import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.customcommands.TwasiPluginCommand;
import net.twasi.core.plugin.api.events.TwasiInstallEvent;

import java.util.ArrayList;
import java.util.List;

public class QuotesUserPlugin extends TwasiUserPlugin {

    private List<TwasiPluginCommand> commands = new ArrayList<>();

    public QuotesUserPlugin() {
        commands.add(new QuotesCommand(this));
    }

    @Override
    public void onInstall(TwasiInstallEvent e) {
        e.getDefaultGroup().addKey("net.twasi.quotes.read");
        e.getModeratorsGroup().addKey("net.twasi.quotes.*");
    }

    @Override
    public void onUninstall(TwasiInstallEvent e) {
        e.getDefaultGroup().removeKey("net.twasi.quotes.read");
        e.getModeratorsGroup().removeKey("net.twasi.quotes.*");
    }

    @Override
    public List<TwasiPluginCommand> getCommands() {
        return commands;
    }
}
