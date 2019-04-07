package net.twasiplugin.quotes.persistence;

import net.twasi.core.database.lib.Repository;
import net.twasi.core.database.models.TwitchAccount;
import net.twasi.core.database.models.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class QuotesRepository extends Repository<Quote> {

    /**
     * Gets a quote by a user and numeric id
     * @param user the user
     * @param numId the numeric id
     * @return the quote if found, null otherwise
     */
    public Quote getByNumId(User user, int numId) {
        Query<Quote> q = store.createQuery(Quote.class);

        List<Quote> quotes = q
                .field("numId").equal(numId)
                .field("user").equal(user)
                .asList();

        if (quotes.size() == 0) {
            return null;
        }
        return quotes.get(0);
    }

    public List<Quote> getAllForUser(User user) {
        return store.createQuery(Quote.class)
                .field("user").equal(user)
                .asList();
    }

    public Quote getById(User user, String id) {
        Query<Quote> q = store.createQuery(Quote.class);

        List<Quote> quotes = q
                .field("_id").equal(new ObjectId(id))
                .field("user").equal(user)
                .asList();

        if (quotes.size() == 0) {
            return null;
        }
        return quotes.get(0);
    }

    public String create(User user, String content, String game, TwitchAccount reporter) {
        // Generate new numid
        List<Quote> highestId = store.createQuery(Quote.class)
                .field("user").equal(user)
                .order("-numId")
                .asList();

        int numId = 1;

        if (highestId.size() > 0) {
            numId = highestId.get(0).getNumId() + 1;
        }

        Quote q = new Quote(user, numId, content, game, reporter);
        store.save(q);

        return getByNumId(user, numId).getId().toString();
    }

    public boolean edit(User user, String id, String content) {
        Quote quote = getById(user, id);

        if (quote == null) {
            return false;
        }

        if (content == null || content.length() == 0) {
            return false;
        }

        quote.setContent(content);

        store.save(quote);

        return true;
    }
    public boolean editByNumId(User user, int numId, String content) {
        Quote quote = getByNumId(user, numId);
        return edit(user, quote.getId().toString(), content);
    }

    public boolean delete(User user, String id) {
        Quote quote = getById(user, id);

        if (quote == null) {
            return false;
        }

        store.delete(quote);
        return true;
    }
    public boolean deleteByNumId(User user, int numId) {
        Quote quote = getByNumId(user, numId);
        return delete(user, quote.getId().toString());
    }
}
