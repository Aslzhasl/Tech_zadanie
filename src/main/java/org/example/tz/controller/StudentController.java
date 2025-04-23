package org.example.tz.controller;


import lombok.RequiredArgsConstructor;
import org.example.tz.dto.RegistrationRequest;
import org.example.tz.dto.StudentResponse;
import org.example.tz.entity.Student;
import org.example.tz.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();

        List<StudentResponse> responseList = students.stream()
                .map(student -> StudentResponse.builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .email(student.getEmail())
                        .courseId(student.getCourse().getId())
                        .courseName(student.getCourse().getName())
                        .build()
                ).toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentResponse>> search(@RequestParam String keyword) {
        List<Student> students = studentService.searchStudents(keyword);
        List<StudentResponse> responseList = students.stream().map(student -> StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .courseId(student.getCourse().getId())
                .courseName(student.getCourse().getName())
                .build()
        ).toList();

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/register/{courseId}")
    public ResponseEntity<StudentResponse> registerStudent(
            @PathVariable Long courseId,
            @RequestBody RegistrationRequest request
    ) {
        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        Student saved = studentService.registerStudent(courseId, student);

        StudentResponse response = StudentResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .courseId(saved.getCourse().getId())
                .courseName(saved.getCourse().getName())
                .build();

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{courseId}/{studentId}")
    public ResponseEntity<Void> deleteStudentFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ) {
        studentService.deleteStudent(courseId, studentId);
        return ResponseEntity.noContent().build();
    }
}
