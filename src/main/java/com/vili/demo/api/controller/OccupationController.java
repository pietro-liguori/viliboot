package com.vili.demo.api.controller;

import com.vili.demo.api.dto.insert.InsertOccupationDTO;
import com.vili.demo.api.dto.update.UpdateOccupationDTO;
import com.vili.demo.domain.entity.Occupation;
import com.vili.demo.core.exception.ResourceNotFoundException;
import com.vili.demo.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/occupations")
@RequiredArgsConstructor
public class OccupationController {

    private final OccupationService service;

    @GetMapping
    public ResponseEntity<List<Occupation>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pages/{page}")
    public ResponseEntity<Page<Occupation>> findPage(@PathVariable int page, @RequestParam String size) {
        try {
            return ResponseEntity.ok(service.findPage(PageRequest.of(page - 1, Objects.equals(size, "") ? 10 : Integer.parseInt(size))));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Occupation> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Occupation> save(@RequestBody InsertOccupationDTO body) {
        try {
            return ResponseEntity.ok(service.save(body));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Occupation> update(@PathVariable Long id, @RequestBody UpdateOccupationDTO body) {
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
    public ResponseEntity<Occupation> delete(@PathVariable Long id) {
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
