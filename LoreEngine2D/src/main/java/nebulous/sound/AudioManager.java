package nebulous.sound;

import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

public class AudioManager {
	
	public static void init() {
		
		long device = alcOpenDevice((ByteBuffer) null);
        if (device == 0)
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        
        long context = alcCreateContext(device, (IntBuffer) null);
        if (context == 0)
            throw new IllegalStateException("Failed to create OpenAL context.");
        
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
		
	}

}
