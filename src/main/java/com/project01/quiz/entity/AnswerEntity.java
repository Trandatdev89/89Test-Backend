package com.project01.quiz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer answeruser;

    @ManyToOne
    @JoinColumn(name = "questionid")
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;


    @ManyToOne
    @JoinColumn(name = "topicid")
    private TopicEntity topic;
}
