package org.example.tz.controller;


import lombok.RequiredArgsConstructor;
import org.example.tz.dto.CourseResponse;
import org.example.tz.entity.Course;
import org.example.tz.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        List<CourseResponse> response = courses.stream().map(course -> {
            List<CourseResponse.StudentSummary> studentList = course.getStudents().stream()
                    .map(student -> new CourseResponse.StudentSummary(
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getEmail()
                    )).toList();

            return CourseResponse.builder()
                    .id(course.getId())
                    .name(course.getName())
                    .description(course.getDescription())
                    .startDate(course.getStartDate())
                    .endDate(course.getEndDate())
                    .students(studentList)
                    .build();
        }).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
