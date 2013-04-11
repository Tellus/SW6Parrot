package dk.aau.cs.giraf.categorylib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.pictogram.AudioPlayer;
import dk.aau.cs.giraf.pictogram.PictoFactory;
import dk.aau.cs.giraf.pictogram.Pictogram;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class XMLCommunicater {
	private File categoryXmlData=null;
	public static boolean isNewFile = false;
	
	ArrayList<XMLProfile> xmlData= new ArrayList<XMLProfile>();
	
	public XMLCommunicater( )
	{
		boolean mExternalStorageAvailableAndWriteable = false;
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
			mExternalStorageAvailableAndWriteable= true;
		    categoryXmlData = new File(Environment.getExternalStorageDirectory().getPath() +"/Pictures/giraf/categoryData.xml");
					
		    if(!categoryXmlData.exists())
			{
			    try
			    {
			    	Log.v("MessagePARROT","no xml exist, creating xml");
			    	categoryXmlData.createNewFile();
			    	isNewFile=true;
			    	
			    	
			    }
			    catch(IOException e)
			    {
			        Log.e("IOException", "Exception in create new File(");
			    }
			}
		    else
		    {
		      	getDataFromXML();
	         
		    }

		}  
	}
	
	public ArrayList<XMLProfile> getXMLData()
	{
		//temp remove after test
		
		return xmlData;
	}
	public void setXMLDataAndUpdate(ArrayList<XMLProfile> newXMLData)
	{
		xmlData= newXMLData;
		
		new Thread(new Runnable(){
            public void run(){
            	insertInToXML();
            }
        }).start();
		
		
		
	}
	
	public XmlSerializer insertCommonAtributes(XMLCategoryProfile category, XmlSerializer serializer)
	{
		try{
			serializer.attribute(null, XMLProfile.NAME, category.getName());
			serializer.attribute(null, XMLProfile.COLOR, Integer.toString(category.getColor()));
			serializer.attribute(null, XMLProfile.ICON, Long.toString(category.getIconID()));
			
			for(Long pictogramId : category.getPictogramsID())
			{
				Log.v("PARROTMESSAGE",XMLProfile.PICTOGRAM);
				serializer.startTag(null, XMLProfile.PICTOGRAM);
				serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
				serializer.endTag(null, XMLProfile.PICTOGRAM);
				Log.v("PARROTMESSAGE","Pictogram end");
			}
		}catch(Exception e){}
		return serializer;
	}
	
	private void insertInToXML()
	{
		
		FileOutputStream fileos = null;       	
        try{
        	fileos = new FileOutputStream(categoryXmlData);
        }catch(FileNotFoundException e){
        	Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        XmlSerializer serializer = Xml.newSerializer();

        try {
        	//we set the FileOutputStream as output for the serializer, using UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			//Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null) 
			serializer.startDocument(null, Boolean.valueOf(true)); 
			//start a tag called "root"
			
			for(XMLProfile prof : xmlData)
			{
				serializer.startTag(null, XMLProfile.CHILDID);
				Log.v("PARROTMESSAGE",XMLProfile.CHILDID);
				serializer.attribute(null, XMLProfile.ID, Long.toString(prof.getChildID()));
				//i indent code just to have a view similar to xml-tree
				for( XMLCategoryProfile category : prof.getCategories())
				{
					serializer.startTag(null, XMLProfile.CATEGORY);
					Log.v("PARROTMESSAGE",XMLProfile.CATEGORY);
					
						serializer.attribute(null, XMLProfile.NAME, category.getName());
						serializer.attribute(null, XMLProfile.COLOR, Integer.toString(category.getColor()));
						serializer.attribute(null, XMLProfile.ICON, Long.toString(category.getIconID()));
						
						for(XMLCategoryProfile c : category.getSubcategories())
						{
							Log.v("PARROTMESSAGE",XMLProfile.SUBCATEGORY);
							serializer.startTag(null, XMLProfile.SUBCATEGORY);
							serializer.attribute(null, XMLProfile.NAME, c.getName());
							serializer.attribute(null, XMLProfile.COLOR, Integer.toString(c.getColor()));
							serializer.attribute(null, XMLProfile.ICON, Long.toString(c.getIconID()));
							
							for(Long pictogramId : c.getPictogramsID())
							{
								Log.v("PARROTMESSAGE",XMLProfile.PICTOGRAM);
								serializer.startTag(null, XMLProfile.PICTOGRAM);
								serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
								serializer.endTag(null, XMLProfile.PICTOGRAM);
								Log.v("PARROTMESSAGE","Pictogram end");
							}
							serializer.endTag(null, XMLProfile.SUBCATEGORY);
							Log.v("PARROTMESSAGE","SubCategory end");
						}
						for(Long pictogramId : category.getPictogramsID())
						{
							Log.v("PARROTMESSAGE",XMLProfile.PICTOGRAM);
							serializer.startTag(null, XMLProfile.PICTOGRAM);
							serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
							serializer.endTag(null, XMLProfile.PICTOGRAM);
							Log.v("PARROTMESSAGE","Pictogram end");
						}
						
					serializer.endTag(null, XMLProfile.CATEGORY);
					Log.v("PARROTMESSAGE","category end");
				}
					
				serializer.endTag(null, XMLProfile.CHILDID);
			}
			
			
			serializer.endDocument();
			Log.v("PARROTMESSAGE","end doc");
			//write xml data into the FileOutputStream
			serializer.flush();
			Log.v("PARROTMESSAGE","end flush");
			//finally we close the file stream
			fileos.close();
			Log.v("PARROTMESSAGE","writting to xml");
			
	       
		} catch (Exception e) {
			Log.v("PARROTMESSAGE","error occurred while creating xml file");
		}
	}

	private void getDataFromXML()
	{
		Log.v("XMLTESTER", "begin getDataFromXML");
		XMLProfile profile = null;
		XMLCategoryProfile superCategory=null;
		XMLCategoryProfile subCategory=null;
		ArrayList<XMLCategoryProfile> categoryList = null;
		String inSuperOrSubcategory = XMLProfile.CATEGORY;
		//Log.v("XMLTESTER", "begin FileInputStream");
	    InputStream is;
		try {
			is = new FileInputStream(categoryXmlData);
		} catch (FileNotFoundException e) {
			Log.v("MessageParrot","reading from file with exceptions");
			return;
		}
		//Log.v("XMLTESTER", "end FileInputStream");
		try {
            // get a new XmlPullParser object from Factory
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            // set input source
            parser.setInput(is, null);
            // get event type
            int eventType = parser.getEventType();
           // Log.v("XMLTESTER", "parser get event type: "+ eventType);
            // process tag while not reaching the end of document
            while(eventType != XmlPullParser.END_DOCUMENT) {
            	
                switch(eventType) {
                    // at start of document: START_DOCUMENT
                    case XmlPullParser.START_DOCUMENT:
                    	//Log.v("XMLTESTER", "start dok");
                        break;
 
                    // at start of a tag: START_TAG
                    case XmlPullParser.START_TAG:
                    	Log.v("XMLTESTER", "start tag");
                        // get tag name
                        String tagName = parser.getName();
                        //Log.v("XMLTESTER", "tag= " + tagName);
                        // if <ChildId>, get attribute: 'id'
                        if(tagName.equalsIgnoreCase(profile.CHILDID)) {
                        	//Log.v("XMLTESTER","starttag childid");
                        	
                        	
                        	profile = new XMLProfile();
                        	categoryList = new ArrayList<XMLCategoryProfile>();
                            profile.setChildID( Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID)));
                        }
                        // if <Category>
                        else if(tagName.equalsIgnoreCase(XMLProfile.CATEGORY)) {
                        	//Log.v("XMLTESTER","starttag category");
                        	int color= Integer.parseInt(parser.getAttributeValue(null, XMLProfile.COLOR));
                        	String name= parser.getAttributeValue(null, XMLProfile.ICON);
                        	Long iconid= Long.parseLong(parser.getAttributeValue(null, XMLProfile.ICON));
                        	//Log.v("XMLTESTER","_Color: "+ color +" _icon: "+iconid+" _name; "+ name);
                            
                        	superCategory= new XMLCategoryProfile();
                        	superCategory.setColor(color);
                        	superCategory.setIconID(iconid);
                        	superCategory.setName(name);
                            //profile.mContent = parser.nextText();
                        	
                        }
                        // if <SubCategory>
                        else if(tagName.equalsIgnoreCase(XMLProfile.SUBCATEGORY)) {
                        	//Log.v("XMLTESTER","starttag subcate");
                        	inSuperOrSubcategory = XMLProfile.SUBCATEGORY;
                        	subCategory= new XMLCategoryProfile();
                        	subCategory.setColor(Integer.parseInt(parser.getAttributeValue(null, XMLProfile.COLOR)));
                        	subCategory.setIconID(Long.parseLong(parser.getAttributeValue(null, XMLProfile.ICON)));
                        	subCategory.setName(parser.getAttributeValue(null, XMLProfile.ICON));
                        	
                        	//Log.v("XMLTESTER","_Color: "+ subCategory.getColor()+" _icon: "+subCategory.getIconID()+" _name; "+ subCategory.getName());
                           
                        }
                        // if <Pictogram>
                        else if(tagName.equalsIgnoreCase(XMLProfile.PICTOGRAM)) {
                        	//Log.v("XMLTESTER","starttag picto");
                        	if(inSuperOrSubcategory.equalsIgnoreCase(XMLProfile.SUBCATEGORY))
                        	{
                        		//Log.v("XMLTESTER","starttag picto is sub");
                        		Long picId = Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID));
                        		
                        		subCategory.addPictogramId(picId);
                        		//Log.v("XMLTESTER","starttag picto is sub end");
                        	}
                        	else
                        	{
                        		//Log.v("XMLTESTER","starttag picto is cate");
                        		superCategory.addPictogramId(Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID)));
                        	}
                           
                        	//Log.v("XMLTESTER"," ending starttag picto");
                        }
                        break;

                    case XmlPullParser.END_TAG:
                    	String tagName2 = parser.getName();
                    	//Log.v("XMLTESTER", "start tag " + tagName2);
                    	// if <ChildId>, get attribute: 'id'
                    	if(tagName2.equalsIgnoreCase(profile.CHILDID)) 
                    	{
                    		//Log.v("XMLTESTER","endtag childid");
                    		profile.setCategories(categoryList);
                    		xmlData.add(profile);
                    	}
                    	// if <Category>
                        else if(tagName2.equalsIgnoreCase(XMLProfile.CATEGORY)) {
                        	//Log.v("XMLTESTER","endtag catego");
                        	categoryList.add(superCategory);
                        }
                        // if <SubCategory>
                        else if(tagName2.equalsIgnoreCase(XMLProfile.SUBCATEGORY)) {
                        	//Log.v("XMLTESTER","endtag subcatego");
                        	superCategory.addSubcategory(subCategory);
                        	inSuperOrSubcategory = XMLProfile.CATEGORY;
                        }
                    default:
                    	break;
                    	
                }
                // jump to next event
                eventType = parser.next();
            }
        // exception stuffs
        } catch (XmlPullParserException e) {
            profile = null;
            Log.v("XMLTESTER", "XmlPullParserException");
        } catch (IOException e) {
            profile = null;
            Log.v("XMLTESTER", "IOException");
        }
	
		Log.v("XMLTESTER", "end getDataFromXML");
		
		
    }
	
}
