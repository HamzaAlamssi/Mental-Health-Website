package com.example.MentalHealthSystem.Database;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Table(name = "Admin",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Admin extends  User{
    public Admin(){ super();}
}
