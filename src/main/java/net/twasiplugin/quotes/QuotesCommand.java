package net.twasiplugin.quotes;

import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.customcommands.TwasiCustomCommandEvent;
import net.twasi.core.plugin.api.customcommands.TwasiPluginCommand;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasiplugin.quotes.persistence.Quote;
import net.twasiplugin.quotes.persistence.QuotesRepository;

import java.util.List;

public class QuotesCommand extends TwasiPluginCommand {
    QuotesRepository repo = ServiceRegistry.get(DataService.class).get(QuotesRepository.class);
    TwasiUserPlugin plugin;

    QuotesCommand(TwasiUserPlugin twasiUserPlugin) {
        super(twasiUserPlugin);
        this.plugin = twasiUserPlugin;
    }

    @Override
    public void process(TwasiCustomCommandEvent commandEvent) {
        if (!commandEvent.hasArgs()) {
            List<Quote> quotes = repo.getAllForUser(plugin.getTwasiInterface().getStreamer().getUser());
        }
    }

    @Override
    public String getCommandName() {
        return "quote";
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
