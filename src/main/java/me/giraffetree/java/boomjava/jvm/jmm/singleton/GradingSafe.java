package me.giraffetree.java.boomjava.jvm.jmm.singleton;

import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.Outcome;

@Description("Tests the safe singleton pattern.")
@Outcome(id = "0",  expect = Expect.FORBIDDEN,  desc = "Factory returned null singleton.")
@Outcome(id = "1",  expect = Expect.FORBIDDEN,  desc = "The singleton data is null.")
@Outcome(id = "42", expect = Expect.ACCEPTABLE, desc = "The singleton is observed in full.")
public class GradingSafe {
}