package com.example.modelstore.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length=16)
    private UUID id;
    @NonNull
    @Column(name="username")
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String name;
}
