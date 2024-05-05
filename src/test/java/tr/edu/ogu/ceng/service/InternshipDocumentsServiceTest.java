package tr.edu.ogu.ceng.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import tr.edu.ogu.ceng.dao.InternshipDocumentsRepository;
import tr.edu.ogu.ceng.dto.responses.InternshipDocumentsResponseDto;
import tr.edu.ogu.ceng.model.InternshipDocument;

public class InternshipDocumentsServiceTest {

    @Mock
    private InternshipDocumentsRepository internshipDocumentsRepository;

    @InjectMocks
    private InternshipDocumentsService internshipDocumentsService;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllInternshipDocumentsByInternshipId_Success() {
        // Mocking
        Pageable pageable = mock(Pageable.class);
        Page<InternshipDocument> internshipDocumentPage = mock(Page.class);
        List<InternshipDocument> internshipDocuments = new ArrayList<>();
        internshipDocuments.add(new InternshipDocument(1L, "file1.txt", "path/to/file1.txt", null, null, null, null));
        internshipDocuments.add(new InternshipDocument(2L, "file2.txt", "path/to/file2.txt", null, null, null, null));
        Page<InternshipDocument> internshipDocumentPageMock = new PageImpl<>(internshipDocuments);

        when(internshipDocumentsRepository.findAllDocumentByInternshipId(anyLong(), any(Pageable.class)))
                .thenReturn(internshipDocumentPageMock);

        // Execution
        Page<InternshipDocumentsResponseDto> result = internshipDocumentsService
                .getAllInternshipDocumentsByInternshipId(1L, pageable);

        // Assertion
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetAllInternshipDocumentsByInternshipId_EmptyList() {
        // Mocking
        Pageable pageable = mock(Pageable.class);
        Page<InternshipDocument> internshipDocumentPage = mock(Page.class);
        List<InternshipDocument> internshipDocuments = new ArrayList<>();
        Page<InternshipDocument> internshipDocumentPageMock = new PageImpl<>(internshipDocuments);

        when(internshipDocumentsRepository.findAllDocumentByInternshipId(anyLong(), any(Pageable.class)))
                .thenReturn(internshipDocumentPageMock);

        // Execution
        Page<InternshipDocumentsResponseDto> result = internshipDocumentsService
                .getAllInternshipDocumentsByInternshipId(1L, pageable);

        // Assertion
        assertTrue(result.isEmpty());
    }

    

    @Test
    void testDownload_FileNotFound() {
        // Mocking
        when(internshipDocumentsRepository.getReferenceById(anyLong())).thenReturn(null);

        // Execution and Assertion
        assertThrows(IOException.class, () -> {
            internshipDocumentsService.download(1L);
        });
    }

    @Test
    void testDownload_IOError() throws IOException {
        // Mocking
        InternshipDocument internshipDocument = new InternshipDocument(1L, "file.txt", "path/to/file.txt", null, null, null, null);
        when(internshipDocumentsRepository.getReferenceById(anyLong())).thenReturn(internshipDocument);

        // Execution and Assertion
        assertThrows(IOException.class, () -> {
            internshipDocumentsService.download(1L);
        });
    }
}
