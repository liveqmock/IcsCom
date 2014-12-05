package com.gdbocom.util.communication.custom.shaoguan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

/**
 * 福利彩票485404交易的报文配置类
 * @author qm
 *
 */
public class Sgd466676 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"ACTNO", "%-21s", FieldSource.VAR},
        		
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"ACTNAM","60",FieldTypes.STATIC},               
              
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
    	 Map request = new HashMap();
         //报文头字段
         request.put("TTxnCd", "466676");
         request.put("FeCod", "466676");

         request.put("ACTNO", "6222620710006559452");

         Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466676);
         	
         
         if("E".equals(responseMap.get("MsgTyp"))){
         	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
         }else{
         	
         	System.out.println(responseMap+"1111=========");

         	 System.out.println(responseMap.get("LotNam"));
         }
         

        
         /*System.out.println(responseMap.get("IdNo"));*/
    }
}
