package com.vili.demo.validation;

import com.vili.demo.api.dto.TutorDTO;
import com.vili.demo.core.validation.AbstractValidator;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.core.request.FieldMessage;
import com.vili.demo.validation.constraint.ValidTutor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
public class TutorValidator extends AbstractValidator implements ConstraintValidator<ValidTutor, TutorDTO> {

    @Override
    public boolean isValid(TutorDTO dto, ConstraintValidatorContext context) {
        clear();
        setContext(context);
        validate(dto);
        return buildConstraintViolations();
    }

    public List<FieldMessage> validate(TutorDTO dto) {
        notEmpty("id", dto.getId(), dto.isUpdate());
        length("name", dto.getName(), 3, 120, dto.isInsert());
        notEmpty("nicknames", dto.getNicknames(), false);
        notEmpty("dateOfBirth", dto.getDateOfBirth(), dto.isInsert());
        notNull("type", dto.getType(), dto.isInsert());
        notEmpty("occupationIds",dto.getOccupationIds(), false);
        notEmpty("job", dto.getJob(), dto.getType().equals(TutorType.ADULT));
        notEmpty("school", dto.getSchool(), dto.getType().equals(TutorType.CHILD));
        return getErrors();
    }
}
