package com.cybrixsystems.apipm.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Service.CategoryService;

@RestController
@RequestMapping("/apiPM/Categories")
public class CategoryController {
    @Autowired
    private CategoryService cs;

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories(){
        return ResponseEntity.ok(cs.findAllCategories());
    }

    @GetMapping("/{idC}")
    public ResponseEntity<Category> findByIdCategory(@PathVariable("idC") Long idC){
        Optional<Category> category = cs.findCategoryById(idC);
        if(category.isPresent()){
            return ResponseEntity.ok(category.orElseThrow());
        }else{
             return ResponseEntity.notFound().build();   
        }
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        Category catSaved = cs.saveORupdateCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(catSaved);  
    }

    @PutMapping("/{idC}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category
                                                    ,@PathVariable("idC")Long idC){
        category.setIdCategory(idC);
        Category catUpd = cs.saveORupdateCategory(category);
        if(! category.equals(catUpd)){
            return ResponseEntity.ok(catUpd);
        }else{
            return ResponseEntity.notFound().build();
        }                                                
    }

    @DeleteMapping("/{idC}")
    public ResponseEntity<?> deleteCategory(@PathVariable("idC")Long idC){
        return (cs.deleteCategoryById(idC)) ? 
                ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
}
