import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert;
import java.lang.Object;
import java.lang.Enum<KeyCode>;

public class DirectoryTest {

Directory dir = new Directory();

@Test

//Tests finding a User in the PatientList
public void findUser() {
    if(dir.addPatient("a", "b", "c", "d", "e")) {
       int x = dir.findPatientByName("a");
       assertEquals("Patient not Found by Name", -1, x);
    }
}

@Test

//Tests finding a User by Username in the PatientList
public void findUserName() {
    if(dir.addPatient("a", "b", "c", "d", "e")) {
        int x = dir.findPatient("b");
        assertEquals("Patient not Found by UserName", -1, x);
    }
}

@Test

//Tests Finding Doctor in List
public void doctorExists() {
    if(dir.addDoctor("a", "b", "c", "d", "e")) {
        boolean x = dir.doctorExists("a");
        assertTrue("Doctor added properly", x);
    }
}

@Test

//Tests Finding Doctor in List
public void findDoctor() {
    if(dir.addDoctor("a", "b", "c", "d", "e")) {
        int x = dir.findDoctor("a");
        assertEquals("Doctor not Added properly", -1, x);
    }
}

@Test

//Tests GUI Features
public void guitTest() {
    Robot rob = new Robot();
    if(dir.addDoctor("a", "b", "c", "d", "e")) {
        rob.mouseMove(dir.getLocationUserName());
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        rob.keyPress(A);
        rob.keyRelease(A);

        rob.mouseMove(dir.getLocationPassword());
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        rob.keyPress(B);
        rob.keyRelease(B);

        rob.mouseMove(dir.getLocationLogin());
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);

        dir.addSurvey();
        rob.mouseMove(dir.getLocationHistory());
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}
}
