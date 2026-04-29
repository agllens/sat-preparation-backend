package com.sat_preparation_backend.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    public void createTest(TestRequest request) {

        Test test = new Test();
        test.setTitle(request.getTitle());
        test.setTestNum(request.getTestNum());
        test.setSection(request.getSection());

        List<Question> questions = request.getQuestions().stream().map(q -> {
            Question question = new Question();
            question.setQuestionText(q.questionText);
            question.setOptionA(q.optionA);
            question.setOptionB(q.optionB);
            question.setOptionC(q.optionC);
            question.setOptionD(q.optionD);
            question.setCorrectAnswer(q.correctAnswer);
            question.setTest(test);
            return question;
        }).toList();

        test.setQuestions(questions);

        testRepository.save(test);
    }

    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        questionRepository.delete(question);
    }

    public void updateQuestion(Long id, QuestionDto dto) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setQuestionText(dto.questionText);
        question.setOptionA(dto.optionA);
        question.setOptionB(dto.optionB);
        question.setOptionC(dto.optionC);
        question.setOptionD(dto.optionD);
        question.setCorrectAnswer(dto.correctAnswer);

        questionRepository.save(question);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }
}
