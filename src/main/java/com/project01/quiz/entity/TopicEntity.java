package com.project01.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "topic")
public class TopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String thumnail;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "topic",cascade = CascadeType.MERGE,orphanRemoval = true)
    private List<QuestionEntity> user =new ArrayList<QuestionEntity>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "topic",cascade = CascadeType.MERGE,orphanRemoval = true)
    private List<AnswerEntity> answers =new ArrayList<AnswerEntity>();
}
