package io.cognitionbox.petra.examples.driverlesscars;

import io.cognitionbox.petra.examples.driverlesscars.steps.ChangeSignal1ToGreen;
import io.cognitionbox.petra.examples.driverlesscars.steps.ChangeSignal2ToGreen;
import io.cognitionbox.petra.examples.driverlesscars.steps.MoveCarIntoJunction;
import io.cognitionbox.petra.examples.driverlesscars.steps.MoveCarOutOfJunction;
import io.cognitionbox.petra.lang.PGraph;

public class Simulate extends PGraph<Simlulation> {
    {
        setSleepPeriod(1000);
        pre(Simlulation.class, s->
                (s.getNoOfCarsInJunction()<=1 &&
                ((s.signalAisRED() && s.signalBisGREEN()) ^
                        (s.signalAisGREEN() && s.signalBisRED())) ) );
        lc(s->(s.getNoOfCarsInJunction()<=1 &&
                ((s.signalAisRED() && s.signalBisGREEN()) ^
                        (s.signalAisGREEN() && s.signalBisRED())) ) );
        step(new ChangeSignal1ToGreen());
        step(new ChangeSignal2ToGreen());
        step(new MoveCarIntoJunction());
        step(new MoveCarOutOfJunction());
        post(Simlulation.class,c->getCurrentIteration()==10);
    }
}