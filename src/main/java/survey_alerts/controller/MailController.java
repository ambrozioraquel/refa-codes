package survey_alerts.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import survey_alerts.domain.Email;
import survey_alerts.dto.EmailDTO;
import survey_alerts.service.MailService;

import java.util.List;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;
    private final ModelMapper modelMapper;


    public MailController(MailService mailService, ModelMapper modelMapper) {
        this.mailService = mailService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<EmailDTO>> getAllEmails() {
        List<Email> emails = mailService.getAllEmails();
        if (emails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EmailDTO> emailDTOs = emails.stream()
                .map(email -> modelMapper.map(email, EmailDTO.class))
                .toList();
        return ResponseEntity.ok(emailDTOs);
    }
}
