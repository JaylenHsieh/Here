package com.hdu.newe.here.biz.profile.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author Jaylen Hsieh
 * @date 2018/05/01
 */
public class QuestionBean extends BmobObject{

    private String question;
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
