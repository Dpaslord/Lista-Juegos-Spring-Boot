package org.example.helloworldspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface GameRepository extends JpaRepository<Game, Long> {
    public List<Game> findByPlatformContainingIgnoreCase(String platform);

    @Query("SELECT DISTINCT g.platform FROM Game g")
    List<String> findDistinctPlatforms();
}
