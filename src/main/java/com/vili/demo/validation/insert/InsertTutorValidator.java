package com.vili.demo.validation.insert;

import java.util.List;

import com.vili.demo.api.dto.insert.InsertTutorDTO;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.core.request.FieldMessage;
import com.vili.demo.core.validation.AbstractValidator;
import com.vili.demo.validation.insert.constraint.ValidInsertTutor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true)
public class InsertTutorValidator extends AbstractValidator implements ConstraintValidator<ValidInsertTutor, InsertTutorDTO> {

    @Override
    public boolean isValid(InsertTutorDTO dto, ConstraintValidatorContext context) {
        clear();
        setContext(context);
        validate(dto);
        return buildConstraintViolations();
    }

    public List<FieldMessage> validate(InsertTutorDTO dto) {
        length("name", dto.getName(), 3, 120, true);
        notEmpty("nicknames", dto.getNicknames(), false);
        notEmpty("dateOfBirth", dto.getDateOfBirth(), false);
        notNull("type", dto.getType(), true);
        notEmpty("occupationIds",dto.getOccupationIds(), false);

        if (dto.getType().equals(TutorType.ADULT)) validateAdult(dto);
        else validateChild(dto);

        return getErrors();
    }

    private void validateAdult(InsertTutorDTO dto) {
        notEmpty("job", dto.getJob(), true);
    }

    private void validateChild(InsertTutorDTO dto) {
        notEmpty("school", dto.getSchool(), true);
    }
}
