package fractaloid.inst;

import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.audio.io.SampleOut;
import jm.audio.synth.*;

/**
 * A sawtooth waveform instrument implementation
 * which iincludes a low pass filter that is swept by
 * an envelope and uses the multiply object to scale
 * the filter envelope depth. A second oscillator is added
 * and detuned slightly to give a fat chorused effect.
 * @author Andrew Brown
 */

public final class SawLPFInstF extends Instrument{
	//----------------------------------------------
	// Attributes
	//----------------------------------------------
	private int sampleRate;
        private int filterCutoff;
        private int channels;

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
	public SawLPFInstF(int sampleRate){
		this(sampleRate, 1000, 1);
	}
        
        /**
	 *  Constructor that sets sample rate and the number of channels
         * @param sampleRate The number of samples per second (quality)
         * @param filterCutoff The frequency above which overtones are cut
         */
     public SawLPFInstF(int sampleRate, int filterCutoff){
		this(sampleRate, filterCutoff, 1);
	}
        
        /**
	 *  Constructor that sets sample rate and the number of channels
         * @param sampleRate The number of samples per second (quality)
         * @param filterCutoff The frequency above which overtones are cut
         * @param channels 1 for Mono or 2 for Stereo
         */
        public SawLPFInstF(int sampleRate, int filterCutoff, int channels){
		this.sampleRate = sampleRate;
                this.filterCutoff = filterCutoff;
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
          Envelope filtEnv = new Envelope(this, this.sampleRate, this.channels,
              new double[] {0.0, 0.0, 0.5, 1.0, 1.0, 0.0});
          Value scalefactor = new Value(this, this.sampleRate, this.channels, (float)2000.0);
          Multiply mult = new Multiply(new AudioObject[] {filtEnv, scalefactor});
          Oscillator wave = new Oscillator(this, Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
          Oscillator wave2 = new Oscillator(this, Oscillator.SAWTOOTH_WAVE, this.sampleRate, this.channels);
          wave2.setFrqRatio((float)1.001); // for effect try 1.02
          Add summedWaves = new Add(new AudioObject[] {wave, wave2});
          Filter filt = new Filter(new AudioObject[] {summedWaves, mult}, this.filterCutoff, Filter.LOW_PASS);
          Envelope env = new Envelope(filt, 
              new double[] {0.0, 0.0, 0.05, 1.0, 0.2, 0.4, 0.8, 0.3, 1.0, 0.0});
		Volume vol = new Volume(env);
        Compressor comp = new Compressor(vol);
		SampleOut sout = new SampleOut(comp);
	}	
}

