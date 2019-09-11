package com.Dao;

import com.Entity.ImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUrlDao extends JpaRepository<ImageUrl,String> {
}
