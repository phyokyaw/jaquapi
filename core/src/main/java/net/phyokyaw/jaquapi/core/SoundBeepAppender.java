package net.phyokyaw.jaquapi.core;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class SoundBeepAppender extends AppenderSkeleton {

	@Override
	public void close() {}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent arg0) {
		if (arg0.getLevel().equals(Level.ERROR)) {
			try {
				SountUtil.playClip();
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
				// Can't do anything!
			}
		}
	}

}
