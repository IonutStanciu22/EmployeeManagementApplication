package edu.scoalainformala.StanciuIonut.model;

import edu.scoalainformala.StanciuIonut.model.Salary;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Set;


@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<Salary> salaries;

    public BigDecimal getLastSalary() {
        return salaries.stream()
                .max(Comparator.comparing(Salary::getEffectiveDate))
                .map(Salary::getAmount)
                .orElse(null);
    }


    public String calculateAge() {
        if (this.dateOfBirth == null) {
            return "Unknown";
        }
        Period period = Period.between(this.dateOfBirth, LocalDate.now());
        return period.getYears() + " years, " + period.getMonths() + " months, and " + period.getDays() + " days";
    }

    // Constructors, getters, and setters are managed by Lombok.
}