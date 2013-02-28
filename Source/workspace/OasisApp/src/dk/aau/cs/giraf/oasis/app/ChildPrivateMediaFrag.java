package dk.aau.cs.giraf.oasis.app;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import dk.aau.cs.giraf.oasis.lib.Helper;
import dk.aau.cs.giraf.oasis.lib.models.Media;

/**
 * Fragment which is used for showing the childs profile information
 * 
 * @author Oasis
 *
 */
public class ChildPrivateMediaFrag extends ListFragment {

	Helper helper;
	List<Media> list;
	Media media;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.media_view, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		helper = new Helper(getActivity().getApplicationContext());

		if (MainActivity.child != null) {
			list = helper.mediaHelper.getMediaIOwn(MainActivity.child);
			setListAdapter(new MediaListAdapter(getActivity().getApplicationContext(), list));
		} else {
			setListAdapter(null);
		}

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v,int position, long id) 
			{
				media = (Media) getListAdapter().getItem(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Billede");
				ImageView i = new ImageView(getActivity());
				Bitmap bm = BitmapFactory.decodeFile(media.getMPath());
				i.setImageBitmap(bm);
				//				i.setImageResource(R.drawable.no_profile_pic);
				builder.setView(i);
				builder.setNegativeButton("Cancel", null);
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
				media = (Media) getListAdapter().getItem(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Slet Billede");
				builder.setMessage("Sikker på at du vil slette " + media.getName() + "?");
				builder.setPositiveButton("Ja", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						helper.mediaHelper.removeMediaAttachmentToProfile(media, MainActivity.child);
						list = helper.mediaHelper.getMediaIOwn(MainActivity.child);
						setListAdapter(new MediaListAdapter(getActivity().getApplicationContext(), list));
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("Nej", null);
				AlertDialog alert = builder.create();
				alert.show();

				return false;
			}
		});
	}

}
