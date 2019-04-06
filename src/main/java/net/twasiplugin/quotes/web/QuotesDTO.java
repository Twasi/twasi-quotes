package net.twasiplugin.quotes.web;

import net.twasi.core.database.models.User;
import net.twasi.core.database.repositories.UserRepository;
import net.twasi.core.services.ServiceRegistry;
import net.twasi.core.services.providers.DataService;
import net.twasiplugin.quotes.persistence.QuotesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class QuotesDTO {
    private User user;
    private QuotesRepository repo = ServiceRegistry.get(DataService.class).get(QuotesRepository.class);

    public QuotesDTO() {
    }

    public QuotesDTO(User user) {
        this.user = user;
    }

    public List<QuoteDTO> getPublicAll(String twitchId) {
        User user = ServiceRegistry.get(DataService.class).get(UserRepository.class).getByTwitchId(twitchId);

        if (user == null) return null;

        return repo.getAllForUser(user)
                .stream()
                .map(QuoteDTO::new)
                .collect(Collectors.toList());
    }

}
