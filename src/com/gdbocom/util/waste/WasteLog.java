package com.gdbocom.util.waste;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * 由于区别于其他业务信息，因此另外创建一个位置保存通讯的报文日志。但是这种早就应该用
 * logger组件来解决，因此叫Waste。
 * @author qm
 *
 */
public class WasteLog
{
    private FileWriter fw;
    private String LogDirect = new String("");

    public WasteLog(String Direct)
    {
    	LogDirect = Direct;
    }

    /**
     * 记录日志
     * @param strLog
     */
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