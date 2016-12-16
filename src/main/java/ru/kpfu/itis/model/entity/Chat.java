package ru.kpfu.itis.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.internal.Objects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Set<Message> messageSet = new LinkedHashSet<>();

    private Set<User> userSet = new LinkedHashSet<>();

    private boolean isNew = true;


    public Chat() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @JsonProperty("new")
    @Column(name = "new")
    public boolean getIsNew() {
        return isNew;
    }

    @JsonProperty("created")
    @Column(name = "created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("updated")
    @Column(name = "updated_at")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @OrderBy("sentAt desc")
    @JsonProperty("messages")
    @Fetch(value = FetchMode.JOIN)
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL /*, before @Fetch fetch = FetchType.EAGER*/)
    public Set<Message> getMessageSet() {
        return messageSet;
    }

    @JsonProperty("users")
    @ManyToMany
    @Fetch(value = FetchMode.JOIN)
    public Set<User> getUserSet() {
        return userSet;
    }

    public void setIsNew(boolean aNew) {
        isNew = aNew;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    public void addMessage(Message message) {
        message.setChat(this);
        this.messageSet.add(message);
    }

    @Transient
    @JsonProperty("lastMessage")
    public Message getLastMessage() {
        if (messageSet.isEmpty()) return null;
        return messageSet.iterator().hasNext() ? messageSet.iterator().next() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;
        if (createdAt != null ? !createdAt.equals(chat.createdAt) : chat.createdAt != null) return false;
        return updatedAt != null ? updatedAt.equals(chat.updatedAt) : chat.updatedAt == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(createdAt, updatedAt);
    }
}
