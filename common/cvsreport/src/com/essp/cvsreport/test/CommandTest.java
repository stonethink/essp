package com.essp.cvsreport.test;

import junit.framework.TestCase;
import java.io.PrintStream;
import com.essp.cvsreport.*;

public class CommandTest extends TestCase {
  Command f;
  PrintStream out = System.out;

  public void testHistory(){
    f = new Command.HistoryCommand();
    try {
      f.execute();
    } catch (Exception ex) {
      ex.printStackTrace();
      fail();
    }
  }


}
