package org.example.tz.repository;

import org.example.tz.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmailAndCourseId(String email, Long courseId);

    List<Student> findByCourseId(Long courseId);

    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email
    );

    Optional<Student> findByIdAndCourseId(Long studentId, Long courseId);
}
