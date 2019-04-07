package net.twasiplugin.quotes;

import net.twasi.core.database.models.User;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.customcommands.TwasiCustomCommandEvent;
import net.twasi.core.plugin.api.customcommands.TwasiPluginCommand;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasi.twitchapi.TwitchAPI;
import net.twasiplugin.quotes.persistence.Quote;
import net.twasiplugin.quotes.persistence.QuotesRepository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

public class QuotesCommand extends TwasiPluginCommand {
    private QuotesRepository repo = ServiceRegistry.get(DataService.class).get(QuotesRepository.class);
    private TwasiUserPlugin plugin;
    private Random rand = new Random();

    private SimpleDateFormat format;

    QuotesCommand(TwasiUserPlugin twasiUserPlugin) {
        super(twasiUserPlugin);
        this.plugin = twasiUserPlugin;
    }

    @Override
    public void process(TwasiCustomCommandEvent commandEvent) {
        User user = commandEvent.getStreamer().getUser();
        format = new SimpleDateFormat(getTranslation("quotes.output.dateformat"));

        if (!commandEvent.hasArgs()) {
            if (!user.hasPermission(commandEvent.getSender(), "net.twasi.quotes.read")) {
                return;
            }

            List<Quote> quotes = repo.getAllForUser(plugin.getTwasiInterface().getStreamer().getUser());

            if (quotes.size() == 0) {
                commandEvent.reply(getTranslation("quotes.none"));
                return;
            }

            outputQuote(commandEvent, quotes.get(rand.nextInt(quotes.size())));
        }

        if (commandEvent.hasArgs()) {
            // is it numeric?
            if (commandEvent.getArgsAsOne().matches("^\\d+$")) {
                if (!user.hasPermission(commandEvent.getSender(), "net.twasi.quotes.read")) {
                    return;
                }

                int id = Integer.valueOf(commandEvent.getArgs().get(0));

                Quote quote = repo.getByNumId(user, id);

                if (quote == null) {
                    commandEvent.reply(getTranslation("quotes.notfound", id));
                    return;
                }

                outputQuote(commandEvent, quote);
            } else {

                if (!user.hasPermission(commandEvent.getSender(), "net.twasi.quotes.manage")) {
                    return;
                }

                // Edit
                if (commandEvent.getArgs().get(0).equalsIgnoreCase("edit")) {
                    if (commandEvent.getArgs().size() < 3) {
                        commandEvent.reply(getTranslation("quotes.edit.syntax"));
                        return;
                    }
                    if (!commandEvent.getArgs().get(1).matches("^\\d+$")) {
                        commandEvent.reply(getTranslation("quotes.numberformat"));
                        return;
                    }

                    int id = Integer.valueOf(commandEvent.getArgs().get(1));

                    String newContent = commandEvent.getArgsAsOne().split(" ", 3)[2];

                    repo.editByNumId(user, id, newContent);

                    outputQuote(commandEvent, repo.getByNumId(user, id));
                }

                // Create
                if (commandEvent.getArgs().get(0).equalsIgnoreCase("add")) {
                    String content = commandEvent.getArgsAsOne().split(" ", 2)[1];

                    String game = TwitchAPI.kraken().channels().withAuth(user.getTwitchAccount().toAuthContext()).updateChannel(null, null).getGame();

                    String id = repo.create(user, content, game, commandEvent.getSender());

                    if (id == null) {
                        throw new RuntimeException("Failed to create quote");
                    }

                    outputQuote(commandEvent, repo.getById(user, id));
                }
            }
        }
    }

    public void outputQuote(TwasiCustomCommandEvent commandEvent, Quote quote) {
        commandEvent.reply(getTranslation(
                "quotes.output",
                quote.getContent(),
                quote.getUser().getTwitchAccount().getDisplayName(),
                format.format(quote.getCreatedAt()),
                quote.getGame(),
                quote.getReporter().getDisplayName(),
                quote.getNumId()
        ));
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
