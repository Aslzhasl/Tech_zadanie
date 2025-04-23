package org.example.tz.service;


import lombok.RequiredArgsConstructor;
import org.example.tz.entity.Course;
import org.example.tz.entity.Student;
import org.example.tz.repository.CourseRepository;
import org.example.tz.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> searchStudents(String keyword) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword, keyword, keyword
        );
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @Transactional
    public Student registerStudent(Long courseId, Student student) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + courseId));

        if (studentRepository.existsByEmailAndCourseId(student.getEmail(), courseId)) {
            throw new RuntimeException("Student already registered to this course");
        }

        student.setRegistrationDate(LocalDateTime.now());
        student.setCourse(course);

        Student savedStudent = studentRepository.save(student);

        // Send confirmation email
        emailService.sendRegistrationEmail(
                savedStudent.getEmail(),
                course.getName(),
                course.getStartDate().toString()
        );

        return savedStudent;
    }

    public void deleteStudent(Long courseId, Long studentId) {
        Student student = studentRepository.findByIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new RuntimeException("Student not found in this course"));
        studentRepository.delete(student);
    }
}
