package com.gdbocom.util.waste;

import java.io.*;
import java.util.*;
import java.text.*;

public class WasteLog
{
    private FileWriter fw;
    private String LogDirect = new String("");
    
    public WasteLog(String Direct)
    {
    	LogDirect = Direct;
    }

    public void Write(String strLog)
    {
    	Date current = new Date();
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd"); 
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss"); 
        String current_date = sdf_date.format(current);
        String current_time = sdf_time.format(current);
        try
        {
	    if(!(new File(LogDirect).isDirectory()))
            	new File(LogDirect).mkdir();
            fw = new FileWriter(LogDirect+"/"+current_date+".log.waste",true);
            fw.write(current_time+"	"+strLog+"\n");
            fw.write("------------------------------------------------------------------\n");
            fw.close();
	}
       	catch(IOException e)
      	{
             //setErrorMessage("Error writting the file: "+e.getMessage());
             return;
      	}
    }
}