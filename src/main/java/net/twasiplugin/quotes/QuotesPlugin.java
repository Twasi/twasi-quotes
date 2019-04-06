package net.twasiplugin.quotes;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasiplugin.quotes.web.QuotesResolver;

public class QuotesPlugin extends TwasiPlugin {
    @Override
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return QuotesUserPlugin.class;
    }

    @Override
    public GraphQLQueryResolver getGraphQLResolver() {
        return new QuotesResolver();
    }
}
