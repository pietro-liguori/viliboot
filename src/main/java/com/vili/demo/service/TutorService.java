package com.vili.demo.service;

import com.vili.demo.api.dto.insert.InsertTutorDTO;
import com.vili.demo.api.dto.update.UpdateTutorDTO;
import com.vili.demo.core.service.IService;
import com.vili.demo.domain.entity.*;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.domain.repository.*;
import com.vili.demo.exception.InternalErrorException;
import com.vili.demo.core.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor @Getter
public class TutorService implements IService<Tutor> {

    private final TutorRepository repository;
    private final AdultRepository adultRepository;
    private final ChildRepository childRepository;
    private final DocumentRepository documentRepository;
    private final OccupationRepository occupationRepository;

    public List<Tutor> findAll() {
        return repository.findAll();
    }

    public Page<Tutor> findPage(Pageable page) {
        return repository.findAll(page);
    }

    public Tutor findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Tutor save(InsertTutorDTO tutorDTO) throws ResourceNotFoundException, InternalErrorException {
        List<Occupation> occupations = tutorDTO.getOccupationIds().stream()
                .map(occId -> occupationRepository
                        .findById(occId)
                        .orElseThrow(( ) -> new ResourceNotFoundException(occId)))
                .toList();

        switch (tutorDTO.getType()) {
            case ADULT:
                Adult adult = repository.save(Adult.builder()
                        .name(tutorDTO.getName())
                        .type(TutorType.ADULT)
                        .dateOfBirth(tutorDTO.getDateOfBirth())
                        .job(tutorDTO.getJob())
                        .nicknames(tutorDTO.getNicknames())
                        .occupations(occupations)
                        .build());

                adult.setDocument(documentRepository.save(Document.builder()
                        .code(tutorDTO.getDocument().getCode())
                        .expiration(tutorDTO.getDocument().getExpiration())
                        .type(tutorDTO.getDocument().getType())
                        .tutor(adult)
                        .build()));

                return adult;
            case CHILD:
                Child child = repository.save(Child.builder()
                        .name(tutorDTO.getName())
                        .type(TutorType.CHILD)
                        .dateOfBirth(tutorDTO.getDateOfBirth())
                        .school(tutorDTO.getSchool())
                        .nicknames(tutorDTO.getNicknames())
                        .occupations(occupations)
                        .build());

                child.setDocument(documentRepository.save(Document.builder()
                        .code(tutorDTO.getDocument().getCode())
                        .expiration(tutorDTO.getDocument().getExpiration())
                        .type(tutorDTO.getDocument().getType())
                        .tutor(child)
                        .build()));

                return child;
        }

        throw new InternalErrorException();
    }

    @Transactional
    public void update(Long id, UpdateTutorDTO tutorDTO) throws ResourceNotFoundException {
        switch (tutorDTO.getType()) {
            case ADULT:
                if (!adultRepository.existsById(id)) throw new ResourceNotFoundException(id);

                Adult adult = repository.save(Adult.builder()
                        .id(id)
                        .name(tutorDTO.getName())
                        .type(TutorType.ADULT)
                        .dateOfBirth(tutorDTO.getDateOfBirth())
                        .job(tutorDTO.getJob())
                        .build());
            case CHILD:
                    if (!childRepository.existsById(id)) throw new ResourceNotFoundException(id);

                    Child child = repository.save(Child.builder()
                        .id(id)
                        .name(tutorDTO.getName())
                        .type(TutorType.CHILD)
                        .dateOfBirth(tutorDTO.getDateOfBirth())
                        .school(tutorDTO.getJob())
                        .build());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        repository.delete(findById(id));
    }
}
