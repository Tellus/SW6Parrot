package dk.aau.cs.giraf.pictosearch;
import java.util.List;

import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;

public class DatabaseHandler {
	Helper helper;
	List<Media> arraylist; 
	
	public List<Media> getPictograms(String pictogramname) {
		arraylist.addAll(( helper.mediaHelper.getMediaByName(pictogramname)));
		
		return arraylist;
	}
}
