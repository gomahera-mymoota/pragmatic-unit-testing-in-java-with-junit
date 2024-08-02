package scratch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssertTest {

    private Account account;

    @BeforeEach
    public void createAccount() {
        account = new Account("an account name");
    }

    @Test
    public void testHasPositiveBalance() {
        account.deposit(50);

        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void testDepositIncreasesBalance() {
        var initialBalance = account.getBalance();
        account.deposit(100);

        assertTrue(account.getBalance() > initialBalance);   // JUnit5

        assertEquals(100, account.getBalance());    // JUnit5, 정확한 값으로 단언하는 것이 좋다
        
        assertThat(account.getBalance()).isEqualTo(100);    // assertJ
    }

    @Test
    public void testDepositIncreasesBalance_hamcrestAssertTrue() {
        account.deposit(50);

        org.hamcrest.MatcherAssert.assertThat(account.getBalance() > 0, is(true));       // 장황하다

        assertTrue(account.getBalance() > 0);                   // 이게 낫고
        assertTrue(account.hasPositiveBalance());               // 이게 더 낫다
        
        assertThat(account.hasPositiveBalance()).isTrue();      // assertJ 방식
    }

    @Test
    public void testAssertFailure() {
        // 아래 testMatchesFailure()의 에러 메시지와 비교
        assertTrue(account.getName().startsWith("xyz"));
    }

    @Test
    public void testMatchesFailure() {
        // 아래 testAssertJMatchesFailure()의 가독성 및 에러 메시지와 비교
        org.hamcrest.MatcherAssert.assertThat(account.getName(), startsWith("xyz"));
    }
    
    @Test
    public void testAssertJMatchesFailure() {
        assertThat(account.getName()).startsWith("xyz");
    }

}
