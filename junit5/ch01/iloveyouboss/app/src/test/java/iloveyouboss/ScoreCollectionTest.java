package iloveyouboss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreCollectionTest {
    
    @Test
    @DisplayName("두 수의 산술평균 테스트")
    public void testArithmeticMeanOfTwoNumbers() {
        // Arrange: 준비
        var collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // Act: 실행
        var actualResult = collection.arithmeticMean();

        // Assert: 단언
        assertEquals(actualResult, 6);
    }
    
}
