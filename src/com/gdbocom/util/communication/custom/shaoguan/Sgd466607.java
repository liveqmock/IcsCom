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
 * 韶关代收付466607交易的报文配置类
 * @author qm
 *
 */
public class Sgd466607 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"TXNCNL", "%-1s", FieldSource.VAR},
        		{"CLI_IDENTITY_CARD",  "%-18s", FieldSource.VAR},
        		{"FUNFLG",  "%-1s", FieldSource.VAR},
        		{"CLI_CODE",  "%-18s", FieldSource.VAR},
        		{"CLI_NAME",  "%-40s", FieldSource.VAR},
        		{"BUSCLA",  "%-2s", FieldSource.VAR},
        		{"AREANUM",  "%-2s", FieldSource.VAR},
        		{"CLI_UNIT_CODE",  "%-6s", FieldSource.VAR},
        		{"CLI_TRADE_FLAG",  "%-2s", FieldSource.VAR},
        		{"PZFLG",  "%-3s", "007"},
        		{"PZNO",  "%-20s", ""},
        		{"CLI_BANK_ACCOUNT",  "%-40s", FieldSource.VAR},
        		{"PIN",  "%-20s", FieldSource.VAR},
        		{"CLI_BANK_ACCNAM",  "%-60s", FieldSource.VAR},
        		{"CLI_TRADE_IDENT",  "%-15s", FieldSource.VAR},
        		{"CLI_CODE_STATUS",  "%-6s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"CLI_IDENTITY_CARD", "18", FieldTypes.STATIC},  //客户身份证
                {"FUNFLG", "1", FieldTypes.STATIC},           //客户姓名
                {"CLI_CODE", "18", FieldTypes.STATIC},            //性别
                {"CLI_NAME","40", FieldTypes.STATIC},              //年龄
                {"BUSCLA", "2", FieldTypes.STATIC},           //客户级别
                {"AREANUM", "2", FieldTypes.STATIC},          //客户状态
                {"CLI_BANK_CODE", "6", FieldTypes.STATIC},   //家庭住址
                {"CLI_UNIT_CODE","6",FieldTypes.STATIC},   //家庭电话
                {"CLI_TRADE_FLAG", "2", FieldTypes.STATIC},       //家庭邮编
                {"ACTTYP", "3", FieldTypes.STATIC},          //电子信箱
                {"PZNO", "20", FieldTypes.STATIC},       //主页
                {"CLI_BANK_ACCOUNT", "40", FieldTypes.STATIC},       //主页
                {"PIN","20",FieldTypes.STATIC},        //单位名称
                {"CLI_BANK_ACCNAM", "60", FieldTypes.STATIC},    //单位地址
                {"CLI_TRADE_IDENT", "15", FieldTypes.STATIC}, //单位电话
                {"CLI_CODE_STATUS", "6", FieldTypes.STATIC},       //单位邮编
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
    	 Map request = new HashMap();
         //报文头字段
         request.put("TTxnCd", "466607");
         request.put("FeCod", "466607");

//         {"CLI_IDENTITY_CARD",  "%-18s", FieldSource.VAR},
// 		{"FUNFLG",  "%-1s", FieldSource.VAR},
// 		{"CLI_CODE",  "%-18s", FieldSource.VAR},
// 		{"CLI_NAME",  "%-40s", FieldSource.VAR},
// 		{"BUSCLA",  "%-2s", FieldSource.VAR},
// 		{"AREANUM",  "%-2s", FieldSource.VAR},
// 		{"CLI_UNIT_CODE",  "%-6s", FieldSource.VAR},
// 		{"CLI_TRADE_FLAG",  "%-2s", FieldSource.VAR},
// 		{"PZFLG",  "%-3s", FieldSource.VAR},
// 		{"PZNO",  "%-20s", FieldSource.VAR},
// 		{"CLI_BANK_ACCOUNT",  "%-40s", FieldSource.VAR},
// 		{"PIN",  "%-20s", FieldSource.VAR},
// 		{"CLI_BANK_ACCNAM",  "%-60s", FieldSource.VAR},
// 		{"CLI_TRADE_IDENT",  "%-15s", FieldSource.VAR},
// 		{"CLI_CODE_STATUS",  "%-6s", FieldSource.VAR},
         request.put("CLI_IDENTITY_CARD", "341222199309015999");
         request.put("FUNFLG", "2");
         request.put("CLI_CODE", "100000201410200012");
         request.put("CLI_NAME", "renhao");
         request.put("BUSCLA", "1");
         request.put("AREANUM", "24");
         request.put("CLI_UNIT_CODE", "24");
         request.put("CLI_TRADE_FLAG", "24");
         request.put("PZFLG", "24");
         request.put("PZNO", "24");
         request.put("CLI_BANK_ACCOUNT", "6222620710003025622");
         request.put("PIN", "7EB3FE2C1A278F18");
         request.put("CLI_BANK_ACCNAM", "BANKCOMM");
         Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466607);
         
         
         if("E".equals(responseMap.get("MsgTyp"))){
         	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
         }else{
         	
         	System.out.println(responseMap+"1111=========");

         	 System.out.println(responseMap.get("LotNam"));
         }
         

        
         /*System.out.println(responseMap.get("IdNo"));*/

    }
}
