package com.exo1.exo1.service;

import com.exo1.exo1.dto.ProjetDto;
import com.exo1.exo1.entity.Projet;
import com.exo1.exo1.mapper.ProjetMapper;
import com.exo1.exo1.repository.ProjetRepository;
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
public class ProjetService {
    private final ProjetRepository projetRepository;
    private final ProjetMapper projetMapper;
    private final TaskRepository taskRepository;

    /**
     * Récupère une liste paginée de projets.
     * Utilise le mapper pour transformer les entités en DTO.
     * @param pageable l'objet de pagination contenant le numéro de page et la taille de la page.
     * @return une liste de ProjetDto correspondant à la page demandée.
     */
    public List<ProjetDto> findAll(Pageable pageable) {
        return projetMapper.toDtos(projetRepository.findAll(pageable).getContent());
    }

    /**
     * Récupère un projet spécifique par son ID.
     * Cette méthode est mise en cache pour réduire les appels à la base de données.
     * @param id l'identifiant du projet.
     * @return le ProjetDto correspondant, ou null si le projet n'existe pas.
     */
    @Cacheable(value = "projetsById", key = "#id")
    public ProjetDto findById(long id) {
        return projetMapper.toDto(projetRepository.findById(id).orElse(null));
    }

    /**
     * Récupère une liste de projets en fonction d'un critère de test basé sur le nom.
     * Cette méthode est mise en cache pour réduire les appels à la base de données.
     * @param name le nom du test.
     * @return une liste de ProjetDto correspondant au critère fourni.
     */
    @Cacheable(value = "projetsByTest", key = "#name")
    public List<ProjetDto> findByTest(String name) {
        return projetMapper.toDtos(projetRepository.findByTest(name));
    }

    /**
     * Récupère une liste de projets correspondant à un nom donné.
     * Cette méthode est mise en cache pour réduire les appels à la base de données.
     * @param name le nom du projet.
     * @return une liste de ProjetDto correspondant au nom spécifié.
     */
    @Cacheable(value = "projetsByName", key = "#name")
    public List<ProjetDto> findByName(String name) {
        return projetMapper.toDtos(projetRepository.findByName(name));
    }

    /**
     * Crée un nouveau projet et initialise les relations de tâches si présentes.
     * Met à jour le cache après l'ajout d'un projet.
     * @param projetDto les informations du projet à créer.
     * @return le ProjetDto du projet nouvellement créé.
     */
    @CacheEvict(value = {"projetsById", "projetsByName", "projetsByTest"}, allEntries = true)
    public ProjetDto save(ProjetDto projetDto) {
        Projet projet = projetMapper.toEntity(projetDto);
        if (projet.getTasks() != null) {
            projet.getTasks().forEach(t -> t.setProjet(projet));
        }
        return projetMapper.toDto(projetRepository.save(projet));
    }

    /**
     * Met à jour un projet existant en utilisant les nouvelles informations fournies.
     * Actualise le cache après la mise à jour.
     * @param id l'ID du projet à mettre à jour.
     * @param projetDto les informations mises à jour du projet.
     * @return le ProjetDto du projet mis à jour.
     */
    @CacheEvict(value = {"projetsById", "projetsByName", "projetsByTest"}, allEntries = true)
    public ProjetDto update(Long id, ProjetDto projetDto) {
        Projet existingProjet = projetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet not found with id " + id));
        projetDto.setId(existingProjet.getId());
        Projet projetUpdated = projetMapper.toEntity(projetDto);
        if (projetUpdated.getTasks() != null) {
            projetUpdated.getTasks().forEach(t -> {
                if (taskRepository.existsById(t.getId())) {
                    t.setProjet(projetUpdated);
                }
            });
        }
        return projetMapper.toDto(projetRepository.save(projetUpdated));
    }

    /**
     * Supprime un projet en fonction de son identifiant.
     * Supprime les entrées du cache associées après la suppression.
     * @param id l'identifiant du projet à supprimer.
     */
    @CacheEvict(value = {"projetsById", "projetsByName", "projetsByTest"}, allEntries = true)
    public void delete(Long id) {
        projetRepository.deleteById(id);
    }
}
