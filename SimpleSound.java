package doppler2;

import java.io.*;
import javax.sound.sampled.*;

public class SimpleSound {
	
	/*
	 * Tworzenie prostego, monotonnego dzwieku.
	 * Tutaj tez sie go zapisuje i tworzy pliki TMP
	 */
	
	@SuppressWarnings("unused")
	private Mediator mediator;
	private File emittedSoundTemp, receivedSoundTemp;
	private File emittedSoundPerm, receivedSoundPerm;

	public void generatedEmittedWave(long freq) {
		
		int duration = 5000;			//5000ms = 5s
		float sampleRate = 44100;		//najwyzsze probkowanie, najlepszy dzwiek
		
		byte[] buf = new byte[2 * duration * (int) (sampleRate / 1000)]; //dzielenie przez 1000 bo duration jest w milisekundach
		for (int i = 0; i < buf.length; i += 2) {
			double angle = i / (sampleRate / freq ) *2 * Math.PI;
			short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
			buf[i] = (byte) (sample & 0xFF);
			buf[i + 1] = (byte) ((sample >> 8 ) & 0xFF);
		}
		
		emittedSoundTemp = new File("emitted_sound_temp.wav");
		try {
			
            FileOutputStream fileOutputStream = new FileOutputStream(emittedSoundTemp);

            AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, buf.length / 2);

            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	emittedSoundTemp.deleteOnExit();
        }
		
	}
	
	public void generateReceivedSound(long freq, double factor) {
		int duration = 5000;
		float sampleRate = 44100;
		
		byte[] buf = new byte[2 * duration * (int) (sampleRate / 1000)]; //dzielenie przez 1000 bo duration jest w milisekundach
		for (int i = 0; i < buf.length; i += 2) {
			double angle = i / (sampleRate / (freq * factor)) *2 * Math.PI;
			short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
			buf[i] = (byte) (sample & 0xFF);
			buf[i + 1] = (byte) ((sample >> 8 ) & 0xFF);
		}
		
		receivedSoundTemp = new File("received_sound_temp.wav");
		try {
           
            FileOutputStream fileOutputStream = new FileOutputStream(receivedSoundTemp);

            AudioFormat audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, buf.length / 2);

            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	receivedSoundTemp.deleteOnExit();
        }
	}
	
	public void saveSoundsToFile(long freq, double factor) {
		int duration = 5000;
		float sampleRate = 44100;
		
		byte[] bufR = new byte[2 * duration * (int) (sampleRate / 1000)]; //dzielenie przez 1000 bo duration jest w milisekundach
		for (int i = 0; i < bufR.length; i += 2) {
			double angle = i / (sampleRate / (freq * factor)) *2 * Math.PI;
			short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
			bufR[i] = (byte) (sample & 0xFF);
			bufR[i + 1] = (byte) ((sample >> 8 ) & 0xFF);
		}
		
		receivedSoundPerm = new File("received_sound.wav");
		try {

            FileOutputStream fileOutputStreamR = new FileOutputStream(receivedSoundPerm);

            AudioFormat audioFormatR = new AudioFormat(sampleRate, 16, 1, true, false);

            ByteArrayInputStream byteArrayInputStreamR = new ByteArrayInputStream(bufR);
            AudioInputStream audioInputStreamR = new AudioInputStream(byteArrayInputStreamR, audioFormatR, bufR.length / 2);

            AudioSystem.write(audioInputStreamR, AudioFileFormat.Type.WAVE, fileOutputStreamR);
            fileOutputStreamR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		byte[] bufE = new byte[2 * duration * (int) (sampleRate / 1000)]; //dzielenie przez 1000 bo duration jest w milisekundach
		for (int i = 0; i < bufE.length; i += 2) {
			double angle = i / (sampleRate / freq ) *2 * Math.PI;
			short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
			bufE[i] = (byte) (sample & 0xFF);
			bufE[i + 1] = (byte) ((sample >> 8 ) & 0xFF);
		}
		
		emittedSoundPerm = new File("emitted_sound.wav");
		try {

            FileOutputStream fileOutputStreamE = new FileOutputStream(emittedSoundPerm);

            AudioFormat audioFormatE = new AudioFormat(sampleRate, 16, 1, true, false);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bufE);
            AudioInputStream audioInputStreamE = new AudioInputStream(byteArrayInputStream, audioFormatE, bufE.length / 2);

            AudioSystem.write(audioInputStreamE, AudioFileFormat.Type.WAVE, fileOutputStreamE);
            fileOutputStreamE.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void deleteTempFiles() {				//usuwanie plikow TMP wywolywane przyciskiem RESET
		receivedSoundTemp.delete();
		emittedSoundTemp.delete();
	}
	
	public void playEmittedSound() {			//odtwarzanie dzwieku emitowanego, z przycisku
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(emittedSoundTemp);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void playReceivedSound() {			// odtwarzanie dzwieku odbieranego, z przycisku
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(receivedSoundTemp);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void setMediator(Mediator mediator) {
		this.mediator = mediator;	
	}
}
