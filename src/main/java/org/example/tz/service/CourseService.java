package org.example.tz.service;

import lombok.RequiredArgsConstructor;
import org.example.tz.entity.Course;
import org.example.tz.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updated) {
        Course course = getCourseById(id);
        course.setName(updated.getName());
        course.setDescription(updated.getDescription());
        course.setStartDate(updated.getStartDate());
        course.setEndDate(updated.getEndDate());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
