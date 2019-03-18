package net.twasiplugin.quotes;

import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;

public class QuotesPlugin extends TwasiPlugin {
    @Override
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return QuotesUserPlugin.class;
    }
}
