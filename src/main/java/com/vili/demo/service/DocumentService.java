package com.vili.demo.service;

import com.vili.demo.api.dto.insert.InsertDocumentDTO;
import com.vili.demo.api.dto.update.UpdateDocumentDTO;
import com.vili.demo.core.repository.IRepository;
import com.vili.demo.core.service.IService;
import com.vili.demo.domain.entity.Document;
import com.vili.demo.domain.entity.Tutor;
import com.vili.demo.domain.repository.DocumentRepository;
import com.vili.demo.domain.repository.TutorRepository;
import com.vili.demo.core.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor @Getter
public class DocumentService implements IService<Document> {

    private final DocumentRepository repository;
    private final TutorRepository tutorRepository;

    public List<Document> findAll() {
        return repository.findAll();
    }

    public Page<Document> findPage(Pageable page) {
        return repository.findAll(page);
    }

    public Document findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Document save(InsertDocumentDTO documentDTO, Tutor tutor) {
        return repository.save(Document.builder()
                .code(documentDTO.getCode())
                .expiration(documentDTO.getExpiration())
                .type(documentDTO.getType())
                .tutor(tutor)
                .build());
    }

    public void update(Long id, UpdateDocumentDTO documentDTO) throws ResourceNotFoundException {
        if (!repository.existsById(id)) throw new ResourceNotFoundException(id);

        Document document = repository.save(Document.builder()
                .id(id)
                .code(documentDTO.getCode())
                .type(documentDTO.getType())
                .expiration(documentDTO.getExpiration())
                .tutor(tutorRepository.findById(documentDTO.getTutorId())
                        .orElseThrow(() -> new ResourceNotFoundException(documentDTO.getTutorId())))
                .build());
    }

    public void delete(Long id) throws ResourceNotFoundException {
        repository.delete(findById(id));
    }
}
