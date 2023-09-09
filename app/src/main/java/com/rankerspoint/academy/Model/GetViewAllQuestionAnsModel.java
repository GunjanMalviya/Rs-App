package com.rankerspoint.academy.Model;

public class GetViewAllQuestionAnsModel {

    public GetViewAllQuestionAnsModel(String recordId, String examId, String examName, String questionId, String question_id, String subject_id, String question, String marks, String time_to_spend, String hint, String correct_answer, String answer1, String answer2, String answer3, String answer4, String question_l2, String answer1_l2, String answer2_l2, String answer3_l2, String answer4_l2,String explanation,String explanation_l2) {
        RecordId = recordId;
        ExamId = examId;
        ExamName = examName;
        QuestionId = questionId;
        this.question_id = question_id;
        this.subject_id = subject_id;
        this.question = question;
        this.marks = marks;
        this.time_to_spend = time_to_spend;
        this.hint = hint;
        this.correct_answer = correct_answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.question_l2 = question_l2;
        this.answer1_l2 = answer1_l2;
        this.answer2_l2 = answer2_l2;
        this.answer3_l2 = answer3_l2;
        this.answer4_l2 = answer4_l2;
        this.explanation = explanation;
        this.explanation_l2 = explanation_l2;
    }
    public GetViewAllQuestionAnsModel(){}

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getExamId() {
        return ExamId;
    }

    public void setExamId(String examId) {
        ExamId = examId;
    }

    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        ExamName = examName;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getTime_to_spend() {
        return time_to_spend;
    }

    public void setTime_to_spend(String time_to_spend) {
        this.time_to_spend = time_to_spend;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getQuestion_l2() {
        return question_l2;
    }

    public void setQuestion_l2(String question_l2) {
        this.question_l2 = question_l2;
    }

    public String getAnswer1_l2() {
        return answer1_l2;
    }

    public void setAnswer1_l2(String answer1_l2) {
        this.answer1_l2 = answer1_l2;
    }

    public String getAnswer2_l2() {
        return answer2_l2;
    }

    public void setAnswer2_l2(String answer2_l2) {
        this.answer2_l2 = answer2_l2;
    }

    public String getAnswer3_l2() {
        return answer3_l2;
    }

    public void setAnswer3_l2(String answer3_l2) {
        this.answer3_l2 = answer3_l2;
    }

    public String getAnswer4_l2() {
        return answer4_l2;
    }

    public void setAnswer4_l2(String answer4_l2) {
        this.answer4_l2 = answer4_l2;
    }
    private String RecordId;
    private String ExamId;
    private String ExamName;
    private String QuestionId;
    private String question_id;
    private String subject_id;
    private String question;
    private String marks;
    private String time_to_spend;
    private String hint;
    private String correct_answer;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String question_l2;
    private String answer1_l2;
    private String answer2_l2;
    private String answer3_l2;
    private String answer4_l2;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation_l2() {
        return explanation_l2;
    }

    public void setExplanation_l2(String explanation_l2) {
        this.explanation_l2 = explanation_l2;
    }

    private String explanation;
    private String explanation_l2;

    public String getCheckedAnswerStatus() {
        return CheckedAnswerStatus;
    }

    public void setCheckedAnswerStatus(String checkedAnswerStatus) {
        CheckedAnswerStatus = checkedAnswerStatus;
    }

    private String CheckedAnswerStatus;

    public String getCheckedAnswer() {
        return CheckedAnswer;
    }

    public void setCheckedAnswer(String checkedAnswer) {
        CheckedAnswer = checkedAnswer;
    }

    private String CheckedAnswer;

    public boolean getFlgRating() {
        return flgRating;
    }

    public void setFlgRating(boolean flgRating) {
        this.flgRating = flgRating;
    }

    private boolean flgRating;

    public int getSelindex() {
        return selindex;
    }

    public void setSelindex(int selindex) {
        this.selindex = selindex;
    }

    private int selindex;

    public int getSelectedRadioButtonId() {
        return SelectedRadioButtonId;
    }

    public void setSelectedRadioButtonId(int selectedRadioButtonId) {
        SelectedRadioButtonId = selectedRadioButtonId;
    }

    private int SelectedRadioButtonId;



}
