package com.vili.demo.api.controller;

import com.vili.demo.api.dto.insert.InsertTutorDTO;
import com.vili.demo.api.dto.update.UpdateTutorDTO;
import com.vili.demo.domain.entity.Tutor;
import com.vili.demo.core.exception.ResourceNotFoundException;
import com.vili.demo.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService service;

    @GetMapping
    public ResponseEntity<List<Tutor>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<Page<Tutor>> findPage(@PathVariable int page, @RequestParam String size) {
        try {
            return ResponseEntity.ok(service.findPage(PageRequest.of(page - 1, Objects.equals(size, "") ? 10 : Integer.parseInt(size))));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Tutor> save(@RequestBody @Valid InsertTutorDTO body) {
        try {
            return ResponseEntity.ok(service.save(body));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> update(@PathVariable Long id, @RequestBody @Valid UpdateTutorDTO body) {
        try {
            service.update(id, body);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tutor> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
