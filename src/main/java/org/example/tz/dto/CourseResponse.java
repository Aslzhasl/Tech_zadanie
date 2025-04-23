package org.example.tz.dto;



import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<StudentSummary> students;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentSummary {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
    }
}
