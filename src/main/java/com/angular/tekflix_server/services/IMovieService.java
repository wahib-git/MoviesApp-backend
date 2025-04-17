package com.angular.tekflix_server.services;


import com.angular.tekflix_server.models.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IMovieService {

    public List<Movie> getAllMovies();

    public Optional<Movie> getMovieById(Long id);

    public Movie createMovie(Movie movie);

    public Movie updateMovie(Movie movie);

    public void deleteMovie(Long id);

    public String saveFile(MultipartFile image) throws IOException;
}
