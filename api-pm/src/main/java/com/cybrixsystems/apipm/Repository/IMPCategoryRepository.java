package com.cybrixsystems.apipm.Repository;

import java.util.List;
import java.util.Optional;

import com.cybrixsystems.apipm.Data;
import com.cybrixsystems.apipm.Model.Category;

public class IMPCategoryRepository implements CategoryRepository{

    @Override
    public List<Category> findAll() {
        System.out.println("IMPCategoryRepository.findAll");
    return Data.CATEGORIES;    
    }

    @Override
    public Optional<Category> findById(Long idC) {
        System.out.println("IMPCategoryRepository.findById: " + idC );
       return this.findAll()
                .stream()
                .filter(e -> e.getIdCategory().compareTo(idC) == 0)
                .findFirst();   
    }

    @Override
    public Optional<Category> findByName(String name) {
        System.out.println("IMPCategoryRepository.findByName: " + name );
       return this.findAll()
                .stream()
                .filter(e -> e.getName().compareTo(name) == 0)
                .findFirst();
    }

    @Override
    public Category save(Category category) {
        System.out.println("IMPCategoryRepository.save");
        category.setIdCategory(Long.valueOf(Data.CATEGORIES.size() + 1));
    return category;    
    }

    @Override
    public void deleteById(Long idC) {
        System.out.println("IMPCategoryRepository.deleteById");
        Optional<Category> cat = this.findById(idC);
        if(cat.isPresent()){
            System.err.println("LLEGUEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            Data.CATEGORIES.remove(cat.get());
            System.err.println("IMPCategoryRepository.deleteById COMPLETE DELETE");
        }
    }
    
}
