package net.ojhp.dare.controllers;

import net.ojhp.dare.models.Page;
import net.ojhp.dare.repositories.CmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CmsRepository cmsRepository;

    @Autowired
    public HomeController(CmsRepository cmsRepository) {
        this.cmsRepository = cmsRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Page> pages = this.cmsRepository.getAll(Page.class);
        pages.sort(Comparator.comparingInt(Page::getOrder));
        model.addAttribute("pages", pages);

        return "index";
    }
}
