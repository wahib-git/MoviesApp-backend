# Tekflix Server

Backend RESTful API développé avec **Spring Boot**, fournissant la gestion des films (CRUD) avec support d’upload d’images et intégration avec une base de données MySQL/MariaDB.

---

## Table des matières

- [Présentation](#présentation)  
- [Fonctionnalités](#fonctionnalités)  
- [Technologies utilisées](#technologies-utilisées)  
- [Installation](#installation)  
- [Configuration](#configuration)  
- [Utilisation](#utilisation)  
- [API Endpoints](#api-endpoints)  
- [Tests](#tests)  
- [Contribuer](#contribuer)  
- [Licence](#licence)  

---

## Présentation

Tekflix Server est une API backend conçue pour gérer une collection de films, avec la possibilité d’ajouter, modifier, supprimer et récupérer des films. Chaque film peut être associé à une image (affiche) et une URL de bande-annonce. Le projet suit l’architecture MVC avec Spring Boot, JPA/Hibernate et une base de données relationnelle.

---

## Fonctionnalités

- CRUD complet pour les films (Create, Read, Update, Delete)  
- Upload et gestion des images associées aux films  
- Génération dynamique des URLs d’accès aux images  
- Support CORS pour intégration avec un frontend Angular (localhost:4200)  
- Pool de connexions optimisé avec HikariCP  
- Configuration flexible via `application.properties`  

---

## Technologies utilisées

- Java 17+  
- Spring Boot 3.x  
- Spring Data JPA (Hibernate)  
- MariaDB / MySQL  
- Lombok  
- Maven  
- MultipartFile pour upload d’images  
- Angular (frontend séparé, non inclus)  

---

## Installation

### Prérequis

- Java JDK 17 ou supérieur  
- Maven 3.x  
- MariaDB ou MySQL installé et configuré  
- IDE (IntelliJ IDEA, Eclipse, VSCode, etc.)  

### Étapes

1. Clonez le dépôt : git clone https://github.com/votre-utilisateur/tekflix_server.git
cd tekflix_server
   
2. Configurez la base de données :  
- Créez une base `tekflix` dans MariaDB/MySQL  
- Mettez à jour les paramètres dans `src/main/resources/application.properties` (URL, utilisateur, mot de passe)

3. Construisez le projet :  mvn clean install

4. Lancez l’application :  mvn spring-boot:run
   
---

## Configuration

Dans `application.properties`, configurez notamment :
spring.datasource.url=jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MariaDB103Dialect

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

file.upload-dir=specifier le shemins vres le repertoire destiné pour l'importation des images (`file.upload-dir`)

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2

---

## Utilisation

- L’API est accessible par défaut sur `http://localhost:8081/api/v0/movies`  
- Les images uploadées sont stockées dans le dossier configuré (`file.upload-dir`)  
- Les URLs des images sont générées automatiquement dans les réponses JSON  

---

## API Endpoints

| Méthode | URL                      | Description                      | Paramètres / Body                      |
|---------|--------------------------|--------------------------------|--------------------------------------|
| GET     | `/api/v0/movies`         | Récupérer tous les films       | Aucun                               |
| GET     | `/api/v0/movies/{id}`    | Récupérer un film par ID       | `id` (path variable)                 |
| POST    | `/api/v0/movies`         | Créer un nouveau film          | `multipart/form-data` avec champs : `title`, `description`, `genre`, `rating`, `year`, `isNew`, `trailerUrl`, `image` (fichier) |
| PUT     | `/api/v0/movies/{id}`    | Mettre à jour un film existant | `id` (path variable), JSON ou form-data selon implémentation |
| DELETE  | `/api/v0/movies/{id}`    | Supprimer un film              | `id` (path variable)                 |

---

## Tests

- Tests unitaires et d’intégration à compléter selon besoins  
- Utilisez Postman ou Insomnia pour tester les endpoints REST  
- Exemple de test POST avec upload d’image via `form-data`  

---

## Contribuer

Les contributions sont les bienvenues !  
- Forkez le projet  
- Créez une branche feature (`git checkout -b feature/ma-fonctionnalite`)  
- Commitez vos modifications (`git commit -m 'Ajout de ma fonctionnalité'`)  
- Poussez la branche (`git push origin feature/ma-fonctionnalite`)  
- Ouvrez une Pull Request  

---

## Licence


---

**Merci d’utiliser Tekflix Server !**  
Pour toute question, contactez [wahibbachoua95@gmail.com].




