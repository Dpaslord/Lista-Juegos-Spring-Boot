package org.example.helloworldspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/web")
class WebController {

    private final GameRepository gameRepository;

    public WebController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @ModelAttribute("platforms")
    public List<String> getPlatforms() {
        return gameRepository.findDistinctPlatforms();
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        return "index";
    }

    @GetMapping("/index/{platform}")
    public String filterByPlatform(@PathVariable String platform, Model model) {
        model.addAttribute("games", gameRepository.findByPlatformContainingIgnoreCase(platform));
        model.addAttribute("selectedPlatform", platform);
        return "index";
    }

    @GetMapping("/juego/{id}")
    public String juego(@PathVariable Long id, Model model) {
        return gameRepository.findById(id).map(game -> {
            model.addAttribute("game", game);
            return "juego";
        }).orElseGet(() -> {
            model.addAttribute("error", "No existe el juego con ID: " + id);
            return "error";
        });
    }
}