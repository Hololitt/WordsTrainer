package com.hololitt.SpringBootProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FlashCard {
private String correctAnswer;
private String wrongAnswer;

}