package com.project01.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invalidatetoken")
public class InValidTokenEntity {
    @Id
    private String id;

    @Column
    private Date expirytime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;
}
