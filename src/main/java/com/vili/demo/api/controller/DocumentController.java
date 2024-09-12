package com.vili.demo.api.controller;

import com.vili.demo.api.dto.DocumentDTO;
import com.vili.demo.api.dto.update.UpdateDocumentDTO;
import com.vili.demo.core.controller.IController;
import com.vili.demo.domain.entity.Document;
import com.vili.demo.core.exception.ResourceNotFoundException;
import com.vili.demo.service.DocumentService;
import com.vili.demo.service.TutorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor @Getter
public class DocumentController implements IController<Document, DocumentDTO> {

    private final DocumentService service;
    private final TutorService tutorService;

    @Override
    public Class<Document> getEntityType() {
        return Document.class;
    }

//    @GetMapping
//    public ResponseEntity<List<Document>> findAll() {
//        try {
//            return ResponseEntity.ok(service.findAll());
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @GetMapping("/pages/{page}")
//    public ResponseEntity<Page<Document>> findPage(@PathVariable int page, @RequestParam String size) {
//        try {
//            return ResponseEntity.ok(service.findPage(PageRequest.of(page - 1, Objects.equals(size, "") ? 10 : Integer.parseInt(size))));
//        } catch (NumberFormatException e) {
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Document> findById(@PathVariable Long id) {
//        try {
//            return ResponseEntity.ok(service.findById(id));
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Document> update(@PathVariable Long id, @RequestBody UpdateDocumentDTO body) {
//        try {
//            service.update(id, body);
//            return ResponseEntity.noContent().build();
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Document> delete(@PathVariable Long id) {
//        try {
//            service.delete(id);
//            return ResponseEntity.noContent().build();
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }

    //*********************************************************

    @GetMapping("/{id}")
    public ResponseEntity<Document>findById(@PathVariable Object id) {
        return IController.super.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<Document>> findAll() {
        return IController.super.findAll();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Document>> findPage(HttpServletRequest request) {
        return IController.super.findPage(request);
    }

    @PostMapping
    public ResponseEntity<Document> insert(@RequestBody @Valid DocumentDTO dto) {
        return IController.super.insert(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> update(@RequestBody @Valid DocumentDTO dto, @PathVariable Object id) {
        return IController.super.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Object id) {
        return IController.super.delete(id);
    }
}
