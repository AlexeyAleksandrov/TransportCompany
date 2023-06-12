package ru.transportcompany.application.models.database;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
@Data
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule")
    private Schedule schedule;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type")
    private int type;
}