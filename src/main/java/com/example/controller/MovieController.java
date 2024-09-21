/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.models.Movie;
import com.example.repositories.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/movies")
public class MovieController {
    
    @Autowired
    private MovieRepository movieRepository;
    
    @GetMapping("/all")
    public List<Movie> getAllMovies (){
        return movieRepository.findAll();
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Movie> getMovieByID(@PathVariable  Long id){
      Optional<Movie>  movie = movieRepository.findById(id);
      return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    
    @GetMapping("/create")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie savedMovie= movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
        
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Movie> deleteMovieByID(@PathVariable Long id){
        if(!movieRepository.existsById(id)){
             return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
           return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovieByID(@PathVariable Long id,@RequestBody Movie movie){
         if(!movieRepository.existsById(id)){
             return ResponseEntity.notFound().build();
        }
         
         movie.setId(id);
         Movie updated= movieRepository.save(movie);
         return ResponseEntity.ok(updated);
    }
    
    
    @PutMapping("/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie (@PathVariable Long id,@PathVariable double rating){
      if(!movieRepository.existsById(id)){
             return ResponseEntity.notFound().build();
        }
      Optional<Movie> optional = movieRepository.findById(id);
      Movie movie = optional.get();
      double newRating = ((movie.getVotes()*movie.getRating())+rating)/(movie.getVotes()+1);
      movie.setVotes(movie.getVotes()+1);
      movie.setRating(newRating);
      Movie saved = movieRepository.save(movie);
        return ResponseEntity.ok(saved);
    }
}
