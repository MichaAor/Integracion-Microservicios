package com.cybrixsystems.apipm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cybrixsystems.apipm.Model.Category;
import com.cybrixsystems.apipm.Repository.CategoryRepository;

@Service
public class IMPCategoryService implements CategoryService{
    @Autowired
    private CategoryRepository cr;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return cr.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findCategoryById(Long idC) {
        return cr.findById(idC);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findCategoryByName(String name) {
        return cr.findByName(name);
    }

    @Override
    @Transactional
    public Category saveORupdateCategory(Category category) {
        if(category.getIdCategory() != null){
            Optional<Category> catExist = cr.findById(category.getIdCategory());
            if(catExist.isPresent()){
                /*
                 ? Utilizo el método map de Optional para transformar el objeto "catToUpd" 
                 ? copiando los valores relevantes de "category" a través de la función lambda.
                */
                Category catToUpd = catExist.map(c ->{
                                                    c.setName(category.getName());
                                                    c.setProducts(category.getProducts());
                                                    return c;
                                                }).get();
                return cr.save(catToUpd);
            }else{
                return cr.save(category);
            }
        }
        return cr.save(category);
    }

    @Override
    @Transactional
    public boolean deleteCategoryById(Long idC) {
        cr.deleteById(idC);
        boolean deleted = cr.findById(idC).isEmpty();
    return deleted;       
    }
}
