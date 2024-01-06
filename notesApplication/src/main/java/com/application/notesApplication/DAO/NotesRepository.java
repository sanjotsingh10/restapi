package com.application.notesApplication.DAO;

import com.application.notesApplication.Model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotesRepository extends MongoRepository<Note,String> {
    List<Note> findByCreatedBy(String createdBy);
    Optional<Note> findById(String id);

    @Query("{'id': ?0}")
    Note updateNote(String id, String title, String content, LocalDateTime updatedAt);

    @Query("{'$and': [{'$or': [{'title': {'$regex': ?0, '$options': 'i'}}, {'content': {'$regex': ?0, '$options': 'i'}}]}, {'createdBy': ?1}]}")
    List<Note> findByTitleContainingOrContentContainingAndCreatedBy(String Keyword, String username);
}
