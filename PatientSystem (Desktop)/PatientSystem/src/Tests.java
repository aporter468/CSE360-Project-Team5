import static org.junit.Assert.*;

import org.junit.Test;


public class Tests {

Directory dir = new Directory();

@Test

//Tests finding a User in the PatientList
public void findUser() {
    if(dir.addPatient("a", "b", "c", "d", "e", "f", "g")) {
       int x = dir.findPatientByName("a");
       if(x!= -1)
       {
           boolean found = true;
           assertTrue("Patient a is Found", found);
       }
       assertEquals("Patient a not Found by Name", -1, x);
       int y = dir.findPatientByName("b");
       assertEquals("Patient b not Found by Name", -1, y);
    }
}

@Test

//Tests finding a User by User name in the PatientList
public void findUserName() {
    if(dir.addPatient("a", "b", "c", "d", "e", "f", "g")) {
        int x = dir.findPatient("b");
        if(x!= -1)
        {
           boolean found = true;
           assertTrue("Patient a is Found", found);
        }
        assertEquals("Patient a not Found by UserName", -1, x);
        int y = dir.findPatient("c");
        assertEquals("Patient b not Found by Name", -1, y);
    }
}

@Test

//Tests Finding Doctor in List
public void doctorExists() {
    if(dir.addDoctor("a", "b", "c", "d", "e", "f", "g")) {
        boolean x = dir.doctorExists("a");
        assertTrue("Doctor a exists", x);
        boolean y = dir.doctorExists("b");
        assertFalse("Doctor b does not exist", y);
    }
}

@Test

//Tests Finding Doctor in List
public void findDoctor() {
    if(dir.addDoctor("a", "b", "c", "d", "e", "f", "g")) {
        int x = dir.findDoctor("a");
        if(x!= -1)
        {
           boolean found = true;
           assertTrue("Doctor a is Found", found);
        }
        assertEquals("Doctor a not found", -1, x);
        int y = dir.findDoctor("b");
        assertEquals("Doctor b not found", -1, y);
    }
}

}
