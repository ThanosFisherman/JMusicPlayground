package fractaloid;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import jm.util.Write;

/**
 * A short example which generates a chromatic fractal melody
 * and writes it to a MIDI file called fractal.mid
 * @author Andrew Brown
 * Fractal algorithm by R. F. Voss as cited in Dodge and Jerse "Computer Music" p.369.
 */
public final class Fractal implements JMC {
    Score score = new Score("JMDemo - Fractal");
    Part part = new Part("Piano", PIANO);
    Phrase phr = new Phrase();

    float sum;
    float[] rg = new float[16];
    int k, kg, ng, threshold;
    int np = 1;
    int nbits = 1;
    int npts = 48; //number of notes
    float nr = (float)(npts);

    public static void main(String[] args){
        Fractal fract = new Fractal();
    }

    public Fractal() {
        part.addPhrase(phr);
        score.addPart(part);
        setupMath();
        calcNotes();
        output();
    }

    private void setupMath() {
        nr = nr/2;
        // create a phrase of fractal pitches quavers over the full MIDI range.
        System.out.println("Calculating fractal melody. . .");
        while (nr > 1) {
            nbits++;
            np = 2 * np;
            nr = nr/2;
        }
        for(kg=0; kg<nbits; kg++) {
            rg[kg] = (float)(Math.random());
        }
    }

    private void calcNotes() {
        for(k=0; k<npts; k++) {
            threshold = np;
            ng = nbits;
            while(k%threshold != 0) {
                ng--;
                threshold = threshold / 2;
            }
            sum = 0;
            for(kg=0; kg<nbits; kg++) {
                if(kg<ng) {rg[kg]=(float)(Math.random());}
                sum += rg[kg];
            }
            Note note = new Note((int)(sum/nbits*127), Q);
            phr.addNote(note);
        }
    }

    private void output() {
        View.show(score);
        Write.midi(score, "FractalMusic.mid");
    }
}