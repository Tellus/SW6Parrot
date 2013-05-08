package dk.aau.cs.giraf.pictosearch;

import dk.aau.cs.giraf.pictogram.Pictogram;

public class SearchNode {
	Pictogram pic;
	int searchvalue;
	
	public SearchNode(Pictogram p, int value){
		this.pic = p;
		this.searchvalue = value;
	}
}
