package iloveyouboss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ProfileTest {

    private Profile profile;
    private Criteria criteria;
    
    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    
    @Before
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Before
    public void createQuestionsAndAnswers() {
        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition = new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimbursesTuition, Bool.FALSE);

        questionIsThereRelocation = new BooleanQuestion(2, "Relocation package?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);

        questionOnsiteDaycare = new BooleanQuestion(3, "Onsite daycare?");
        answerHasOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare = new Answer(questionOnsiteDaycare, Bool.FALSE);
    }
    
    @Test
    public void testMatchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // Arrange
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));
        
        // Act
        var matches = profile.matches(criteria);

        // Assert
        assertFalse(matches);
    }

    @Test
    public void testMatchAnswersTrueForAnyDontCareCriteria() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    public void testMatchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    public void testMatchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testScoreIsZeroWhenThereAreNoMatches() {
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertThat(profile.score(), equalTo(0));
        assertEquals(profile.score(), 0);
    }

    @Test
    public void testScoreIsCriterionValueForSingleMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertEquals(profile.score(), Weight.Important.getValue());
    }

    @Test
    public void testScoreAccumulatesCriterionValuesForMatches() {
        profile.add(answerThereIsRelocation);
        profile.add(answerReimbursesTuition);
        profile.add(answerNoOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.WouldPrefer));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.VeryImportant));

        profile.matches(criteria);

        var expectedScore = Weight.Important.getValue() + Weight.WouldPrefer.getValue();
        assertEquals(profile.score(), expectedScore);
    }

}
