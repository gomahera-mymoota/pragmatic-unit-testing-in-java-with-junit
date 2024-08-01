package scratch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AssertTest {

    private Account account;

    @Before
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

        assertTrue(account.getBalance() > initialBalance);

        org.junit.Assert.assertThat(account.getBalance(), equalTo(100));    // deprecated
        org.hamcrest.MatcherAssert.assertThat(account.getBalance(), equalTo(100));
    }

    @Test
    public void testDepositIncreasesBalance_hamcrestAssertTrue() {
        account.deposit(50);

        assertThat(account.getBalance() > 0, is(true));       // 장황하다

        assertTrue(account.getBalance() > 0);                       // 이게 낫고
        assertTrue(account.hasPositiveBalance());                   // 이게 더 낫다
    }

    @Ignore
    @Test
    public void testAssertFailure() {
        // 아래 testMatchesFailure()의 에러메시지와 비교
        assertTrue(account.getName().startsWith("xyz"));
    }

    @Ignore
    @Test
    public void testMatchesFailure() {
        assertThat(account.getName(), startsWith("xyz"));
    }

    @Ignore
    @Test
    @ExpectToFail
    public void testComparesArraysFailing() {
        assertThat(new String[] {"a", "b", "c"}, equalTo(new String[] {"a", "b"}));
    }

    @Ignore
    @Test
    @ExpectToFail
    public void testComparesCollectionsFailing() {
        assertThat(Arrays.asList(new String[] {"a"}), equalTo(Arrays.asList(new String[] {"a", "b"})));
    }

    @Test
    public void testComparesArraysPassing() {
        assertThat(new String[] {"a", "b"}, equalTo(new String[] {"a", "b"}));
    }

    @Test
    public void testComparesCollectionsPassing() {
        assertThat(Arrays.asList(new String[] {"a"}), equalTo(Arrays.asList(new String[] {"a"})));
    }

    @Test
    public void testVariousMatcherTests() {
        // decorator
        var account = new Account("my big fat acct");

        assertThat(account.getName(), equalTo("my big fat acct"));
        assertThat(account.getName(), is(equalTo("my big fat acct")));

        assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct")));

        assertThat(account.getName(), anyOf(startsWith("my"), endsWith("loot")));

        assertThat(account.getName(), not(equalTo("plunderings")));

        assertThat(account.getName(), is(not(nullValue())));
        assertThat(account.getName(), is(notNullValue()));

        assertThat(account.getName(), isA(String.class));

        assertThat(account.getName(), is(notNullValue()));  // not helpful
        assertThat(account.getName(), equalTo("my big fat acct"));
    }

    @Test
    public void testSameInstance() {
        var a = new Account("a");
        var aPrime = new Account("a");

        assertThat(a, not(sameInstance(aPrime)));
    }

    @Test
    public void testMoreMatchers() {
        var account = new Account(null);

        assertThat(account.getName(), is(nullValue()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testItems() {
        List<String> names = new ArrayList<>();
        names.add("Moe");
        names.add("Larry");
        names.add("Curly");

        assertThat(names, hasItem("Curly"));

        assertThat(names, hasItems("Curly", "Moe"));

        assertThat(names, hasItem(endsWith("y")));

        assertThat(names, hasItems(endsWith("y"), startsWith("C")));

        assertThat(names, not(everyItem(endsWith("y"))));
    }

}
