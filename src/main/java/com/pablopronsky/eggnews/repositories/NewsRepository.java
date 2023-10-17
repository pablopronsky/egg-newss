package com.pablopronsky.eggnews.repositories;

import com.pablopronsky.eggnews.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n where n.title LIKE CONCAT('%',:title,'%')")
    public List<News> searchNewsByTitle(@Param("title") String title);

}
