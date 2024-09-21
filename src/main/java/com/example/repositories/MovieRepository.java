/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.repositories;

import com.example.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author HighCore
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
}
