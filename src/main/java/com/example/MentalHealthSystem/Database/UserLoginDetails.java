package com.example.MentalHealthSystem.Database;

import com.example.MentalHealthSystem.constants.UserRoles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "UserLoginDetails",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserLoginDetails {
    @Id
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    UserRoles role;
}
