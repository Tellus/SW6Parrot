package dk.aau.cs.giraf.pictoadmin;

import java.util.List;

import dk.aau.cs.giraf.oasis.lib.models.Media;

import android.app.Activity;
import android.widget.GridView;


public class DisplayPictograms extends Activity {
	List<Media> pictograms;
	GridView pictodisplay;
	PictoAdapter pictoadapt;
	
	// How do we display pictograms?
	public void updatePictogramDisplayer(List<Media> pictograms) {
		pictodisplay = (GridView) findViewById(R.id.pictogram_displayer);
		//pictodisplay.setAdapter((ListAdapter) new PictoAdapter(this, PICTO_DISP));
	}
}
