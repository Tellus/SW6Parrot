package dk.aau.cs.giraf.parrot;

import java.util.ArrayList;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

public class TestData {

	
	public void addPictogramsToDatabase(long childId)
	{
		ArrayList<Pictogram> pictograms = new ArrayList<Pictogram>();

		Pictogram badePic = new Pictogram("Bade", "/sdcard/Pictogram/Bade.png", null, "/sdcard/Pictogram/bade.wma");
		badePic.setNewPictogram(true);
		Pictogram b�rsteT�nderPic = new Pictogram("B�rste T�nder", "/sdcard/Pictogram/B�rste_T�nder.png", null, "/sdcard/Pictogram/b�rste_t�nder.wma");
		b�rsteT�nderPic.setNewPictogram(true);
		Pictogram drikkePic = new Pictogram("Drikke", "/sdcard/Pictogram/Drikke.png", null, "/sdcard/Pictogram/drikke.wma");
		drikkePic.setNewPictogram(true);
		Pictogram duPic = new Pictogram("Du", "/sdcard/Pictogram/Du.png", null, "/sdcard/Pictogram/du.wma");
		duPic.setNewPictogram(true);
		Pictogram filmPic = new Pictogram("Film", "/sdcard/Pictogram/Film.png", null, "/sdcard/Pictogram/film.wma");
		filmPic.setNewPictogram(true);
		Pictogram forH�jtPic = new Pictogram("For H�jt", "/sdcard/Pictogram/For_H�jt.png", null, "/sdcard/Pictogram/for_h�jt.wma");
		forH�jtPic.setNewPictogram(true);
		Pictogram g�Pic = new Pictogram("G�", "/sdcard/Pictogram/G�.png", null, "/sdcard/Pictogram/g�.wma");
		g�Pic.setNewPictogram(true);
		Pictogram jaPic = new Pictogram("Ja", "/sdcard/Pictogram/Ja.png", null, "/sdcard/Pictogram/ja.wma");
		jaPic.setNewPictogram(true);
		Pictogram k�rePic = new Pictogram("K�re", "/sdcard/Pictogram/K�re.png", null, "/sdcard/Pictogram/k�re.wma");
		k�rePic.setNewPictogram(true);
		Pictogram laveMadPic = new Pictogram("Lave Mad", "/sdcard/Pictogram/Lave_Mad.png", null, "/sdcard/Pictogram/lave_mad.wma");
		laveMadPic.setNewPictogram(true);
		Pictogram legePic = new Pictogram("Lege", "/sdcard/Pictogram/Lege.png", null, "/sdcard/Pictogram/lege.wma");
		legePic.setNewPictogram(true);
		Pictogram migPic = new Pictogram("Mig", "/sdcard/Pictogram/Mig.png", null, "/sdcard/Pictogram/mig.wma");
		migPic.setNewPictogram(true);
		Pictogram morgenRoutinePic = new Pictogram("Morgen Routine", "/sdcard/Pictogram/Morgen_Routine.png", null, "/sdcard/Pictogram/morgen_routine.wma");
		morgenRoutinePic.setNewPictogram(true);
		Pictogram nejPic = new Pictogram("Nej", "/sdcard/Pictogram/Nej.png", null, "/sdcard/Pictogram/nej.wma");
		nejPic.setNewPictogram(true);
		Pictogram sePic = new Pictogram("Se", "/sdcard/Pictogram/Se.png", null, "/sdcard/Pictogram/se.wma");
		sePic.setNewPictogram(true);
		Pictogram sideNedPic = new Pictogram("Side Ned", "/sdcard/Pictogram/Side_Ned.png", null, "/sdcard/Pictogram/side_ned.wma");
		sideNedPic.setNewPictogram(true);
		Pictogram spilleComputerPic = new Pictogram("Spille Computer", "/sdcard/Pictogram/Spille_Computer.png", null, "/sdcard/Pictogram/spille_computer.wma");
		spilleComputerPic.setNewPictogram(true);
		Pictogram stopPic = new Pictogram("Stop", "/sdcard/Pictogram/Stop.png", null, "/sdcard/Pictogram/stop.wma");
		stopPic.setNewPictogram(true);
		Pictogram sultenPic = new Pictogram("Sulten", "/sdcard/Pictogram/Sulten.png", null, "/sdcard/Pictogram/sulten.wma");
		sultenPic.setNewPictogram(true);
		Pictogram s�vnigPic = new Pictogram("S�vnig", "/sdcard/Pictogram/S�vnig.png", null, "/sdcard/Pictogram/s�vnig.wma");
		s�vnigPic.setNewPictogram(true);
		Pictogram taleSammenPic = new Pictogram("Tale Sammen", "/sdcard/Pictogram/Tale_Sammen.png", null, "/sdcard/Pictogram/tale_sammen.wma");
		taleSammenPic.setNewPictogram(true);
		Pictogram t�rstigPic = new Pictogram("T�rstig", "/sdcard/Pictogram/T�rstig.png", null, "/sdcard/Pictogram/t�rstig.wma");
		t�rstigPic.setNewPictogram(true);
		Pictogram v�reStillePic = new Pictogram("V�re Stille", "/sdcard/Pictogram/V�re_Stille.png", null, "/sdcard/Pictogram/v�re_stille.wma");
		v�reStillePic.setNewPictogram(true);

		
		pictograms.add(badePic);
		pictograms.add(b�rsteT�nderPic); 
		pictograms.add(drikkePic); 
		pictograms.add(duPic); 
		pictograms.add(filmPic); 
		pictograms.add(forH�jtPic); 
		pictograms.add(g�Pic); 
		
		pictograms.add(jaPic); 
		pictograms.add(k�rePic); 
		pictograms.add(laveMadPic); 
		pictograms.add(legePic); 
		pictograms.add(migPic);
		pictograms.add(morgenRoutinePic); 
		pictograms.add(nejPic); 
		pictograms.add(sePic); 
		pictograms.add(sideNedPic); 
		
		pictograms.add(spilleComputerPic); 
		pictograms.add(stopPic); 
		pictograms.add(sultenPic); 
		pictograms.add(s�vnigPic); 
		pictograms.add(taleSammenPic);
		pictograms.add(t�rstigPic);
		pictograms.add(v�reStillePic);

		
	}

}
