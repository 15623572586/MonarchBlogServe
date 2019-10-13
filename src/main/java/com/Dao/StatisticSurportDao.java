package com.Dao;

import com.Entity.StatisticSurport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticSurportDao extends JpaRepository<StatisticSurport,String> {
    StatisticSurport findAllByArticleIdAndUserId(String articleId,String userId);
}
