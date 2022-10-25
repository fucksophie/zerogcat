package lv.yourfriend.zerogcat.answers;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.answers.impl.Banned;
import lv.yourfriend.zerogcat.answers.impl.Bloat;
import lv.yourfriend.zerogcat.answers.impl.Goober;
import lv.yourfriend.zerogcat.answers.impl.Gravity;
import lv.yourfriend.zerogcat.answers.impl.Meow;

public class AnswerManager {
    static public ArrayList<Answer> answers = new ArrayList<>();

    static public void init() {
        answers.add(new Goober());
        answers.add(new Gravity());
        answers.add(new Meow());
        answers.add(new Banned());
        answers.add(new Bloat());
    }
}
