package iloveyouboss;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTest {

    @Test
    @DisplayName("머스트 매치 조건이 만족하지 않을 때 답은 FALSE")
    public void testMatchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // Arrange
        var profile = new Profile("Bull Hockey, Inc.");
        var question = new BooleanQuestion(1, "Got bonuses?");
        var profileAnswer = new Answer(question, Bool.FALSE);
        profile.add(profileAnswer);

        var criteria = new Criteria();
        var criteriaAnswer = new Answer(question, Bool.TRUE);
        var criterion = new Criterion(criteriaAnswer, Weight.MustMatch);

        criteria.add(criterion);

        // Act
        var matches = profile.matches(criteria);

        // Assert
        assertFalse(matches);
    }

}
