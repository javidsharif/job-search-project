package com.practice.jobsearchproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "applicant_name")
    private String applicantName;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "communication_tool_id", referencedColumnName = "id")
    @JsonIgnore
    private CommunicationTool communicationTool;
    @Column(name = "vacancy_name")
    private String vacancyName;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonIgnore
    private Category category;
    @Column(name = "is_internship")
    private boolean isInternship;
    @ManyToMany
    @JoinTable(
            name = "vacancies_cities",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id"))
    private Set<City> cities = new HashSet<>();
    @Column(name = "address")
    private String address;
    @Column(name = "from_salary")
    private Double fromSalary;
    @Column(name = "to_salary")
    private Double toSalary;
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @JsonIgnore
    private Currency currency;
    @ManyToMany
    @JoinTable(
            name = "vacancies_job_types",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "job_type_id"))
    private Set<JobType> jobTypes = new HashSet<>();
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    @ManyToMany
    @JoinTable(
            name = "vacancies_special_knowledge",
            joinColumns = @JoinColumn(name = "vacancy_id"),
            inverseJoinColumns = @JoinColumn(name = "special_knowledge_id"))
    private Set<SpecialKnowledge> specialKnowledge = new HashSet<>();
    @NotBlank(message = "text of vacancy is mandatory")
    @Column(name = "text_of_vacancy")
    private String textOfVacancy;
    @Column(name = "special_requirements")
    private String specialRequirements;
    @Column(name = "for_apply")
    private String forApply;
    @Column(name = "minimal_work_experience")
    private Integer minimalWorkExperience;
    @ManyToOne
    @JoinColumn(name = "education_level_id", referencedColumnName = "id")
    @JsonIgnore
    private EducationLevel educationLevel;
    @Column(name = "note_contact_at_vacancy")
    private boolean noteContactAtVacancy;
    @Column(name = "post_vacancy")
    private boolean postVacancy;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "view_number")
    private long viewNumber;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @JsonIgnore
    private Status status;

    @OneToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    @JsonIgnore
    private Note note;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @JsonIgnore
    private Company company;
}
