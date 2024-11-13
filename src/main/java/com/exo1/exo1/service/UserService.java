package com.exo1.exo1.service;

import com.exo1.exo1.dto.UserDto;
import com.exo1.exo1.entity.User;
import com.exo1.exo1.mapper.UserMapper;
import com.exo1.exo1.repository.ProjetRepository;
import com.exo1.exo1.repository.TaskRepository;
import com.exo1.exo1.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private ProjetRepository projetRepository;
    private UserMapper userMapper;

    /**
     * Récupère tous les utilisateurs de manière paginée.
     * Utilise le cache pour stocker les résultats.
     * @param pageable objet de pagination contenant le numéro de page et la taille de page.
     * @return la liste paginée des utilisateurs sous forme de UserDto.
     */
    @Cacheable("usersByPage")
    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto).getContent();
    }

    /**
     * Récupère un utilisateur spécifique par son ID.
     * Cette méthode est mise en cache pour optimiser les performances.
     * @param id l'identifiant de l'utilisateur.
     * @return le UserDto correspondant ou null si non trouvé.
     */
    @Cacheable(value = "userById", key = "#id")
    public UserDto findById(long id) {
        return userMapper.toDto(userRepository.findByIdWithTask(id).orElse(null));
    }

    /**
     * Sauvegarde un nouvel utilisateur avec les informations fournies dans UserDto.
     * Invalide le cache après la sauvegarde.
     * @param userDto les informations de l'utilisateur à sauvegarder.
     * @return le UserDto de l'utilisateur nouvellement créé.
     */
    @CacheEvict(value = {"usersByPage", "userById"}, allEntries = true)
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        if (user.getProjets() == null) {
            user.setProjets(new HashSet<>());
        }

        user.getProjets().forEach(projet -> {
            if (projet.getTasks() == null) {
                projet.setTasks(new HashSet<>());
            }
            projet.getTasks().forEach(task -> task.setProjet(projet));
        });

        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Met à jour un utilisateur existant avec les nouvelles informations fournies.
     * Invalide le cache après la mise à jour.
     * @param id l'ID de l'utilisateur à mettre à jour.
     * @param userDto les nouvelles informations de l'utilisateur.
     * @return le UserDto de l'utilisateur mis à jour.
     */
    @CacheEvict(value = {"usersByPage", "userById"}, allEntries = true)
    public UserDto update(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id " + id));
        userDto.setId(existingUser.getId());
        User userUpdated = userMapper.toEntity(userDto);

        userUpdated.getProjets().forEach(projet -> {
            if (projetRepository.findById(projet.getId()).isPresent()) {
                projet.setUsers(new HashSet<>(Collections.singleton(userUpdated)));
                projet.getTasks().forEach(task -> {
                    if (taskRepository.findById(task.getId()).isPresent()) {
                        task.setProjet(projet);
                    }
                });
            }
        });

        return userMapper.toDto(userRepository.save(userUpdated));
    }

    /**
     * Supprime un utilisateur de la base de données en fonction de son identifiant.
     * Invalide le cache après la suppression.
     * @param id l'identifiant de l'utilisateur à supprimer.
     */
    @CacheEvict(value = {"usersByPage", "userById"}, allEntries = true)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
