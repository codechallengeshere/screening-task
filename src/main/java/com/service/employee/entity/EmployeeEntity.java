package com.service.employee.entity;

import com.service.employee.enumeration.DepartmentEnumeration;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Validated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "employees",
        indexes = {
                @Index(
                        name = "employed_at__department__idx",
                        columnList = "employedAt,department"
                )
        }
)
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false, length = 50)
    private DepartmentEnumeration departmentEnumeration;

    @CreationTimestamp
    @Column(name = "employed_at", nullable = false)
    private Date employedAt;
}
