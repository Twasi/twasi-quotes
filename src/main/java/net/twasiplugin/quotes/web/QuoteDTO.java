package net.twasiplugin.quotes.web;

import net.twasiplugin.quotes.persistence.Quote;

public class QuoteDTO {
    private Quote entity;

    public QuoteDTO(Quote entity) {
        this.entity = entity;
    }

    public String getId() {
        return entity.getId().toString();
    }

    public int getNumId() {
        return entity.getNumId();
    }

    public String getContent() {
        return entity.getContent();
    }

    public String getCreatedAt() {
        return entity.getCreatedAt().toString();
    }

}
