package com.example.appenquetes1.controller;

import com.example.appenquetes1.config.JwtUtil;
import com.example.appenquetes1.dto.admin.AnswerDetailDTO;
import com.example.appenquetes1.dto.admin.BatchActionRequest;
import com.example.appenquetes1.dto.admin.SubmissionAdminDTO;
import com.example.appenquetes1.entity.Answers;
import com.example.appenquetes1.entity.User;
import com.example.appenquetes1.repository.AnswersRepository;
import com.example.appenquetes1.repository.UserRepository;
import com.example.appenquetes1.service.AdminAnswerService;
import com.example.appenquetes1.service.DashboardWebSocketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/answers")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAnswersController {
    @Autowired
    private AdminAnswerService adminService;
    @Autowired private JwtUtil jwtUtil; // pour extraire adminId
    @Autowired
    private AnswersRepository answersRepository;
    @Autowired
    private AdminAnswerService adminAnswerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DashboardWebSocketService webSocketService;

    @SneakyThrows
    @GetMapping("/{answerId}/file")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer answerId) throws IOException {
        Answers answer = answersRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée"));
        byte[] fileData = Base64.getDecoder().decode(answer.getValue()); // si stocké en base64
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(answer.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + answer.getFileName() + "\"")
                .body(fileData);
    }
    @GetMapping("/submissions")
    public ResponseEntity<Page<SubmissionAdminDTO>> getSubmissions(
            @RequestParam(required = false) Integer surveyId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validationDate,
            @PageableDefault(size = 20) Pageable pageable,
            HttpServletRequest request) {
        Integer adminId = (Integer) request.getAttribute("userId");
        // verif que user a le role admin(du token)
        Page<SubmissionAdminDTO> page = adminService.getSubmissions(
                surveyId, userId, status, validationDate, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/submissions/{id}")
    public ResponseEntity<SubmissionAdminDTO> getSubmissionDetail(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getSubmissionDetail(id));
    }

    @PutMapping("/submissions/{id}/validate")
    public ResponseEntity<?> validateSubmission(@PathVariable Integer id,
                                                @RequestBody Map<String, String> payload,
                                                HttpServletRequest request) {
        Integer adminId = (Integer) request.getAttribute("userId");
        String comment = payload.get("comment");
        adminService.validateSubmission(id, adminId, comment);
        Map<String, Object> update = new HashMap<>();
        update.put("stats", adminService.getDashboardStats());
        update.put("dailySubmissions", adminService.getDailySubmissions());
        webSocketService.emitDashboardUpdate(update);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/submissions/{id}/reject")
    public ResponseEntity<?> rejectSubmission(@PathVariable Integer id,
                                              @RequestBody Map<String, String> payload,
                                              HttpServletRequest request) {
        Integer adminId = (Integer) request.getAttribute("userId");
        String comment = payload.get("comment");
        adminService.rejectSubmission(id, adminId, comment);

        return ResponseEntity.ok().build();
    }

//    //un batch de validation /rejet
//    @PutMapping("/submissions/batch/validate")
//    public ResponseEntity<?> batchValidate(@RequestBody BatchActionRequest request ,UserDetails adminDetails) {
//        String username = adminDetails.getUsername();
//        User admin = userRepository.findByUsername(username).orElseThrow();
//        adminAnswerService.batchValidate(request.getIds(), admin.getId(), request.getComment());
//        System.out.println("batchValidate - ids: " + request.getIds());
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/submissions/batch/reject")
//    public ResponseEntity<?> batchReject(@RequestBody BatchActionRequest request,UserDetails adminDetails) {
//        Integer adminId = userRepository.findByUsername(adminDetails.getUsername()).orElseThrow().getId();
//        adminAnswerService.batchReject(request.getIds(), adminId, request.getComment());
//
//        System.out.println("batchReject - ids: " + request.getIds());
//
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/submissions/export/csv")
//    public void exportCsv(@RequestParam(required = false) Integer surveyId,
//                          @RequestParam(required = false) Integer userId,
//                          @RequestParam(required = false) String status,
//                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validationDate,
//                          HttpServletResponse response) throws IOException {
//        List<SubmissionAdminDTO> list = adminService.getAllSubmissionsNoPage(surveyId, userId, status, validationDate );
//        response.setContentType("text/csv;charset=UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename=submissions_" + System.currentTimeMillis() + ".csv");
//        PrintWriter writer = response.getWriter();
//        writer.println("ID,Survey,Enquêteur,Date soumission,Statut,Commentaire validation");
//        for (SubmissionAdminDTO s : list) {
//            writer.printf("%d,\"%s\",\"%s\",%s,%s,\"%s\"\n",
//                    s.getId(), escapeCsv(s.getSurveyLibelle()), escapeCsv(s.getUsername()),
//                    s.getSubmissionDate(), s.getStatus(), escapeCsv(s.getValidationComment()));
//        }
//        writer.flush();
//    }
//
//    @GetMapping("/submissions/export/excel")
//    public void exportExcel(@RequestParam(required = false) Integer surveyId,
//                            @RequestParam(required = false) Integer userId,
//                            @RequestParam(required = false) String status,
//                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validationDate,
//                            HttpServletResponse response) throws IOException {
//        List<SubmissionAdminDTO> list = adminService.getAllSubmissionsNoPage(surveyId, userId, status, validationDate);
//        Workbook wb = new XSSFWorkbook();
//        Sheet sheet = wb.createSheet("Soumissions");
//        Row header = sheet.createRow(0);
//        String[] columns = {"ID", "Survey", "Enquêteur", "Date soumission", "Statut", "Commentaire"};
//        for (int i = 0; i < columns.length; i++) header.createCell(i).setCellValue(columns[i]);
//        int rowNum = 1;
//        for (SubmissionAdminDTO s : list) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(s.getId());
//            row.createCell(1).setCellValue(s.getSurveyLibelle());
//            row.createCell(2).setCellValue(s.getUsername());
//            row.createCell(3).setCellValue(s.getSubmissionDate().toString());
//            row.createCell(4).setCellValue(s.getStatus());
//            row.createCell(5).setCellValue(s.getValidationComment());
//        }
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=submissions.xlsx");
//        wb.write(response.getOutputStream());
//        wb.close();
//    }

    @GetMapping("/submissions/{submissionId}/export/csv")
    public void exportSubmissionCsv(@PathVariable Integer submissionId, HttpServletResponse response) throws IOException {
        SubmissionAdminDTO detail = adminService.getSubmissionDetail(submissionId);
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=submission_" + submissionId + ".csv");
        PrintWriter writer = response.getWriter();
        writer.println("Question,Réponse,CodeQuestion");
        for (AnswerDetailDTO ans : detail.getAnswers()) {
            String valeur = ans.getValue();
            if (ans.getSelectedOptions() != null && !ans.getSelectedOptions().isEmpty()) {
                valeur = String.join(", ", ans.getSelectedOptions());
            }
            writer.printf("\"%s\",\"%s\",\"%s\"\n",
                    escapeCsv(ans.getQuestionText()), escapeCsv(valeur), ans.getCodeQuestion());
        }
        writer.flush();
    }

    @GetMapping("/submissions/{submissionId}/export/excel")
    public void exportSubmissionExcel(@PathVariable Integer submissionId, HttpServletResponse response) throws IOException {
        SubmissionAdminDTO detail = adminService.getSubmissionDetail(submissionId);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Soumission #" + submissionId);
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Question");
        header.createCell(1).setCellValue("Réponse");
        header.createCell(2).setCellValue("CodeQuestion");
        int rowNum = 1;
        for (AnswerDetailDTO ans : detail.getAnswers()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ans.getQuestionText());
            String valeur = ans.getValue();
            if (ans.getSelectedOptions() != null && !ans.getSelectedOptions().isEmpty()) {
                valeur = String.join(", ", ans.getSelectedOptions());
            }
            row.createCell(1).setCellValue(valeur);
            row.createCell(2).setCellValue(ans.getCodeQuestion());
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=submission_" + submissionId + ".xlsx");
        wb.write(response.getOutputStream());
        wb.close();
    }

    // Méthode utilitaire pour échapper les guillemets dans CSV
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
    @GetMapping("/stats/{surveyId}")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Integer surveyId) {
        return ResponseEntity.ok(adminService.getSurveyStats(surveyId));
    }

}
