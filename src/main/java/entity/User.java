package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String avatar;
    private LocalDate birthDate;
    private String phone;
    private String address;

    @Column(updatable = false)
    private LocalDate createdDate;

    private String role;
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now();
        isActive = false;
    }
}