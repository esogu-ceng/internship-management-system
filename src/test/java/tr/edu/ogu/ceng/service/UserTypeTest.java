package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tr.edu.ogu.ceng.dao.UserTypeRepository;
import tr.edu.ogu.ceng.dto.UserTypeDto;
import tr.edu.ogu.ceng.model.UserType;

public class UserTypeTest {

	@Mock
	UserTypeRepository userTypeRepository;
	UserTypeService userTypeService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

		userTypeService = new UserTypeService(userTypeRepository);
	}

	@Test
	void is_user_type_saved_successfully() {
		var userTypeDtoToSave = UserTypeDto.builder().id(1L).type("Student").build();
		LocalDateTime dateTime = LocalDateTime.now();
		var userTypeToSave = UserType.builder().id(0L).type("Student").createDate(dateTime).updateDate(dateTime)
				.build();

		when(userTypeRepository.save(any(UserType.class))).thenReturn(userTypeToSave);

		var actual = userTypeService.saveUsertype(userTypeDtoToSave);

		assertEquals(userTypeToSave.getId(), actual.getId());
		assertEquals(userTypeToSave.getType(), actual.getType());
		assertEquals(userTypeToSave.getCreateDate(), actual.getCreateDate());
		assertEquals(userTypeToSave.getUpdateDate(), actual.getUpdateDate());

	}
}
