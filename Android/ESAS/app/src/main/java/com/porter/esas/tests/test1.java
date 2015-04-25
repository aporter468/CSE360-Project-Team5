package com.porter.esas.tests;
import android.test.InstrumentationTestCase;
import com.porter.esas.Patient;
public class test1 extends InstrumentationTestCase {
    public void testnum() throws Exception {
        final int expected = 1;
        final int reality = 1;
        Patient p = new Patient(null,null);
       String testJSONString = "{\"surveys\":[{\"anxiety\":0,\"pain\":4,\"comments\":\"\",\"patientid\":2,\"wellbeing\":0,\"drowsiness\":4,\"appetite\":4,\"shortnessofbreath\":4,\"depression\":0,\"nausea\":4,\"timestamp\":1429290293758},{\"anxiety\":0,\"pain\":3,\"comments\":\"\",\"patientid\":2,\"wellbeing\":0,\"drowsiness\":3,\"appetite\":0,\"shortnessofbreath\":0,\"depression\":0,\"nausea\":0,\"timestamp\":1429290017213}]}\n";
        p.setupSurveys(testJSONString);
        assertEquals(p.getSurveys().size(), 2);

    }
    public void testvals() throws Exception {
        final int expected = 1;
        final int reality = 1;
        Patient p = new Patient(null,null);
        String testJSONString = "{\"surveys\":[{\"anxiety\":0,\"pain\":4,\"comments\":\"\",\"patientid\":2,\"wellbeing\":0,\"drowsiness\":4,\"appetite\":4,\"shortnessofbreath\":4,\"depression\":0,\"nausea\":4,\"timestamp\":1429290293758},{\"anxiety\":0,\"pain\":3,\"comments\":\"\",\"patientid\":2,\"wellbeing\":0,\"drowsiness\":3,\"appetite\":0,\"shortnessofbreath\":0,\"depression\":0,\"nausea\":0,\"timestamp\":1429290017213}]}\n";
        p.setupSurveys(testJSONString);
        //testing for pain == 4 in first survey
        assertEquals(p.getSurveys().get(0).getSurveyValues()[1], 4);

    }

}
