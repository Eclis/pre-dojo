package pre.dojo.eclis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pre.dojo.eclis.util.SupportTest;

@RunWith(Suite.class)
@SuiteClasses({
	SupportTest.class,
	JogadorTest.class,
	LogTest.class,
	MainTest.class,
	PartidaTest.class,
})

public class Predojo {
}
