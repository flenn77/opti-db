package com.exo1.exo1.controller;

import com.exo1.exo1.dto.ProjetDto;
import com.exo1.exo1.service.ProjetService;
import com.exo1.exo1.service.ProjetTaskCountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations de projets.
 */
@RestController
@RequestMapping("/projets")
@AllArgsConstructor
public class ProjetController {

    @Autowired
    private ProjetService projetService;
    private ProjetTaskCountService projetTaskCountService;

    /**
     * Crée un nouveau projet et rafraîchit la vue matérialisée de comptage des tâches.
     *
     * @param projetDto les informations du projet à créer.
     * @return la réponse contenant le projet nouvellement créé.
     */
    @PostMapping("/add")
    public ResponseEntity<ProjetDto> createProjet(@RequestBody ProjetDto projetDto) {
        ProjetDto savedProject = projetService.save(projetDto);
        projetTaskCountService.refreshMaterializedView();
        return ResponseEntity.ok(savedProject);
    }

    /**
     * Supprime un projet spécifique par son identifiant.
     *
     * @param id identifiant du projet à supprimer.
     * @return une réponse sans contenu après suppression.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjet(@PathVariable Long id) {
        projetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère les informations d'un projet spécifique par son identifiant.
     *
     * @param id l'ID du projet.
     * @return réponse contenant les détails du projet.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjetDto> getProjetById(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findById(id));
    }

    /**
     * Récupère une liste de projets par nom.
     *
     * @param name nom du projet à rechercher.
     * @return réponse contenant les projets correspondant au nom.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProjetDto>> getProjetsByName(@PathVariable String name) {
        return ResponseEntity.ok(projetService.findByName(name));
    }

    /**
     * Récupère les projets correspondant à un critère de test.
     *
     * @param name critère de test pour le projet.
     * @return réponse contenant la liste des projets associés au critère.
     */
    @GetMapping("/test/{name}")
    public ResponseEntity<List<ProjetDto>> getProjetsByTest(@PathVariable String name) {
        return ResponseEntity.ok(projetService.findByTest(name));
    }

    /**
     * Obtenez une liste de projets paginée.
     *
     * @param page numéro de la page, par défaut 0.
     * @param size nombre d'éléments par page, par défaut 10.
     * @return réponse contenant la liste paginée des projets.
     */
    @GetMapping
    public ResponseEntity<List<ProjetDto>> getAllProjetsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(projetService.findAll(pageable));
    }

    /**
     * Met à jour les informations d'un projet.
     *
     * @param id l'ID du projet à mettre à jour.
     * @param projetDto nouvelles informations du projet.
     * @return réponse contenant le projet mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjetDto> updateProjet(@PathVariable Long id, @RequestBody ProjetDto projetDto) {
        return ResponseEntity.ok(projetService.update(id, projetDto));
    }
}
