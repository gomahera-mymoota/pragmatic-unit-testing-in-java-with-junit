package iloveyouboss;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ProfileTest {

    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    @Before
    public void create() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }
    
    @Test
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
