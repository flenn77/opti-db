package com.exo1.exo1.controller;

import com.exo1.exo1.dto.UserDto;
import com.exo1.exo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des utilisateurs, offrant des endpoints pour les opérations CRUD et la pagination.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Crée un nouvel utilisateur en utilisant les informations fournies dans UserDto.
     *
     * @param userDto Un objet UserDto contenant les informations de l'utilisateur à ajouter.
     * @return un objet UserDto contenant les détails de l'utilisateur créé.
     */
    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.save(userDto));
    }

    /**
     * Supprime un utilisateur de la base de données en fonction de son ID.
     *
     * @param id L'identifiant unique de l'utilisateur à supprimer.
     * @return une réponse sans contenu indiquant le succès de l'opération.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Met à jour les informations d'un utilisateur existant.
     *
     * @param id L'identifiant unique de l'utilisateur à modifier.
     * @param userDto Un objet UserDto contenant les nouvelles informations de l'utilisateur.
     * @return une réponse contenant l'objet UserDto mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> modifyUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    /**
     * Récupère la liste des utilisateurs avec une pagination configurable.
     *
     * @param page Numéro de la page de résultats (par défaut 0).
     * @param size Nombre d'utilisateurs par page (par défaut 10).
     * @return une liste d'objets UserDto correspondant aux utilisateurs de la page demandée.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    /**
     * Récupère un utilisateur par son identifiant unique.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @return un objet UserDto contenant les informations de l'utilisateur, s'il est trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
