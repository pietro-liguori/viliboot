package com.vili.demo.service;

import com.vili.demo.api.dto.insert.InsertOccupationDTO;
import com.vili.demo.api.dto.update.UpdateOccupationDTO;
import com.vili.demo.core.service.IService;
import com.vili.demo.domain.entity.Occupation;
import com.vili.demo.domain.repository.OccupationRepository;
import com.vili.demo.core.exception.ResourceNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor @Getter
public class OccupationService implements IService<Occupation> {

    private final OccupationRepository repository;

    public List<Occupation> findAll() {
        return repository.findAll();
    }

    public Page<Occupation> findPage(Pageable page) {
        return repository.findAll(page);
    }

    public Occupation findById(Long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Occupation save(InsertOccupationDTO occupationDTO) throws ResourceNotFoundException {
        return repository.save(Occupation.builder()
                .name(occupationDTO.getName())
                .description(occupationDTO.getDescription())
                .build());
    }

    public void update(Long id, UpdateOccupationDTO occupationDTO) throws ResourceNotFoundException {
        if (!repository.existsById(id)) throw new ResourceNotFoundException(id);

        Occupation occupation = repository.save(Occupation.builder()
                .id(id)
                .name(occupationDTO.getName())
                .description(occupationDTO.getDescription())
                .build());
    }

    public void delete(Long id) throws ResourceNotFoundException {
        repository.delete(findById(id));
    }
}
