package org.example.tz.dto;



import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long courseId;
    private String courseName;
}
