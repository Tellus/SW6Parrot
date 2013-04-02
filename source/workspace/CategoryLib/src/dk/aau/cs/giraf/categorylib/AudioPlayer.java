/*
 * Copyright (C) 2011 Mette Bank, Rikke Jensen, Kenneth Brodersen, Thomas Pedersen
 * 
 * This file is part of digiPECS.
 * 
 * digiPECS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * digiPECS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with digiPECS.  If not, see <http://www.gnu.org/licenses/>.
 */

package dk.aau.cs.giraf.categorylib;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class AudioPlayer {
	private static AudioPlayer mInstance;
	private MediaPlayer mMediaPlayer = null; 
	
	private AudioPlayer(){}
	
	private static AudioPlayer getInstance()
	{
		if(mInstance==null)
			mInstance = new AudioPlayer(); 
		return mInstance; 
	}
	
	public static void open()
	{
		AudioPlayer ap = getInstance();
		ap.mMediaPlayer = new MediaPlayer();
	}
	
	public static void close()
	{
		AudioPlayer ap = getInstance();
		ap.mMediaPlayer.release();
	}
	
	public static void play(String path, final OnCompletionListener listener)
	{
		AudioPlayer ap = getInstance();
		 
		try {
			ap.mMediaPlayer.reset();
			ap.mMediaPlayer.setDataSource(path);
			ap.mMediaPlayer.prepare();
			if (listener != null)
				ap.mMediaPlayer.setOnCompletionListener(listener);
			ap.mMediaPlayer.start(); 
		} catch (IllegalArgumentException e) {
			Log.e("sw6", "Media player throw exception, see stack trace");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.e("sw6", "Media player throw exception, see stack trace");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("sw6", "Media player throw exception, see stack trace");
			e.printStackTrace();
		} 
	}
}
