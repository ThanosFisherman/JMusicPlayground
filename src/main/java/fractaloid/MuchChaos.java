package fractaloid;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

/**
 * A short example which generates a chaotic chromatic melody
 * and writes to a MIDI file called chaos.mid
 * Algorithm taken from "Computer Music" by Dodge and Jerse, P.373.
 *
 * @author Andrew Brown
 */
public final class MuchChaos implements JMC {
    public static void main(String[] args) {
        Score score = new Score("JMDemo - Chaos", 120);
        Part guitarPart = new Part("Guitar", NYLON_GUITAR, 0);
        Part pianoPart = new Part("Piano", PIANO, 1);
        Phrase phr = new Phrase(0.0);
        Phrase phr2 = new Phrase(0.0);
        double xold = 0.0; // initial x position
        double x, y; // temp variables
        double yold = 0.0; // initial y position

        //---------------
        // first phrase
        //----------------
        double a = 1.4; // first constant. For oscillation try 1.04
        double b = 0.3; //second constant. For oscillation try 0.3

        // create a phrase of chaotically pitched quavers over a limited MIDI range.
        for (short i = 0; i < 100; i++) {
            x = 1 + yold - a * xold * xold;
            y = b * xold;
            // map the x value across a few octaves
            Note note = new Note((int) ((x * 24) + 48), C);
            phr.addNote(note);
            xold = x;
            yold = y;
        }
        guitarPart.addPhrase(phr);

        //---------------
        // Second phrase
        //----------------
        a = 1.401; // first constant. For oscillation try 1.04
        b = 0.3; // second constant. For oscillation try 0.3
        xold = 0.0;
        yold = 0.0;

        // create a phrase of chaotically pitched quavers over a limited MIDI range.
        for (short i = 0; i < 100; i++) {
            x = 1 + yold - a * xold * xold;
            y = b * xold;
            // map the x value across a few octaves
            Note note = new Note((int) ((x * 24) + 48), C);
            phr2.addNote(note);
            xold = x;
            yold = y;
        }
        pianoPart.addPhrase(phr2);

        // add the part that to a score
        score.addPart(guitarPart);
        score.addPart(pianoPart);

        // create a MIDI file of the score
        Write.midi(score, "muchChaos.mid");

        //print out the score
        View.show(score);
    }
}