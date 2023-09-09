package com.rankerspoint.academy.Model;

public class MyAnsResponse{
    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String questionID) {
        QuestionID = questionID;
    }

    public String getQuesStatus() {
        return QuesStatus;
    }

    public void setQuesStatus(String quesStatus) {
        QuesStatus = quesStatus;
    }

    public String QuestionID;
    public String QuesStatus;

    public String getFlagStar() {
        return FlagStar;
    }

    public void setFlagStar(String flagStar) {
        FlagStar = flagStar;
    }

    public String FlagStar;

    public String getSelAnsWer() {
        return SelAnsWer;
    }

    public void setSelAnsWer(String selAnsWer) {
        SelAnsWer = selAnsWer;
    }


    public String SelAnsWer;

}

