package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    
    private final Map<String, Answer> answers = new HashMap<>();
    private int score;
    private final String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        score = 0;

        var kill = false;
        var anyMatches = false;
        for (Criterion criterion: criteria) {
            var answer = answers.get(criterion.getAnswer().getQuestionText());
            var match = criterion.getWeight() == Weight.DontCare || answer.match(criterion.getAnswer());

            if (!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }

            if (match) {
                score += criterion.getWeight().getValue();
            }

            anyMatches |= match;
        }

        if (kill) {
            return false;
        }

        return anyMatches;
    }

    public int score() {
        return score;
    }
}
