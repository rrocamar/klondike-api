package es.upm.miw.klondike.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "game")
public class SavedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    @Lob
    private Serializable gameMemento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serializable getGameMemento() {
        return gameMemento;
    }

    public void setGameMemento(Serializable gameMemento) {
        this.gameMemento = gameMemento;
    }
}
