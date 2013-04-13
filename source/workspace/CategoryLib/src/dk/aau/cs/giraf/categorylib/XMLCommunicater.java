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

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class XMLCommunicater {
	private File categoryXmlData=null;
	public static boolean isNewFile = false;
	
	public static ArrayList<XMLProfile> xmlData= new ArrayList<XMLProfile>();
	
	public XMLCommunicater( )
	{
		//Log.v("MessageXML", "begin XMLCommunicater");
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    categoryXmlData = new File(Environment.getExternalStorageDirectory().getPath() +"/Pictures/giraf/categoryData.xml");
					
		    if(!categoryXmlData.exists())
			{
			    try
			    {
			    	//Log.v("MessagePARROT","no xml exist, creating xml");
			    	categoryXmlData.createNewFile();
			    	isNewFile=true;
			    	
			    	
			    }
			    catch(IOException e)
			    {
			        //Log.e("IOException", "Exception in create new File(");
			    }
			}
		    else
		    {
		      	getDataFromXML();
	         
		    }

		}  
		//Log.v("MessageXML", "end XMLCommunicater");
	}
	
	public XMLProfile getChildXMLData(long childId)
	{
		//Log.v("MessageXML", "begin getChildXMLData");
		//temp remove after test
		for(XMLProfile profile: xmlData)
		{
			if(profile.getChildID()==childId)
			{
				return profile;
			}
		}		
		
		//Log.v("MessageXML", "xmlChild does not exist return null");
		//Log.v("MessageXML", "end getChildXMLData");
		return null;
		
	}
	
	public void setXMLDataAndUpdate(XMLProfile xmlChild)//ArrayList<XMLProfile> newXMLData)
	{
		//Log.v("XMLComMessage", "begin setXMLDataAndUpdate");
		if(xmlChild==null){}
		else if(!xmlData.isEmpty())
		{
			for(XMLProfile profile: xmlData)
			{
				if(profile.getChildID()== xmlChild.getChildID())
				{
					if(xmlChild.getCategories().isEmpty())
					{
						xmlData.remove(profile);
					} 
					else
					{
						xmlData.remove(profile);
						xmlData.add(xmlChild);
					}
				}
				else
				{
					xmlData.add(xmlChild);
				}
			}
		}
		else
		{
			xmlData.add(xmlChild);
		}
		
		new Thread(new Runnable(){
            public void run(){
            	insertInToXML();
            }
        }).start();
		
		
		//Log.v("XMLComMessage", "end setXMLDataAndUpdate");
	}
	
	public XmlSerializer insertCommonAtributes(XMLCategoryProfile category, XmlSerializer serializer)
	{
		//Log.v("XMLComMessage", "begin insertCommonAtributes");
		try{
			serializer.attribute(null, XMLProfile.NAME, category.getName());
			serializer.attribute(null, XMLProfile.COLOR, Integer.toString(category.getColor()));
			serializer.attribute(null, XMLProfile.ICON, Long.toString(category.getIconID()));
			
			for(Long pictogramId : category.getPictogramsID())
			{
				serializer.startTag(null, XMLProfile.PICTOGRAM);
				serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
				serializer.endTag(null, XMLProfile.PICTOGRAM);
			}
		}catch(Exception e){}
		//Log.v("XMLComMessage", "end insertCommonAtributes");
		return serializer;
	}
	
	private void insertInToXML()
	{
		//Log.v("XMLComMessage", "begin insertInToXML");
		FileOutputStream fileos = null;       	
        try{
        	fileos = new FileOutputStream(categoryXmlData);
        }catch(FileNotFoundException e){
        	//Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        XmlSerializer serializer = Xml.newSerializer();

        try {
        	//we set the FileOutputStream as output for the serializer, using UTF-8 encoding
			serializer.setOutput(fileos, "UTF-8");
			//Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null) 
			serializer.startDocument(null, Boolean.valueOf(true)); 
			
			for(XMLProfile prof : xmlData)
			{
				serializer.startTag(null, XMLProfile.CHILDID);
				serializer.attribute(null, XMLProfile.ID, Long.toString(prof.getChildID()));
				//i indent code just to have a view similar to xml-tree
				for( XMLCategoryProfile category : prof.getCategories())
				{
					serializer.startTag(null, XMLProfile.CATEGORY);
					
						serializer.attribute(null, XMLProfile.NAME, category.getName());
						serializer.attribute(null, XMLProfile.COLOR, Integer.toString(category.getColor()));
						serializer.attribute(null, XMLProfile.ICON, Long.toString(category.getIconID()));
						
						for(XMLCategoryProfile c : category.getSubcategories())
						{
							serializer.startTag(null, XMLProfile.SUBCATEGORY);
							serializer.attribute(null, XMLProfile.NAME, c.getName());
							serializer.attribute(null, XMLProfile.COLOR, Integer.toString(c.getColor()));
							serializer.attribute(null, XMLProfile.ICON, Long.toString(c.getIconID()));
							
							for(Long pictogramId : c.getPictogramsID())
							{
								serializer.startTag(null, XMLProfile.PICTOGRAM);
								serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
								serializer.endTag(null, XMLProfile.PICTOGRAM);
							}
							serializer.endTag(null, XMLProfile.SUBCATEGORY);
						}
						for(Long pictogramId : category.getPictogramsID())
						{
							//Log.v("PARROTMESSAGE",XMLProfile.PICTOGRAM);
							serializer.startTag(null, XMLProfile.PICTOGRAM);
							serializer.attribute(null, XMLProfile.ID, Long.toString(pictogramId));
							serializer.endTag(null, XMLProfile.PICTOGRAM);
							//Log.v("PARROTMESSAGE","Pictogram end");
						}
						
					serializer.endTag(null, XMLProfile.CATEGORY);
				}
					
				serializer.endTag(null, XMLProfile.CHILDID);
			}
			
			
			serializer.endDocument();
			//write xml data into the FileOutputStream
			serializer.flush();
			//finally we close the file stream
			fileos.close();
			
	       
		} catch (Exception e) {
			//Log.v("PARROTMESSAGE","error occurred while creating xml file");
		}
        //Log.v("XMLComMessage", "end insertInToXML");
	}

	private void getDataFromXML()
	{
		//Log.v("XMLComMessage", "begin getDataFromXML");
		XMLProfile profile = null;
		XMLCategoryProfile superCategory=null;
		XMLCategoryProfile subCategory=null;
		ArrayList<XMLCategoryProfile> categoryList = null;
		String inSuperOrSubcategory = XMLProfile.CATEGORY;
		
		InputStream is;
		try {
			is = new FileInputStream(categoryXmlData);
		} catch (FileNotFoundException e) {
			//Log.v("MessageParrot","reading from file with exceptions");
			return;
		}
		try {
            // get a new XmlPullParser object from Factory
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            // set input source
            parser.setInput(is, null);
            // get event type
            int eventType = parser.getEventType();
            // process tag while not reaching the end of document
            while(eventType != XmlPullParser.END_DOCUMENT) {
            	
                switch(eventType) {
                    // at start of document: START_DOCUMENT
                    case XmlPullParser.START_DOCUMENT:
                        break;
 
                    // at start of a tag: START_TAG
                    case XmlPullParser.START_TAG:
                        // get tag name
                        String tagName = parser.getName();
                        // if <ChildId>, get attribute: 'id'
                        if(tagName.equalsIgnoreCase(XMLProfile.CHILDID)) {
                        	profile = new XMLProfile();
                        	categoryList = new ArrayList<XMLCategoryProfile>();
                            profile.setChildID( Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID)));
                        }
                        // if <Category>
                        else if(tagName.equalsIgnoreCase(XMLProfile.CATEGORY)) {
                        	int color= Integer.parseInt(parser.getAttributeValue(null, XMLProfile.COLOR));
                        	String name= parser.getAttributeValue(null, XMLProfile.ICON);
                        	Long iconid= Long.parseLong(parser.getAttributeValue(null, XMLProfile.ICON));                    
                        	
                        	superCategory= new XMLCategoryProfile();
                        	superCategory.setColor(color);
                        	superCategory.setIconID(iconid);
                        	superCategory.setName(name);
                        }
                        // if <SubCategory>
                        else if(tagName.equalsIgnoreCase(XMLProfile.SUBCATEGORY)) {
                        	inSuperOrSubcategory = XMLProfile.SUBCATEGORY;
                        	subCategory= new XMLCategoryProfile();
                        	subCategory.setColor(Integer.parseInt(parser.getAttributeValue(null, XMLProfile.COLOR)));
                        	subCategory.setIconID(Long.parseLong(parser.getAttributeValue(null, XMLProfile.ICON)));
                        	subCategory.setName(parser.getAttributeValue(null, XMLProfile.ICON));
                        }
                        // if <Pictogram>
                        else if(tagName.equalsIgnoreCase(XMLProfile.PICTOGRAM)) {
                        	if(inSuperOrSubcategory.equalsIgnoreCase(XMLProfile.SUBCATEGORY))
                        	{
                        		Long picId = Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID));
                        		
                        		subCategory.addPictogramId(picId);
                        	}
                        	else
                        	{
                        		superCategory.addPictogramId(Long.parseLong(parser.getAttributeValue(null, XMLProfile.ID)));
                        	}       
                        }
                        break;

                    case XmlPullParser.END_TAG:
                    	String tagName2 = parser.getName();
                    	// if <ChildId>, get attribute: 'id'
                    	if(tagName2.equalsIgnoreCase(XMLProfile.CHILDID)) 
                    	{
                    		profile.setCategories(categoryList);
                    		xmlData.add(profile);
                    	}
                    	// if <Category>
                        else if(tagName2.equalsIgnoreCase(XMLProfile.CATEGORY)) {
                        	categoryList.add(superCategory);
                        }
                        // if <SubCategory>
                        else if(tagName2.equalsIgnoreCase(XMLProfile.SUBCATEGORY)) {
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
            //Log.v("XMLComMessage", "XmlPullParserException");
        } catch (IOException e) {
            profile = null;
            //Log.v("XMLComMessage", "IOException");
        }
	
		//Log.v("XMLComMessage", "end getDataFromXML");
		
		
    }
	
}
