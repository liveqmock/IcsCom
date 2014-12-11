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
 * 韶关代收付466603交易的报文配置类
 * @author qm
 *
 */
public class Sgd466603 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"CLI_IDENTITY_CARD",  "%-18s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
//        		<fixString name="CLI_CODE2" length="18" /><!-- 客户代码 -->
//        		<fixString name="CLI_NAME2" length="40" />                   <!-- 客户姓名 -->
//        		<fixString name="CLI_TYPE2" length="2" /> <!-- 客户类型 -->
                {"CLI_CODE2","18",FieldTypes.STATIC},               
                {"CLI_NAME2", "40", FieldTypes.STATIC},  //客户身份证
                {"CLI_TYPE2", "2", FieldTypes.STATIC},           //客户姓名
        };
        return Transation.unpacketsSequence(response, format);

    }
    public static void main(String[] args) throws UnknownHostException, IOException{
   	 Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "466603");
        request.put("FeCod", "466603");

        request.put("CLI_IDENTITY_CARD", "341222199309015999");
      
        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466603);
        
        
        if("E".equals(responseMap.get("MsgTyp"))){
        	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
        }else{
        	
        	System.out.println(responseMap+"1111=========");

        	 System.out.println(responseMap.get("LotNam"));
        }
        

       
        /*System.out.println(responseMap.get("IdNo"));*/

   }
}
