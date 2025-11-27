package com.example.assignment_three_zelora;

import com.example.assignment_three_zelora.model.entitys.Category;
import com.example.assignment_three_zelora.model.repos.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
}
