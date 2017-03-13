package nebulous.sound.component;

import static org.lwjgl.openal.AL10.*;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.stb.STBVorbisInfo;
import static org.lwjgl.stb.STBVorbis.*;

import nebulous.entity.Component;


public class Sound extends Component {

	private int sbo = -1;
	
    private ShortBuffer pcm = null;

    private ByteBuffer vorbis = null;
    
	public Sound(String location) {
		sbo = alGenBuffers();
		
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
//            ShortBuffer pcm = readVorbis(location, 32 * 1024, info);
            alBufferData(sbo, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }
	}
	
}
