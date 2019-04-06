package net.twasiplugin.quotes.web;

import net.twasi.core.database.models.User;
import net.twasi.core.graphql.TwasiCustomResolver;

public class QuotesResolver extends TwasiCustomResolver {

    public QuotesDTO getQuotes(String token) {
        if (token == null || token.equals("")) {
            return new QuotesDTO();
        } else {
            User user = getUser(token);
            if (user == null) return null;

            return new QuotesDTO(user);
        }

    }

}
