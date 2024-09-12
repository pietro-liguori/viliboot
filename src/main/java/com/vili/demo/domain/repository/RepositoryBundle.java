package com.vili.demo.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class RepositoryBundle {

    private final DocumentRepository documentRepository;
    private final OccupationRepository occupationRepository;
    private final PetRepository petRepository;
    private final TutorRepository tutorRepository;

    public Object get(String entity) {
        return switch (entity.toLowerCase()) {
            case "document" -> documentRepository;
            case "occupation" -> occupationRepository;
            case "pet" -> petRepository;
            case "tutor" -> tutorRepository;
            default -> throw new IllegalArgumentException("Repository not found in RepositoryBundle: " + entity.toLowerCase() + "Repository");
        };
    }
}
