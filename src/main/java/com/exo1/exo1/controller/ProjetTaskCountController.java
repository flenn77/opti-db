package com.exo1.exo1.controller;

import com.exo1.exo1.dto.ProjetTaskCountDto;
import com.exo1.exo1.service.ProjetTaskCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour récupérer les statistiques de comptage de tâches pour chaque projet.
 */
@RestController
@RequestMapping("/count")
public class ProjetTaskCountController {

    @Autowired
    private ProjetTaskCountService projetTaskCountService;

    /**
     * Récupère une page de projets avec le nombre de tâches associées, selon la pagination.
     *
     * @param page le numéro de la page (par défaut 0).
     * @param size la taille de la page (par défaut 10).
     * @return une réponse HTTP contenant une page de projets avec leur nombre de tâches.
     */
    @GetMapping("/all")
    public ResponseEntity<Page<ProjetTaskCountDto>> getAllCountsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(projetTaskCountService.findAll(pageRequest));
    }
}
