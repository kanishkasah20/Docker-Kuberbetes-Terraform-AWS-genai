package com.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
