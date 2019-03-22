package net.twasiplugin.quotes.persistence;

import net.twasi.core.database.models.BaseEntity;
import net.twasi.core.database.models.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "net.twasiplugin.quotes", noClassnameStored = true)
public class Quote extends BaseEntity {
    /**
     * Unique ID of the quote
     */
    @Id
    private ObjectId id;

    /**
     * Reference to the user the quote belongs to
     */
    @Reference
    private User user;

    /**
     * The numeric id of the quote
     */
    private int numId;

    /**
     * The content
     */
    private String content;

    /**
     * Creates an empty quote. Used by database.
     */
    public Quote() {}

    public Quote(User user, int numId, String content) {
        this.user = user;
        this.numId = numId;
        this.content = content;
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
