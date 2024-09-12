package com.vili.demo.validation.update;

import com.vili.demo.api.dto.update.UpdateTutorDTO;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.core.request.FieldMessage;
import com.vili.demo.core.validation.AbstractValidator;
import com.vili.demo.validation.update.constraint.ValidUpdateTutor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @EqualsAndHashCode(callSuper = true)
public class UpdateTutorValidator extends AbstractValidator implements ConstraintValidator<ValidUpdateTutor, UpdateTutorDTO> {

    @Override
    public boolean isValid(UpdateTutorDTO dto, ConstraintValidatorContext context) {
        clear();
        setContext(context);
        validate(dto);
        return buildConstraintViolations();
    }

    public List<FieldMessage> validate(UpdateTutorDTO dto) {
        length("name", dto.getName(), 3, 120, true);
        notEmpty("dateOfBirth", dto.getDateOfBirth(), false);
        notNull("type", dto.getType(), true);

        if (dto.getType().equals(TutorType.ADULT)) validateAdult(dto);
        else validateChild(dto);

        return getErrors();
    }

    private void validateAdult(UpdateTutorDTO dto) {
        notEmpty("job", dto.getJob(), false);
    }

    private void validateChild(UpdateTutorDTO dto) {
        notEmpty("school", dto.getSchool(), false);
    }
}
