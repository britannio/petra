package io.cognitionbox.petra.examples.simple2.e_parallel_sequences2;

public class Y {
    @Override
    public String toString() {
        return "Y{" +
                "state='" + state + '\'' +
                '}';
    }

    public Y(State state) {
        this.state = state;
    }

    State state;

    public boolean isA(){ return state== State.A; }
    public boolean isB(){ return state== State.B; }
    public boolean isC(){ return state== State.C; }
    public boolean isABC(){ return isA() ^ isB() ^ isC();}
}