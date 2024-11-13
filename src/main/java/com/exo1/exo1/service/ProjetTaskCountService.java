package com.exo1.exo1.service;

import com.exo1.exo1.dto.ProjetTaskCountDto;
import com.exo1.exo1.mapper.ProjetTaskCountMapper;
import com.exo1.exo1.repository.ProjetTaskCountRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjetTaskCountService {

    private final ProjetTaskCountRepository projetTaskCountRepository;
    private final ProjetTaskCountMapper projetTaskCountMapper;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Récupère une page de données paginées sur le nombre de tâches par projet.
     * Utilise le mapper pour convertir chaque entité en DTO.
     *
     * @param pageable l'objet de pagination contenant le numéro de page et la taille de la page.
     * @return une page de ProjetTaskCountDto représentant le nombre de tâches par projet.
     */
    @Cacheable("projetTaskCounts")
    public Page<ProjetTaskCountDto> findAll(Pageable pageable) {
        return projetTaskCountRepository.findAll(pageable)
                .map(projetTaskCountMapper::toDto);
    }

    /**
     * Rafraîchit la vue matérialisée projet_task_count dans la base de données.
     * Vide également le cache lié au nombre de tâches par projet pour garantir que les données sont à jour.
     */
    @CacheEvict(value = "projetTaskCounts", allEntries = true)
    public void refreshMaterializedView() {
        jdbcTemplate.execute("REFRESH MATERIALIZED VIEW projet_task_count");
    }
}
