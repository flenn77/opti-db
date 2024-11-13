package com.exo1.exo1.service;

import com.exo1.exo1.dto.TaskDto;
import com.exo1.exo1.entity.Task;
import com.exo1.exo1.mapper.TaskMapper;
import com.exo1.exo1.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private TaskMapper taskMapper;

    /**
     * Récupère toutes les tâches sous forme paginée.
     * Utilise la pagination pour limiter le nombre de résultats retournés.
     *
     * @param pageable objet contenant les informations de pagination (page et taille).
     * @return une liste paginée de tâches sous forme de DTO.
     */
    @Cacheable("tasks")
    public List<TaskDto> findAll(Pageable pageable) {
        return taskMapper.toDtos(taskRepository.findAll(pageable).getContent());
    }

    /**
     * Récupère les tâches par titre.
     * Cherche toutes les tâches dont le titre correspond au paramètre donné.
     *
     * @param title le titre des tâches à rechercher.
     * @return une liste de TaskDto dont le titre correspond.
     */
    @Cacheable(value = "tasksByTitle", key = "#title")
    public List<TaskDto> findByTitle(String title) {
        return taskMapper.toDtos(taskRepository.findByTitle(title));
    }

    /**
     * Récupère une tâche spécifique par son ID.
     * Si la tâche est trouvée, elle est convertie en DTO ; sinon, retourne null.
     *
     * @param id l'identifiant de la tâche.
     * @return le TaskDto correspondant ou null si la tâche n'existe pas.
     */
    @Cacheable(value = "taskById", key = "#id")
    public TaskDto findById(long id) {
        return taskMapper.toDto(taskRepository.findById(id).orElse(null));
    }

    /**
     * Sauvegarde une nouvelle tâche dans la base de données.
     * Convertit d'abord le TaskDto en entité avant de le sauvegarder.
     * Invalide le cache après la création d'une nouvelle tâche.
     *
     * @param taskDto les informations de la tâche à sauvegarder.
     * @return le TaskDto de la tâche nouvellement créée.
     */
    @CacheEvict(value = {"tasks", "tasksByTitle", "taskById"}, allEntries = true)
    public TaskDto save(TaskDto taskDto) {
        return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
    }

    /**
     * Met à jour une tâche existante avec les nouvelles informations.
     * Si la tâche n'existe pas, lève une exception NotFoundException.
     * Invalide le cache après la mise à jour de la tâche.
     *
     * @param id l'identifiant de la tâche à mettre à jour.
     * @param taskDto les nouvelles informations de la tâche.
     * @return le TaskDto de la tâche mise à jour.
     */
    @CacheEvict(value = {"tasks", "tasksByTitle", "taskById"}, allEntries = true)
    public TaskDto update(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id " + id));
        taskDto.setId(existingTask.getId());
        return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
    }

    /**
     * Supprime une tâche par son identifiant.
     * Invalide le cache après la suppression de la tâche.
     *
     * @param id l'identifiant de la tâche à supprimer.
     */
    @CacheEvict(value = {"tasks", "tasksByTitle", "taskById"}, allEntries = true)
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
