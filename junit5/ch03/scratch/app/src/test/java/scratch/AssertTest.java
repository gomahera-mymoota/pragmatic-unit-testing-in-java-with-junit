package scratch;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AssertTest {

    private Account acnt;

    @BeforeEach
    public void createAccount() {
        acnt = new Account("an account name");
    }

    @Test
    public void testHasPositiveBalance() {
        acnt.deposit(50);

        assertTrue(acnt.hasPositiveBalance());
    }

    @Test
    public void testDepositIncreasesBalance() {
        var initialBalance = acnt.getBalance();
        acnt.deposit(100);

        assertTrue(acnt.getBalance() > initialBalance);   // JUnit5

        assertEquals(100, acnt.getBalance());    // JUnit5, 정확한 값으로 단언하는 것이 좋다
        
        assertThat(acnt.getBalance()).isEqualTo(100);    // assertJ
    }

    @Test
    public void testDepositIncreasesBalance_hamcrestAssertTrue() {
        acnt.deposit(50);

        org.hamcrest.MatcherAssert.assertThat(acnt.getBalance() > 0, is(true));       // 장황하다

        assertTrue(acnt.getBalance() > 0);                   // 이게 낫고
        assertTrue(acnt.hasPositiveBalance());               // 이게 더 낫다
        
        assertThat(acnt.hasPositiveBalance()).isTrue();      // assertJ 방식
    }

    @Disabled
    @Test
    public void testAssertFailure() {
        // 아래 testMatchesFailure()의 에러 메시지와 비교
        assertTrue(acnt.getName().startsWith("xyz"));
    }

    @Disabled
    @Test
    public void testMatchesFailure() {
        // 아래 testAssertJMatchesFailure()의 가독성 및 에러 메시지와 비교
        org.hamcrest.MatcherAssert.assertThat(acnt.getName(), startsWith("xyz"));
    }
    
    @Disabled("for test to be continued")
    @Test
    public void testAssertJMatchesFailure() {
        assertThat(acnt.getName()).startsWith("xyz");
    }

    @Disabled
    @Test
    @ExpectToFail
    public void testComparesArraysFailing() {
        assertThat(new String[] {"a", "b", "c"}).isEqualTo(new String[] {"a", "b"});
    }

    @Disabled
    @Test
    @ExpectToFail
    public void testComparesCollectionsFailing() {
        assertThat(Arrays.asList(new String[] {"a"})).isEqualTo(Arrays.asList(new String[] {"a", "b"}));
    }

    @Test
    public void testComparesArraysPassing() {
        assertThat(new String[] {"a", "b"}).isEqualTo(new String[] {"a", "b"});
    }

    @Test
    public void testComparesCollectionsPassing() {
        assertThat(Arrays.asList(new String[] {"a"})).isEqualTo(Arrays.asList(new String[] {"a"}));
    }

    @Test
    public void testVariousMatcherTests() {
        // decorator
        var acct = new Account("my big fat acct");

        assertThat(acct.getName()).isEqualTo("my big fat acct");
        assertThat(acct.getName()).is("my big fat acct");

        assertThat(acct.getName()).startsWith("my").endsWith("acct");
        assertThat(acct.getName()).
        startsWith("my").endsWith("acct");

        assert

        assertThat(acct.getName()).
        assertThat(acct.getName(), anyOf(startsWith("my"), endsWith("loot")));

        assertThat(acct.getName(), not(equalTo("plunderings")));

        assertThat(acct.getName(), is(not(nullValue())));
        assertThat(acct.getName(), is(notNullValue()));

        assertThat(acct.getName(), isA(String.class));

        assertThat(acct.getName(), is(notNullValue()));  // not helpful
        assertThat(acct.getName(), equalTo("my big fat acct"));
    }

}
