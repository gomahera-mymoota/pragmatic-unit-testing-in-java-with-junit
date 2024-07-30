package iloveyouboss;

public abstract class Question {

    private final String text;
    private final String[] answerChoices;
    @SuppressWarnings("unused")
    private final int id;

    public Question(int id, String text, String[] answerChoices) {
        this.id = id;
        this.text = text;
        this.answerChoices = answerChoices;
    }

    public String getText() {
        return text;
    }

    public String getAnswerChoice(int i) {
        return answerChoices[i];
    }

    public boolean match(Answer answer) {
        return false;
    }

    public abstract boolean match(int expected, int actual);
    
    public int indexOf(String matchingAnswerChoice) {
        for (int i = 0; i < answerChoices.length; i++) {
            if (answerChoices[i].equals(matchingAnswerChoice)) {
                return i;
            }
        }

        return -1;
    }
}
