package com.application.notesApplication.Controller;

import com.application.notesApplication.DAO.NotesRepository;
import com.application.notesApplication.DTO.NoteDTO;
import com.application.notesApplication.Model.Note;
import com.application.notesApplication.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NotesController {
    private final NotesRepository notesRepository;
    @GetMapping()
    public ResponseEntity<?> getAllNotes(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Note> notes = notesRepository.findByCreatedBy(user.getUsername());
        if(notes.size()==0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any note for the user");
        return ResponseEntity.ok(notes);
    }
    @PostMapping()
    public ResponseEntity<?> saveNote(@RequestBody NoteDTO noteDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Note note = Note.builder()
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(username).build();
        Note save = notesRepository.save(note);
        return ResponseEntity.status(HttpStatus.CREATED).body("Note Saved successFully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getNoteById(@PathVariable String id){
        Optional<Note> note  = notesRepository.findById(id);
        if(note!=null){
            return ResponseEntity.ok(note);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with requested id not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNoteById(@PathVariable String id){
        Optional<Note> note  = notesRepository.findById(id);
        if(note!=null){
            notesRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note Deleted SUccessfuly");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with requested id not found");
    }
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable String id, @RequestBody NoteDTO update){
        Optional<Note> note  = notesRepository.findById(id);
        if(note!=null){
            Note updated = note.get();
            updated.setTitle(update.getTitle());
            updated.setContent(update.getContent());
            updated.setUpdatedAt(LocalDateTime.now());
            Note Saved = notesRepository.save(updated);
            if(Saved!=null)
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Note updated Successfully");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Note not updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note with requested id not found");
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchByKeyword(@PathVariable String keyword){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Note> found  = notesRepository.findByTitleContainingOrContentContainingAndCreatedBy(keyword,username);
        if(found.size()!=0)
            return ResponseEntity.ok(found);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to find any note with the given keyword");
    }
}
