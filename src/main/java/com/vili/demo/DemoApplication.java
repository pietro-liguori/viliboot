package com.vili.demo;

import com.vili.demo.api.dto.insert.InsertDocumentDTO;
import com.vili.demo.api.dto.insert.InsertOccupationDTO;
import com.vili.demo.api.dto.insert.InsertPetDTO;
import com.vili.demo.api.dto.insert.InsertTutorDTO;
import com.vili.demo.domain.entity.*;
import com.vili.demo.domain.enums.DocumentType;
import com.vili.demo.domain.enums.PetType;
import com.vili.demo.domain.enums.TutorType;
import com.vili.demo.domain.repository.DocumentRepository;
import com.vili.demo.domain.repository.RepositoryBundle;
import com.vili.demo.service.OccupationService;
import com.vili.demo.service.PetService;
import com.vili.demo.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private OccupationService occupationService;
	@Autowired
	private PetService petService;
	@Autowired
	private TutorService tutorService;

	@Autowired
	private RepositoryBundle repositoryBundle;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Calendar cal = Calendar.getInstance();
		Date dateOfBirth;
		Date expirationDate;

		Occupation occ1 = occupationService.save(InsertOccupationDTO.builder()
				.name("Teacher")
				.description("Teach")
				.build());

		Occupation occ2 = occupationService.save(InsertOccupationDTO.builder()
				.name("Student")
				.description("Study")
				.build());

		Occupation occ3 = occupationService.save(InsertOccupationDTO.builder()
				.name("UBER Driver")
				.description("Drive")
				.build());

		Occupation occ4 = occupationService.save(InsertOccupationDTO.builder()
				.name("Nurse")
				.description("Nurse")
				.build());

		cal.set(1988, Calendar.FEBRUARY, 1, 0, 0,0);
		dateOfBirth = cal.getTime();
		cal.set(2030, Calendar.JANUARY, 1, 0, 0,0);
		expirationDate = cal.getTime();
		Tutor tut1 = tutorService.save(InsertTutorDTO.builder()
				.name("John")
				.dateOfBirth(cal.getTime())
				.type(TutorType.ADULT)
				.job("Teacher")
				.nicknames(List.of("Johnny", "John John"))
				.occupationIds(List.of(occ1.getId(), occ3.getId()))
				.document(InsertDocumentDTO.builder()
						.code("11111111111")
						.expiration(expirationDate)
						.type(DocumentType.CPF)
						.build())
				.build());

		cal.set(2010, Calendar.OCTOBER, 21);
		dateOfBirth = cal.getTime();
		Tutor tut2 = tutorService.save(InsertTutorDTO.builder()
				.name("Dean")
				.dateOfBirth(dateOfBirth)
				.type(TutorType.CHILD)
				.school("Maple Bear")
				.nicknames(List.of("Little Dean"))
				.occupationIds(List.of(occ2.getId()))
				.document(InsertDocumentDTO.builder()
						.code("22222222222")
						.expiration(expirationDate)
						.type(DocumentType.CPF)
						.build())
				.build());

		cal.set(1998, Calendar.FEBRUARY, 14);
		dateOfBirth = cal.getTime();
		Tutor tut3 = tutorService.save(InsertTutorDTO.builder()
				.name("Anna")
				.dateOfBirth(dateOfBirth)
				.type(TutorType.ADULT)
				.job("Nurse")
				.occupationIds(List.of(occ4.getId()))
				.document(InsertDocumentDTO.builder()
						.code("33333333333")
						.expiration(expirationDate)
						.type(DocumentType.CPF)
						.build())
				.build());

		cal.set(2019, Calendar.MARCH, 2);
		dateOfBirth = cal.getTime();
		Pet pet1 = petService.save(InsertPetDTO.builder()
				.name("Rex")
				.breed("Pit Bull")
				.dateOfBirth(dateOfBirth)
				.type(PetType.DOG)
				.tutorId(tut1.getId())
				.build());

		cal.set(2022, Calendar.DECEMBER, 6);
		dateOfBirth = cal.getTime();
		Pet pet2 = petService.save(InsertPetDTO.builder()
				.name("Mimi")
				.breed("Persian")
				.dateOfBirth(dateOfBirth)
				.type(PetType.CAT)
				.tutorId(tut2.getId())
				.build());
	}
}
