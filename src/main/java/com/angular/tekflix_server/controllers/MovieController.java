package com.angular.tekflix_server.controllers;

import com.angular.tekflix_server.models.Movie;
import com.angular.tekflix_server.services.IMovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Autorise les requêtes provenant de localhost:4200
@RequestMapping("/api/v0/movies")
public class MovieController {

    private final IMovieService movieService;
    @Value("${file.upload-dir}")
    public String uploadDir;


    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }
    /**
     * Récupère tous les films avec leurs URLs d'image complètes.
     */
    @GetMapping
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        // Ajouter l'URL complète pour chaque image
        movies.forEach(movie -> {
            if (movie.getImage() != null) {
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(movie.getImage())
                        .toUriString();
                movie.setImage(imageUrl);
            }
        });

        return movies;
    }
    /**
     * Crée un nouveau film avec l'image uploadée.
     */
    @PostMapping
    public Movie createMovie(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("genre") String genre,
            @RequestParam("rating") String rating,
            @RequestParam("year") String year,
            @RequestParam("isNew") boolean isNew,
            @RequestParam("trailerUrl") String trailerUrl,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        // Save the file if provided
        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            imagePath = movieService.saveFile(image);
        }

        // Créer un objet Movie
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setGenre(genre);
        movie.setRating(rating);
        movie.setYear(year);
        movie.setNew(isNew);
        movie.setTrailerUrl(trailerUrl);
        movie.setImage(imagePath); // Enregistrer le nom de fichier

        return movieService.createMovie(movie);
    }

    /**
     * Supprime un film et son image associée.
     */
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));

        // Supprimer l'image associée au film
        Path filePath = Paths.get(uploadDir + movie.getImage());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'image", e);
        }

        // Supprimer le film de la base de données
        movieService.deleteMovie(id);
    }


    @GetMapping("/{id}")
    public Optional<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("genre") String genre,
            @RequestParam("rating") String rating,
            @RequestParam("year") String year,
            @RequestParam("isNew") boolean isNew,
            @RequestParam("trailerUrl") String trailerUrl,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Movie existingMovie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé avec l'id : " + id));

        // Mise à jour des champs texte
        existingMovie.setTitle(title);
        existingMovie.setDescription(description);
        existingMovie.setGenre(genre);
        existingMovie.setRating(rating);
        existingMovie.setYear(year);
        existingMovie.setNew(isNew);
        existingMovie.setTrailerUrl(trailerUrl);

        // Gestion de l'image (optionnelle)
        if (image != null && !image.isEmpty()) {
            // Supprimer l'ancienne image si elle existe
            String oldImageName = existingMovie.getImage();
            if (oldImageName != null) {
                Path oldImagePath = Paths.get(uploadDir + oldImageName);
                try {
                    Files.deleteIfExists(oldImagePath);
                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors de la suppression de l'ancienne image", e);
                }
            }

            // Sauvegarder la nouvelle image
            String newImageName = movieService.saveFile(image);
            existingMovie.setImage(newImageName);
        }

        // Sauvegarder les modifications
        return movieService.createMovie(existingMovie); // ou updateMovie selon votre service
    }

//    @GetMapping
//    public List<Movie> getAllMovies() {
//        return movieService.getAllMovies();
//    }
//
//    @PostMapping
//    public Movie createMovie(@RequestBody Movie movie) {
//        return movieService.createMovie(movie);
//    }
//
//    @PutMapping("/{id}")
//    public Movie updateMovie(@PathVariable Long id , @RequestBody Movie movie) {
//        return movieService.updateMovie(movie);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMovie(@PathVariable Long id) {
//        movieService.deleteMovie(id);
//    }


}
