package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import tr.edu.ogu.ceng.dao.FacultyRepository;
import tr.edu.ogu.ceng.dao.FacultySupervisorRepository;
import tr.edu.ogu.ceng.enums.UserType;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.model.Faculty;
import tr.edu.ogu.ceng.model.FacultySupervisor;
import tr.edu.ogu.ceng.model.Language;
import tr.edu.ogu.ceng.model.User;

public class FacultySupervisorTest {

	@Mock
	FacultySupervisorRepository facultySupervisorRepository;

	@Mock
	UserService userService;

	@Mock
	FacultyRepository facultyRepository;

	FacultySupervisorService facultySupervisorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		facultySupervisorService = new FacultySupervisorService(facultySupervisorRepository, userService,
				new ModelMapper(), new MessageResource(null));
	}

	@Test
	void is_faculty_supervisor_added_successfully() {

		LocalDateTime localDateTime = LocalDateTime.now();

		var modelFaculty = new Faculty(1L, "Faculty", localDateTime, localDateTime);
		var modelUser = new User(3L, "password", "email", UserType.FACULTYSUPERVISOR, localDateTime, localDateTime,
				new Language(1L, "Türkçe", "tr", "tr", "TR", "Türkiye"), true);
		var modelFacultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
				localDateTime, modelUser, modelFaculty);

		when(facultyRepository.save(any(Faculty.class))).thenReturn(modelFaculty);
		when(userService.addUser(any(User.class))).thenReturn(modelUser);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(modelFacultySupervisor);

		var facultySupervisor = new FacultySupervisor(4L, "Name", "Surname", "Phone", "No", localDateTime,
				localDateTime, modelUser, modelFaculty);

		var actual = facultySupervisorService.addFacultySupervisor(facultySupervisor);

		assertNotNull(actual);
		assertEquals(modelFacultySupervisor.getId(), actual.getId());
		assertEquals(modelFacultySupervisor.getName(), actual.getName());
		assertEquals(modelFacultySupervisor.getSurname(), actual.getSurname());
		assertEquals(modelFacultySupervisor.getPhoneNumber(), actual.getPhoneNumber());
		assertEquals(modelFacultySupervisor.getSupervisorNo(), actual.getSupervisorNo());
	}

	@Test
	void testDeleteSuccess() {
		long Id = 1L;

		when(facultySupervisorRepository.existsById(Id)).thenReturn(true);

		doNothing().when(facultySupervisorRepository).deleteById(Id);

		boolean deleteResult = facultySupervisorService.deleteFacultySupervisor(Id);

		// Assert that deleteUser returned true
		assertTrue(deleteResult);

		// Verify that userRepository.deleteById was called with the correct userId
		verify(facultySupervisorRepository).deleteById(Id);
	}

	@Test
	void testDeleteFacultySupervisor_Failure_DataIntegrityViolationException() {
		long id = 123;
		when(facultySupervisorRepository.existsById(id)).thenReturn(true);
		doThrow(new DataIntegrityViolationException("Integrity violation")).when(facultySupervisorRepository)
				.deleteById(id);

		assertFalse(facultySupervisorService.deleteFacultySupervisor(id));
	}

	@Test
	void testDeleteUnexpectedException() {
		long id = 4L;

		when(facultySupervisorRepository.existsById(id)).thenReturn(true);

		doThrow(new EmptyResultDataAccessException(0)).when(facultySupervisorRepository).deleteById(id);

		assertThrows(EmptyResultDataAccessException.class, () -> facultySupervisorService.deleteFacultySupervisor(id));
	}

	@Test
	void testGetFacultySupervisor_Success() {
		// Test data
		Long id = 123L;
		FacultySupervisor supervisor = new FacultySupervisor();
		supervisor.setId(id);
		// Mocking repository behavior
		Mockito.when(facultySupervisorRepository.existsById(id)).thenReturn(true);
		Mockito.when(facultySupervisorRepository.getById(id)).thenReturn(supervisor);
		// Test
		FacultySupervisor result = facultySupervisorService.getFacultySupervisor(id);
		// Assertion
		assertNotNull(result);
		assertEquals(id, result.getId());
		Mockito.verify(facultySupervisorRepository, Mockito.times(1)).existsById(id);
		Mockito.verify(facultySupervisorRepository, Mockito.times(1)).getById(id);
	}

	@Test
	void testGetFacultySupervisor_Failure_Exception() {
		// Test data
		Long id = 123L;
		// Mocking repository behavior
		Mockito.when(facultySupervisorRepository.existsById(id)).thenReturn(true);
		Mockito.when(facultySupervisorRepository.getById(id))
				.thenThrow(new RuntimeException("Database connection error"));
		// Test & Assertion
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			facultySupervisorService.getFacultySupervisor(id);
		});
		assertEquals("Database connection error", exception.getMessage());
		Mockito.verify(facultySupervisorRepository, Mockito.times(1)).existsById(id);
		Mockito.verify(facultySupervisorRepository, Mockito.times(1)).getById(id);
	}

	@Test
	void testUpdateFacultySupervisor_Success() {
		// Mock verileri oluştur
		FacultySupervisor facultySupervisor = new FacultySupervisor();
		facultySupervisor.setId(1L);
		User user = new User();
		user.setId(1L);
		facultySupervisor.setUser(user);

		// Repository mock'ını ayarla
		when(facultySupervisorRepository.existsById(1L)).thenReturn(true);
		when(facultySupervisorRepository.getById(1L)).thenReturn(facultySupervisor);
		when(facultySupervisorRepository.save(any(FacultySupervisor.class))).thenReturn(facultySupervisor);

		// UserService mock'ını ayarla
		when(userService.GetUserById(1L)).thenReturn(user);

		// Metodu çağır ve sonucu kontrol et
		FacultySupervisor updatedFacultySupervisor = facultySupervisorService
				.updateFacultySupervisor(facultySupervisor);
		assertNotNull(updatedFacultySupervisor);
		assertEquals(user, updatedFacultySupervisor.getUser());
	}

	@Test
	void testGetFacultySupervisorByUserId() {
		Long userId = 123L;
		User user = userService.GetUserById(userId);
		FacultySupervisor expectedFacultySupervisor = new FacultySupervisor();
		expectedFacultySupervisor.setUser(user);
		when(facultySupervisorRepository.findByUserId(userId)).thenReturn(expectedFacultySupervisor);

		FacultySupervisor actualFacultySupervisor = facultySupervisorService.getFacultySupervisorByUserId(userId);

		assertEquals(expectedFacultySupervisor, actualFacultySupervisor, "Faculty supervisor should be returned");
	}

	@Test
	void testGetFacultySupervisorByUserId_Exception() {
		Long userId = 123L;
		String errorMessage = "An error occurred while getting facultySupervisor with given user ID";
		when(facultySupervisorRepository.findByUserId(userId)).thenThrow(new RuntimeException(errorMessage));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			facultySupervisorService.getFacultySupervisorByUserId(userId);
		});

		assertEquals(errorMessage, exception.getMessage(), "Exception message should be correct");
	}

	@Test
	public void testGetAllFacultySupervisors() {
		// Arrange
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<FacultySupervisor> facultySupervisors = new PageImpl<>(
				Arrays.asList(new FacultySupervisor(), new FacultySupervisor()));
		when(facultySupervisorRepository.findAll(pageRequest)).thenReturn(facultySupervisors);

		// Act
		Page<FacultySupervisor> result = facultySupervisorService.getAllFacultySupervisors(pageRequest);

		// Assert
		assertEquals(facultySupervisors, result);
	}
}
