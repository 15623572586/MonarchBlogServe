package com.Dao;

import com.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article,String> {
    @Query(nativeQuery = true,value = "SELECT * FROM article ORDER BY create_time DESC ")
    List<Article> findAll();
    List<Article> findAllByUserIdOrderByCreateTimeDesc(String userId);
}
