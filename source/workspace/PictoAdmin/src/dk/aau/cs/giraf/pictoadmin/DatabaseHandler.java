package dk.aau.cs.giraf.pictoadmin;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;

public class DatabaseHandler {
	Helper helper;
	List<Media> arraylist; 
	DisplayPictograms displaypicto;
	
	// Lol
	public List<Media> getPictograms(String pictogramname) {
		arraylist.addAll(( helper.mediaHelper.getMediaByName(pictogramname)));
		
		return arraylist;
	}
}
