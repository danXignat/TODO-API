package com.ignatdan.backend.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService (NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public ResponseEntity<Object> newNote(Note note) {
        noteRepository.save(note);

        return new ResponseEntity<>(note, HttpStatus.CREATED);
    }

    public List<Note> getNotes() {
        return this.noteRepository.findAll();
    }

    public ResponseEntity<Object> updateNote(Integer id, Note updatedNote) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Note existingNote = noteOptional.get();

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        noteRepository.save(existingNote);
        return ResponseEntity.ok(existingNote);
     }

    public ResponseEntity<Object> deleteNote(Integer id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> getNoteById(Integer id) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (!noteOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Note note = noteOptional.get();

        return ResponseEntity.ok(note);
    }
}
