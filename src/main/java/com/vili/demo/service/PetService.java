package com.vili.demo.service;

import com.vili.demo.api.dto.insert.InsertPetDTO;
import com.vili.demo.api.dto.update.UpdatePetDTO;
import com.vili.demo.core.service.IService;
import com.vili.demo.domain.entity.*;
import com.vili.demo.domain.enums.PetType;
import com.vili.demo.domain.repository.CatRepository;
import com.vili.demo.domain.repository.DogRepository;
import com.vili.demo.domain.repository.PetRepository;
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
public class PetService implements IService<Pet> {

    private final PetRepository repository;
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final TutorService tutorService;

    public List<Pet> findAll() {
        return repository.findAll();
    }

    public Page<Pet> findPage(Pageable page) {
        return repository.findAll(page);
    }

    public Pet findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Pet save(InsertPetDTO petDTO) throws ResourceNotFoundException {
        return switch (petDTO.getType()) {
            case CAT -> repository.save(Cat.builder()
                    .name(petDTO.getName())
                    .breed(petDTO.getBreed())
                    .dateOfBirth(petDTO.getDateOfBirth())
                    .type(PetType.CAT)
                    .tutor(tutorService.findById(petDTO.getTutorId()))
                    .build());
            case DOG -> repository.save(Dog.builder()
                    .name(petDTO.getName())
                    .breed(petDTO.getBreed())
                    .dateOfBirth(petDTO.getDateOfBirth())
                    .type(PetType.DOG)
                    .tutor(tutorService.findById(petDTO.getTutorId()))
                    .build());
        };
    }

    @Transactional
    public void update(Long id, UpdatePetDTO petDTO) throws ResourceNotFoundException {
        switch (petDTO.getType()) {
            case CAT:
                if (!catRepository.existsById(id)) throw new ResourceNotFoundException(id);

                Cat cat = repository.save(Cat.builder()
                        .id(id)
                        .name(petDTO.getName())
                        .breed(petDTO.getBreed())
                        .dateOfBirth(petDTO.getDateOfBirth())
                        .type(PetType.DOG)
                        .tutor(tutorService.findById(petDTO.getTutorId()))
                        .build());
            case DOG:
                if (!dogRepository.existsById(id)) throw new ResourceNotFoundException(id);

                Dog dog = repository.save(Dog.builder()
                        .id(id)
                        .name(petDTO.getName())
                        .breed(petDTO.getBreed())
                        .dateOfBirth(petDTO.getDateOfBirth())
                        .type(PetType.DOG)
                        .tutor(tutorService.findById(petDTO.getTutorId()))
                        .build());
        }
    }

    public void delete(Long id) throws ResourceNotFoundException {
        repository.delete(findById(id));
    }
}
