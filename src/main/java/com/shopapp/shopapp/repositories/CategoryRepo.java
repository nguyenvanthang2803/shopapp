package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
