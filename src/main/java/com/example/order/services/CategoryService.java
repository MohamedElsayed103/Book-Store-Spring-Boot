package com.example.order.services;

import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Category;
import com.example.order.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public Category addCategory(Category category, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail)) throw new RuntimeException("User is not admin");
        Category category1 = Category.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
        return categoryRepository.save(category1);
    }

    public void deleteCategory(Category category, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail)) throw new RuntimeException("user is not admin");
        categoryRepository.delete(category);
    }

   /* public Category getCategoryByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isPresent())
            return category.get();
        else try {
            throw new OptionalEmpty("No such category found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }*/
   /* public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
*/
    public Category getCategoryById(String id)  {

        Optional<Category> category =categoryRepository.findById(id);

        if (category.isPresent())
            return category.get();
        else try {
            throw new OptionalEmpty("No such category found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }
}
