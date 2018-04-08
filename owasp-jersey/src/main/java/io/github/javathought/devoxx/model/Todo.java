package io.github.javathought.devoxx.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.UUID;

/**
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Todo {
    private long id;
    @XmlTransient
    private UUID uuid;
    private long userId;
    private String summary;
    private String description;
    private boolean publique;

    private Todo() {
        // Needed to Unmarshall
    }

    private Todo(long id, UUID uuid, long userId, String summary, String description, boolean publique) {
        this.id = id;
        this.uuid = uuid;
        this.userId = userId;
        this.summary = summary;
        this.description = description;
        this.publique = publique;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getPublique() {
        return publique;
    }

    public void setPublique(Boolean publique) {
        this.publique = publique;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uuid", uuid)
                .append("userId", userId)
                .append("summary", summary)
                .append("description", description)
                .toString();
    }

    public static class TodoBuilder {
        private long id;
        private UUID uuid;
        private long userId;
        private String summary;
        private String description;
        private Boolean publique;


        public TodoBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TodoBuilder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public TodoBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public TodoBuilder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public TodoBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TodoBuilder setPublique(boolean publique) {
            this.publique = publique;
            return this;
        }

        public Todo build() {
            return new Todo(id, uuid, userId, summary, description, publique);
        }
    }
}
