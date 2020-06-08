package es.upm.miw.klondike.daos;

import es.upm.miw.klondike.dtos.GameMinimunDto;
import es.upm.miw.klondike.models.SavedGame;
import es.upm.miw.klondike.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameDao extends JpaRepository<SavedGame, Long> {

    List<GameMinimunDto> findSavedGameByUser(User user);
    Optional<SavedGame> findByIdAndUser(Long id, User user);
    Optional<SavedGame> findByNameAndUser(String name, User user);
}
