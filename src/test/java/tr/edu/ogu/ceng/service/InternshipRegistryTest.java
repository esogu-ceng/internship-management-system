
package tr.edu.ogu.ceng.service;

import org.springframework.transaction.TransactionSystemException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.ArrayList;

import tr.edu.ogu.ceng.internationalization.MessageResource;

import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import tr.edu.ogu.ceng.dao.InternshipRegistryRepository;
import tr.edu.ogu.ceng.dto.requests.InternshipRegistryRequestDto;
import tr.edu.ogu.ceng.dto.responses.InternshipRegistryResponseDto;
import tr.edu.ogu.ceng.model.InternshipRegistry;

import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class InternshipRegistryTest {

	@Mock
	private InternshipRegistryRepository internshipRegistryRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private InternshipRegistryService internshipRegistryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testDeleteInternshipRegistry_WhenRegistryExists() {
		// Arrange
		long id = 1L;
		when(internshipRegistryRepository.existsById(id)).thenReturn(true);

		// Act
		boolean result = internshipRegistryService.deleteInternshipRegistry(id);

		// Assert
		assertTrue(result);
		verify(internshipRegistryRepository, times(1)).existsById(id);
		verify(internshipRegistryRepository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteInternshipRegistry_WhenRegistryNotExists() {
		// Arrange
		long id = 1L;
		when(internshipRegistryRepository.existsById(id)).thenReturn(false);

		// Act
		boolean result = internshipRegistryService.deleteInternshipRegistry(id);

		// Assert
		assertFalse(result);
		verify(internshipRegistryRepository, times(1)).existsById(id);
		verify(internshipRegistryRepository, never()).deleteById(id);
	}

	@Test
	void testAddInternshipRegistry_Success() {
		// Arrange
		LocalDateTime now = LocalDateTime.now();
		InternshipRegistryRequestDto requestDto = new InternshipRegistryRequestDto();
		InternshipRegistry internshipRegistry = new InternshipRegistry();
		internshipRegistry.setId(1L);
		internshipRegistry.setCreateDate(now);
		internshipRegistry.setUpdateDate(now);
		when(modelMapper.map(requestDto, InternshipRegistry.class)).thenReturn(internshipRegistry);
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(internshipRegistry);
		InternshipRegistryResponseDto expectedDto = new InternshipRegistryResponseDto();
		expectedDto.setId(1L);
		expectedDto.setCreateDate(now);
		expectedDto.setUpdateDate(now);
		when(modelMapper.map(internshipRegistry, InternshipRegistryResponseDto.class)).thenReturn(expectedDto);

		// Act
		InternshipRegistryResponseDto actualDto = internshipRegistryService.addInternshipRegistry(requestDto);

		// Assert
		assertNotNull(actualDto);
		assertEquals(1L, actualDto.getId());
		assertEquals(now, actualDto.getCreateDate());
		assertEquals(now, actualDto.getUpdateDate());
		verify(internshipRegistryRepository, times(1)).save(any(InternshipRegistry.class));
	}

	@Test
	void testAddInternshipRegistry_WhenDtoIsNull() {
		// Arrange
		InternshipRegistryRequestDto requestDto = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> internshipRegistryService.addInternshipRegistry(requestDto));
		verifyNoInteractions(internshipRegistryRepository);
	}

	@Test
	void testAddInternshipRegistry_WhenSaveFails() {
		// Arrange
		InternshipRegistryRequestDto requestDto = new InternshipRegistryRequestDto();
		when(modelMapper.map(requestDto, InternshipRegistry.class)).thenReturn(new InternshipRegistry());
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenThrow(RuntimeException.class);

		// Act & Assert
		assertThrows(RuntimeException.class, () -> internshipRegistryService.addInternshipRegistry(requestDto));
		verify(internshipRegistryRepository, times(1)).save(any(InternshipRegistry.class));
	}

	@Test
	void testAddInternshipRegistry_WhenRepositoryReturnsNull() {
		// Arrange
		InternshipRegistryRequestDto requestDto = new InternshipRegistryRequestDto();
		when(modelMapper.map(requestDto, InternshipRegistry.class)).thenReturn(new InternshipRegistry());
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(null);

		// Act & Assert
		assertThrows(RuntimeException.class, () -> internshipRegistryService.addInternshipRegistry(requestDto));
		verify(internshipRegistryRepository, times(1)).save(any(InternshipRegistry.class));
	}

	@Test
	void testUpdateInternshipRegistry_Success() {
		// Arrange
		LocalDateTime now = LocalDateTime.now();
		InternshipRegistryRequestDto requestDto = new InternshipRegistryRequestDto();
		requestDto.setId(1L);
		InternshipRegistry internshipRegistry = new InternshipRegistry();
		internshipRegistry.setId(1L);
		internshipRegistry.setCreateDate(now);
		when(internshipRegistryRepository.existsById(1L)).thenReturn(true);
		when(modelMapper.map(requestDto, InternshipRegistry.class)).thenReturn(internshipRegistry);
		when(internshipRegistryRepository.getById(1L)).thenReturn(internshipRegistry);
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenReturn(internshipRegistry);
		InternshipRegistryResponseDto expectedDto = new InternshipRegistryResponseDto();
		expectedDto.setId(1L);
		expectedDto.setCreateDate(now);
		when(modelMapper.map(internshipRegistry, InternshipRegistryResponseDto.class)).thenReturn(expectedDto);

		// Act
		InternshipRegistryResponseDto actualDto = internshipRegistryService.updateInternshipRegistry(requestDto);

		// Assert
		assertNotNull(actualDto);
		assertEquals(1L, actualDto.getId());
		assertEquals(now, actualDto.getCreateDate());
		verify(internshipRegistryRepository, times(1)).save(any(InternshipRegistry.class));
	}

	@Test
	void testUpdateInternshipRegistry_WhenDtoIsNull() {
		// Arrange
		InternshipRegistryRequestDto requestDto = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class,
				() -> internshipRegistryService.updateInternshipRegistry(requestDto));
		verifyNoInteractions(internshipRegistryRepository);
	}

	@Test
	void testUpdateInternshipRegistry_WhenSaveFails() {
		// Arrange
		InternshipRegistryRequestDto requestDto = new InternshipRegistryRequestDto();
		requestDto.setId(1L);
		InternshipRegistry internshipRegistry = new InternshipRegistry();
		when(internshipRegistryRepository.existsById(1L)).thenReturn(true);
		when(modelMapper.map(requestDto, InternshipRegistry.class)).thenReturn(internshipRegistry);
		when(internshipRegistryRepository.getById(1L)).thenReturn(internshipRegistry);
		when(internshipRegistryRepository.save(any(InternshipRegistry.class))).thenThrow(RuntimeException.class);

		// Act & Assert
		assertThrows(RuntimeException.class, () -> internshipRegistryService.updateInternshipRegistry(requestDto));
		verify(internshipRegistryRepository, times(1)).save(any(InternshipRegistry.class));
	}

	@Test
	void testGetInternshipRegistry_Success() {
		// Arrange
		LocalDateTime now = LocalDateTime.now();
		InternshipRegistry internshipRegistry = new InternshipRegistry();
		internshipRegistry.setId(1L);
		internshipRegistry.setCreateDate(now);
		when(internshipRegistryRepository.existsById(1L)).thenReturn(true);
		when(internshipRegistryRepository.getById(1L)).thenReturn(internshipRegistry);
		InternshipRegistryResponseDto expectedDto = new InternshipRegistryResponseDto();
		expectedDto.setId(1L);
		expectedDto.setCreateDate(now);
		when(modelMapper.map(internshipRegistry, InternshipRegistryResponseDto.class)).thenReturn(expectedDto);

		// Act
		InternshipRegistryResponseDto actualDto = internshipRegistryService.getInternshipRegistry(1L);

		// Assert
		assertNotNull(actualDto);
		assertEquals(1L, actualDto.getId());
		assertEquals(now, actualDto.getCreateDate());
		verify(internshipRegistryRepository, times(1)).getById(anyLong());
	}

	@Test
	void testGetInternshipRegistry_WhenRegistryExists() {
		// Arrange
		Long id = 1L;
		InternshipRegistry internshipRegistry = new InternshipRegistry();
		internshipRegistry.setId(id);
		when(internshipRegistryRepository.existsById(id)).thenReturn(true);
		when(internshipRegistryRepository.getById(id)).thenReturn(internshipRegistry);

		// Act
		InternshipRegistryResponseDto result = internshipRegistryService.getInternshipRegistry(id);

		// Assert
		assertNotNull(result);
		assertEquals(id, result.getId());
		verify(internshipRegistryRepository).existsById(id);
		verify(internshipRegistryRepository).getById(id);
	}

	@Test
	void testGetInternshipRegistry_WhenRepositoryGetByIdThrowsException() {
		// Arrange
		Long id = 1L;
		when(internshipRegistryRepository.existsById(id)).thenReturn(true);
		when(internshipRegistryRepository.getById(id)).thenThrow(new RuntimeException("Test exception"));

		// Act & Assert
		assertThrows(RuntimeException.class, () -> internshipRegistryService.getInternshipRegistry(id));
		verify(internshipRegistryRepository).existsById(id);
		verify(internshipRegistryRepository).getById(id);
		verify(modelMapper, never()).map(any(), eq(InternshipRegistryResponseDto.class));
	}

	@Test
	void testGetAllInternshipRegistiries_Success() {
		// Arrange
		long internshipId = 1L;
		Pageable pageable = Pageable.unpaged();
		Page<InternshipRegistry> page = new PageImpl<>(List.of(new InternshipRegistry()));
		when(internshipRegistryRepository.findAllByInternshipId(internshipId, pageable)).thenReturn(page);
		when(modelMapper.map(any(), eq(InternshipRegistryResponseDto.class)))
				.thenReturn(new InternshipRegistryResponseDto());

		// Act
		Page<InternshipRegistryResponseDto> result = internshipRegistryService.getAllInternshipRegistiries(internshipId,
				pageable);

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	void testGetAllInternshipRegistiries_WhenNotEmpty() {
		// Arrange
		Long internshipId = 1L;
		Pageable pageable = mock(Pageable.class);

		// Örnek staj kayıtları oluştur
		List<InternshipRegistry> registryList = new ArrayList<>();
		registryList.add(new InternshipRegistry());
		registryList.add(new InternshipRegistry());

		// Pageable nesnesiyle birlikte Page nesnesi oluştur
		Page<InternshipRegistry> page = new PageImpl<>(registryList);

		when(internshipRegistryRepository.findAllByInternshipId(internshipId, pageable)).thenReturn(page);

		// Act
		Page<InternshipRegistryResponseDto> result = internshipRegistryService.getAllInternshipRegistiries(internshipId,
				pageable);

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(2, result.getContent().size()); // Örnek staj kayıtları oluşturulduğu için 2 kayıt olmalı
		verify(internshipRegistryRepository, times(1)).findAllByInternshipId(internshipId, pageable);
	}

	@Test
	void testGetAllInternshipRegistiries_WhenEmpty() {
		// Arrange
		Long internshipId = 1L;
		Pageable pageable = mock(Pageable.class);

		// Boş bir Page nesnesi oluştur
		Page<InternshipRegistry> emptyPage = Page.empty();

		when(internshipRegistryRepository.findAllByInternshipId(internshipId, pageable)).thenReturn(emptyPage);

		// Act
		Page<InternshipRegistryResponseDto> result = internshipRegistryService.getAllInternshipRegistiries(internshipId,
				pageable);

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
		verify(internshipRegistryRepository, times(1)).findAllByInternshipId(internshipId, pageable);
	}

	@Test
	void testGetAllInternshipRegistiries_WhenExceptionThrown() {
		// Arrange
		Long internshipId = 1L;
		Pageable pageable = mock(Pageable.class);

		when(internshipRegistryRepository.findAllByInternshipId(internshipId, pageable))
				.thenThrow(new RuntimeException("Test exception"));

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> internshipRegistryService.getAllInternshipRegistiries(internshipId, pageable));
		assertEquals("Test exception", exception.getMessage());
		verify(internshipRegistryRepository).findAllByInternshipId(internshipId, pageable);
		verifyNoMoreInteractions(internshipRegistryRepository);
	}


}

