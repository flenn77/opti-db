package com.exo1.exo1.controller;

import com.exo1.exo1.dto.TaskDto;
import com.exo1.exo1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des tâches, offrant des opérations CRUD et une pagination.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Crée une nouvelle tâche en utilisant les informations fournies.
     *
     * @param taskDto Un objet TaskDto contenant les détails de la tâche.
     * @return Une réponse contenant les informations de la tâche ajoutée.
     */
    @PostMapping("/add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.save(taskDto));
    }

    /**
     * Supprime une tâche spécifiée par son ID.
     *
     * @param id L'identifiant unique de la tâche à supprimer.
     * @return Une réponse sans contenu indiquant le succès de la suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Modifie les informations d'une tâche existante.
     *
     * @param id L'identifiant unique de la tâche à modifier.
     * @param taskDto Les nouvelles informations de la tâche.
     * @return Une réponse contenant l'objet TaskDto mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> modifyTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    /**
     * Récupère une liste paginée de tâches.
     *
     * @param page Numéro de la page (défaut : 0).
     * @param size Nombre de tâches par page (défaut : 10).
     * @return Une liste paginée de TaskDto correspondant aux tâches.
     */
    @GetMapping
    public ResponseEntity<List<TaskDto>> listTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(taskService.findAll(pageable));
    }

    /**
     * Récupère une tâche par son identifiant.
     *
     * @param id L'identifiant unique de la tâche.
     * @return Un objet TaskDto avec les détails de la tâche demandée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> fetchTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    /**
     * Recherche une ou plusieurs tâches en fonction de leur titre.
     *
     * @param title Le titre de la tâche à rechercher.
     * @return Une liste de TaskDto correspondant aux tâches trouvées.
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<List<TaskDto>> searchTasksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(taskService.findByTitle(title));
    }
}
