package org.samples;


import com.google.gson.GsonBuilder;
import org.approvaltests.Approvals;
import org.approvaltests.JsonApprovals;
import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.legacybehavior.Person;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTests
{
  @Test
  public void testNormalJunit()
  {
    assertEquals(5, 5);
  }
  @Test
  public void testStrings()
  {
    Approvals.verify("Hello World!");
  }
  @Test
  public void testArrays()
  {
    String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
    Arrays.sort(names);
    Approvals.verifyAll("", names);
  }

  @Test
  public void testObject() {
    Rectangle rectangle = new Rectangle(5, 5, 5, 7);
    Approvals.verify(rectangle.toString());
  }

  @Test
  public void testJsonApprovals() {
    Person hero = Person.getInstance("Tony Stark", "Iron Man", null,null, null);
    JsonApprovals.verifyAsJson(hero, GsonBuilder::serializeNulls);
  }

  @Test
  public void testCombinationApprovals() {
    Integer[] lengths = new Integer[]{4, 5, 6};
    String[] words = new String[]{"Bookkeeper", "applesauce"};
    CombinationApprovals.verifyAllCombinations((i, s)->s.substring(0,i),lengths,words);
  }

  @ParameterizedTest
  @ValueSource(strings = {"parameter1", "parameter2"})
  void sampleParameterizedTest(String parameter)
  {
    try (NamedEnvironment en = NamerFactory.withParameters(parameter))
    {
      // your code goes here
      Object output = parameter;
      Approvals.verify(output);
    }
  }

}
