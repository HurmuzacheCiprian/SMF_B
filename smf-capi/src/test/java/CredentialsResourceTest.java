import com.smf.main.CredentialsService;
import com.smf.main.domain.UserDao;
import com.smf.main.model.UserResponse;
import com.smf.main.resources.CredentialsResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

/**
 * Created by cipriach on 08.12.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CredentialsResourceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private CredentialsService credentialsService;

    private CredentialsResource classUnderTest;

    @Before
    public void init() {
        classUnderTest = new CredentialsResource(userDao, credentialsService);
    }

    @Test
    public void testUnSuccessfulLogin() {
        Mockito.when(credentialsService.checkUser(null)).thenReturn(false);
        ResponseEntity<UserResponse> response = classUnderTest.checkLoginCredentials(null);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().isOk() == false);
    }

    @Test
    public void testSuccessfulLogin() {
        Mockito.when(credentialsService.checkUser(null)).thenReturn(true);
        ResponseEntity<UserResponse> response = classUnderTest.checkLoginCredentials(null);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().isOk());
    }

}
