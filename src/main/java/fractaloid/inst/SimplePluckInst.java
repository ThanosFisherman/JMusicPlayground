package fractaloid.inst;

import jm.audio.io.SampleOut;
import jm.audio.synth.Pluck;

public final class SimplePluckInst extends jm.audio.Instrument{
	private int sampleRate;
	private int channels;
        
	public SimplePluckInst(int sampleRate){
	    this(sampleRate, 1);
	}
	/** A constructor to set an initial sampling rate and number of channels.*/
	public SimplePluckInst(int sampleRate, int channels){
		this.sampleRate = sampleRate;
		this.channels = channels;
	}
	/** Initialisation method used to build the objects that this instrument will use */
	public void createChain(){
		Pluck plk = new Pluck(this, sampleRate, this.channels);
		SampleOut sout = new SampleOut( plk);
	}	
}

