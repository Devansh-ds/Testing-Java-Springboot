package com.devansh.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ClassATest.class, ClassBTest.class, ClassCTest.class})
public class SuiteTest {

}
