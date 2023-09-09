package com.rankerspoint.academy.Model;

public class GetSectionWiseModel {


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

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    private String RecordId;
    private String ExamId;
    private String SubjectId;
    private String SubjectName;

    public GetSectionWiseModel(String recordId, String examId, String subjectId, String subjectName) {
        RecordId = recordId;
        ExamId = examId;
        SubjectId = subjectId;
        SubjectName = subjectName;
    }
}
