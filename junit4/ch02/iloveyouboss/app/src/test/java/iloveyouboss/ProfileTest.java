package iloveyouboss;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ProfileTest {
    
    @Test
    public void testMatchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // Arrange
        var profile = new Profile("Bull Hockey, Inc.");
        Question question = new BooleanQuestion(1, "Got bonuses?");
 
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
