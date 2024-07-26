package iloveyouboss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ScoreCollectionTest {
    
    @Test
    public void answersArithmeticMeanOfTwoNumbers() {
        // 준비: arrange
        var collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // 실행: act
        var actualResult = collection.arithmeticMean();

        // 단언: assert
        assertThat(actualResult, equalTo(6));     // deprecated
        assertThat(actualResult, is(6));            // deprecated

        assertEquals(actualResult, 6);        
    }
}
