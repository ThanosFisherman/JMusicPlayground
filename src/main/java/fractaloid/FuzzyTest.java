package fractaloid;

import jm.JMC;
import jm.music.tools.fuzzy.FuzzyNumber;
import jm.music.tools.fuzzy.FuzzySet;

public class FuzzyTest implements JMC {
    FuzzyNumber pianissimo, piano, mezzoPiano, mezzoForte, forte, fortissimo;
    FuzzySet dynamicSet;

    public static void main(String[] args) {
        new FuzzyTest();
    }

    public FuzzyTest() {
        dynamicSet = new FuzzySet();
        pianissimo = new FuzzyNumber(0, 0, 40);
        dynamicSet.add(pianissimo);
        piano = new FuzzyNumber(20, 0, 55);
        dynamicSet.add(piano);
        mezzoPiano = new FuzzyNumber(35, 20, 65);
        dynamicSet.add(mezzoPiano);
        mezzoForte = new FuzzyNumber(50, 30, 95);
        dynamicSet.add(mezzoForte);
        forte = new FuzzyNumber(80, 50, 127);
        dynamicSet.add(forte);
        fortissimo = new FuzzyNumber(127, 100, 127);
        dynamicSet.add(fortissimo);

        // test
        for(int i=0; i<127; i++) {
            for(int j=0; j<i; j++) {
                System.out.print(" ");
            }
            System.out.println(".");
            int out = (int)dynamicSet.getOutput(i);
            for(int j=0; j<out; j++) {
                System.out.print(" ");
            }
            System.out.println("+");
        }
    }
}