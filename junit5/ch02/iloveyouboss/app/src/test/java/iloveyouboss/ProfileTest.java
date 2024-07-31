package iloveyouboss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @BeforeEach
    public void createCriteria() {
        criteria = new Criteria();
    }

    @BeforeEach
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
    @DisplayName("머스트 매치 조건이 만족하지 않을 때 답은 FALSE")
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
    @DisplayName("돈 케어가 하나라도 있으면 답은 TRUE")
    public void testMatchAnswersTrueForAnyDontCareCriteria() {
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.DontCare));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    @DisplayName("다중 조건 중 하나라도 일치하면 답은 TRUE")
    public void testMatchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        var matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    @DisplayName("다중 조건 중 어느 것도 일치하지 않으면 답은 FALSE")
    public void testMatchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        var matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    @DisplayName("일치하는 조건이 없으면 점수는 0")
    public void testScoreIsZeroWhenThereAreNoMatches() {
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertEquals(profile.score(), 0);
    }

    @Test
    @DisplayName("조건 하나만 일치하면 점수는 해당 조건의 값")
    public void testScoreIsCriterionValueForSingleMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertEquals(profile.score(), Weight.Important.getValue());
    }

    @Test
    @DisplayName("여러 조건과 일치하면 점수는 해당 조건의 누적 값")
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
