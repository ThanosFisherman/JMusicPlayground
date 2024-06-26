package fractaloid.inst;

import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.audio.io.SampleOut;
import jm.audio.synth.*;

/**
 * A  sawtooth waveform instrument implementation
 * which iincludes a low pass filter that is changed by
 * the note's velocity.
 * @author Andrew Brown
 */

public final class SawLPFInstB extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	private int sampleRate;
        private int filterCutoff;
        private int channels;
        private double dynScale;

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
	 */
	public SawLPFInstB(int sampleRate){
		this(sampleRate, 200, 160, 1);
	}
	
	 /**
	 *  Constructor that sets sample rate and the number of channels
         * @param sampleRate The number of samples per second (quality)
         * @param filterCutoff The frequency above which overtones are cut
         */
	public SawLPFInstB(int sampleRate, int filterCutoff){
		this(sampleRate, filterCutoff, 160, 1);
	}
        
        /**
	 *  Constructor that sets sample rate and the number of channels
         * @param sampleRate The number of samples per second (quality)
         * @param filterCutoff The frequency above which overtones are cut
         * @param dynScale The amount to multiply the dynamic value by in
         *				order for it change the filter cutoff
         */
     public SawLPFInstB(int sampleRate, int filterCutoff, double dynScale){
		this(sampleRate, filterCutoff, dynScale, 1);
	}
        
        /**
	 *  Constructor that sets sample rate and the number of channels
         * @param sampleRate The number of samples per second (quality)
         * @param filterCutoff The frequency above which overtones are cut
         * @param channels 1 for Mono or 2 for Stereo
         */
        public SawLPFInstB(int sampleRate, int filterCutoff, double dynScale, int channels){
		this.sampleRate = sampleRate;
                this.filterCutoff = filterCutoff;
                this.dynScale = dynScale;
                this.channels = channels;
	}

	//----------------------------------------------
	// Methods 
	//----------------------------------------------
	   
	/**
	 * Initialisation method used to build the objects that
	 * this instrument will use and specify thier interconnections.
	 */
	public void createChain(){
		Value modSource = new Value(this, this.sampleRate, 1, Value.NOTE_DYNAMIC);
		Value modAmount = new Value(this, this.sampleRate, 1, (float)this.dynScale);
          Multiply filterControl = new Multiply(new AudioObject[] {modSource, modAmount});
		Oscillator wave = new Oscillator(this, Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
          Filter filt = new Filter(new AudioObject[] {wave, filterControl}, this.filterCutoff, Filter.LOW_PASS);
          Envelope env = new Envelope(filt, 
              new double[] {0.0, 0.0, 0.05, 1.0, 0.2, 0.4, 0.8, 0.3, 1.0, 0.0});
		Volume vol = new Volume(env);
		SampleOut sout = new SampleOut(vol);
	}	
}

