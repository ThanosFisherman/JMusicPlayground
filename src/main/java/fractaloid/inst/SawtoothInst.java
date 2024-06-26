package fractaloid.inst;

import jm.audio.Instrument;
import jm.audio.io.SampleOut;
import jm.audio.synth.*;

/**
 * A basic sawtooth waveform instrument implementation
 * which implements envelope , pan, and volume control
 * @author Andrew Brown and Andrew Sorensen
 */

public final class SawtoothInst extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	
	/** The points to use in the construction of Envelopes */
	private EnvPoint[] pointArray = new EnvPoint[10];
	private int sampleRate;
    private SampleOut sout;

	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	/**
	 * Basic default constructor to set an initial 
	 * sampling rate and buffersize in addition
	 * to the neccessary frequency relationships 
	 * and volumes for each frequency to be added
	 * the instrument
	 * @param sampleRate 
	 * @param buffersize
	 * @param frequencies the relative freqencies to use
	 * @param volumes the volumes to use for the frequencies
	 */
	public SawtoothInst(int sampleRate){
		this.sampleRate = sampleRate;
		EnvPoint[] tempArray = {
			new EnvPoint((float)0.0, (float)0.0),
			new EnvPoint((float)0.02, (float)1.0),
			new EnvPoint((float)0.15, (float)0.6),
			new EnvPoint((float)0.9, (float)0.3),
			new EnvPoint((float)1.0, (float)0.0)
		};
		pointArray = tempArray;
	}

	//----------------------------------------------
	// Methods 
	//----------------------------------------------
	   
	/**
	 * Initialisation method used to build the objects that
	 * this instrument will use
	 */
	public void createChain(){
		Oscillator wt = new Oscillator(this, Oscillator.SAWTOOTH_WAVE,
                    this.sampleRate, 2);
		Envelope env = new Envelope(wt, pointArray);
		Volume vol = new Volume(env);
		StereoPan span = new StereoPan(vol);
		if(output == RENDER) sout = new SampleOut(span);
	}	
}

