package ru.transportcompany.application.models.users;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", schema = "public", catalog = "transport_company")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;
}
