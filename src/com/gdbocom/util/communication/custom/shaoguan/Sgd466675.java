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
 * 韶关代收付466675交易的报文配置类
 * @author qm
 *
 */
public class Sgd466675 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"CLI_UNIT_CODE", "%-6s", FieldSource.VAR},
        		{"CLI_TRADE_FLAG",  "%-2s", FieldSource.VAR},
        		{"CLI_TRADE_IDENT",  "%-15s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"CLI_CODE","18",FieldTypes.STATIC},               
                {"CLI_NAME", "40", FieldTypes.STATIC},         
                {"BUSCLA", "2", FieldTypes.STATIC},           
                {"AREACOD","2", FieldTypes.STATIC},             
                {"CLI_UNIT_CODE", "6", FieldTypes.STATIC},           
                {"CLI_TRADE_FLAG", "2", FieldTypes.STATIC},          
                {"PZFLG", "3", FieldTypes.STATIC},   
                {"CLI_BANK_ACCOUNT","40",FieldTypes.STATIC},   //家庭电话
                {"CLI_BANK_ACCNAM", "60", FieldTypes.STATIC},       //家庭邮编
                {"CLI_TRADE_IDENT", "15", FieldTypes.STATIC},          //电子信箱
                {"CLI_CODE_STATUS", "6", FieldTypes.STATIC},       //主页
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "466675");
        request.put("FeCod", "466675");
        //报文体字段
        request.put("CLI_UNIT_CODE", "800000");
        request.put("CLI_TRADE_FLAG", "68");
        request.put("CLI_TRADE_IDENT", "123456789");
        

        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466676);
        	
        
        if("E".equals(responseMap.get("MsgTyp"))){
        	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
        }else{
        	
        	System.out.println(responseMap+"1111=========");

        	 System.out.println(responseMap.get("LotNam"));
        }
    }
}
