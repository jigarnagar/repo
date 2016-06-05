package com.adp.vehicleidentifier;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.adp.vehicleidentifier.utils.JaxbUtilsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses ({
    VehicleIdentityProcessorTest.class,
    JaxbUtilsTest.class
})
public class VehicleIdentifyTestSuite {

}
