package iloveyouboss;

public class Criterion implements Scoreable {
    
    private final Weight weight;
    private final Answer answer;
    private int score;

    public Criterion(Answer answer, Weight weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Answer getAnswer() { return answer; }
    public Weight getWeight() { return weight;}

    public void setScore(int score) { this.score = score; }
    @Override
    public int getScore() { return score; }

}
