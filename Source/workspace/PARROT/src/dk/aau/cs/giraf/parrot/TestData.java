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
		Pictogram børsteTænderPic = new Pictogram("Børste Tænder", "/sdcard/Pictogram/Børste_Tænder.png", null, "/sdcard/Pictogram/børste_tænder.wma");
		børsteTænderPic.setNewPictogram(true);
		Pictogram drikkePic = new Pictogram("Drikke", "/sdcard/Pictogram/Drikke.png", null, "/sdcard/Pictogram/drikke.wma");
		drikkePic.setNewPictogram(true);
		Pictogram duPic = new Pictogram("Du", "/sdcard/Pictogram/Du.png", null, "/sdcard/Pictogram/du.wma");
		duPic.setNewPictogram(true);
		Pictogram filmPic = new Pictogram("Film", "/sdcard/Pictogram/Film.png", null, "/sdcard/Pictogram/film.wma");
		filmPic.setNewPictogram(true);
		Pictogram forHøjtPic = new Pictogram("For Højt", "/sdcard/Pictogram/For_Højt.png", null, "/sdcard/Pictogram/for_højt.wma");
		forHøjtPic.setNewPictogram(true);
		Pictogram gåPic = new Pictogram("Gå", "/sdcard/Pictogram/Gå.png", null, "/sdcard/Pictogram/gå.wma");
		gåPic.setNewPictogram(true);
		Pictogram jaPic = new Pictogram("Ja", "/sdcard/Pictogram/Ja.png", null, "/sdcard/Pictogram/ja.wma");
		jaPic.setNewPictogram(true);
		Pictogram kørePic = new Pictogram("Køre", "/sdcard/Pictogram/Køre.png", null, "/sdcard/Pictogram/køre.wma");
		kørePic.setNewPictogram(true);
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
		Pictogram søvnigPic = new Pictogram("Søvnig", "/sdcard/Pictogram/Søvnig.png", null, "/sdcard/Pictogram/søvnig.wma");
		søvnigPic.setNewPictogram(true);
		Pictogram taleSammenPic = new Pictogram("Tale Sammen", "/sdcard/Pictogram/Tale_Sammen.png", null, "/sdcard/Pictogram/tale_sammen.wma");
		taleSammenPic.setNewPictogram(true);
		Pictogram tørstigPic = new Pictogram("Tørstig", "/sdcard/Pictogram/Tørstig.png", null, "/sdcard/Pictogram/tørstig.wma");
		tørstigPic.setNewPictogram(true);
		Pictogram væreStillePic = new Pictogram("Være Stille", "/sdcard/Pictogram/Være_Stille.png", null, "/sdcard/Pictogram/være_stille.wma");
		væreStillePic.setNewPictogram(true);

		
		pictograms.add(badePic);
		pictograms.add(børsteTænderPic); 
		pictograms.add(drikkePic); 
		pictograms.add(duPic); 
		pictograms.add(filmPic); 
		pictograms.add(forHøjtPic); 
		pictograms.add(gåPic); 
		
		pictograms.add(jaPic); 
		pictograms.add(kørePic); 
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
		pictograms.add(søvnigPic); 
		pictograms.add(taleSammenPic);
		pictograms.add(tørstigPic);
		pictograms.add(væreStillePic);

		
	}

}
