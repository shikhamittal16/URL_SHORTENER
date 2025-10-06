package com.example.URL_Shortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends JpaRepository<URL, Integer> {
}
