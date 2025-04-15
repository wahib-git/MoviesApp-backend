package com.angular.tekflix_server.services;

import com.angular.tekflix_server.models.Movie;
import com.angular.tekflix_server.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class MovieService implements  IMovieService{

    private final MovieRepository movieRepository;

    //private static final String UPLOAD_DIR = "C:\\Users\\Mss\\IdeaProjects\\tekflix_server\\images"; // Update this with your upload directory path

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

//    public String saveFile(MultipartFile file) throws IOException {
//        // Ensure the upload directory exists
//        Path uploadPath = Paths.get(UPLOAD_DIR);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Save the file to the upload directory
//        String filename = file.getOriginalFilename();
//        Path filePath = Paths.get(UPLOAD_DIR, filename);
//        Files.write(filePath, file.getBytes());
//
//        return UPLOAD_DIR+"/"+filename;
//    }
}
