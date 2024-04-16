package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tr.edu.ogu.ceng.model.Transcript;
import tr.edu.ogu.ceng.service.TranscriptService;

@RestController
public class StudentProfileController {
    
    @Autowired
    private TranscriptService transcriptService;

    @PostMapping("/api/student/uploadTranscript")
    public ResponseEntity<?> uploadTranscript(@RequestParam("file") MultipartFile file) {
        Transcript transcript = transcriptService.uploadTranscript(file);
        if (transcript != null) {
            return ResponseEntity.ok("Transkript başarıyla yüklendi.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transkript yüklenirken bir hata oluştu.");
        }
    }
}
