package iloveyouboss;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTest {

    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    @BeforeEach
    public void create() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @Test
    @DisplayName("머스트 매치 조건이 만족하지 않을 때 답은 FALSE")
    public void testMatchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // Arrange
        var profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);

        var criteriaAnswer = new Answer(question, Bool.TRUE);
        var criterion = new Criterion(criteriaAnswer, Weight.MustMatch);

        criteria.add(criterion);

        // Act
        var matches = profile.matches(criteria);

        // Assert
        assertFalse(matches);
    }

    @Test
    @DisplayName("돈 케어가 하나라도 있으면 답은 TRUE")
    public void testMatchAnswersTrueForAnyDontCareCriteria() {
        var profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);

        var criteriaAnswer = new Answer(question, Bool.TRUE);
        var criterion = new Criterion(criteriaAnswer, Weight.DontCare);
        criteria.add(criterion);

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

}
