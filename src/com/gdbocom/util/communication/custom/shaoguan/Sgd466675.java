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
                {"CLI_NAME", "40", FieldTypes.STATIC},           //客户姓名
                {"BUSCLA", "2", FieldTypes.STATIC},            //性别
                {"AREACOD","2", FieldTypes.STATIC},              //年龄
                {"CLI_UNIT_CODE", "6", FieldTypes.STATIC},           //客户级别
                {"CLI_TRADE_FLAG", "2", FieldTypes.STATIC},          //客户状态
                {"PZFLG", "3", FieldTypes.STATIC},   //家庭住址
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
        request.put("TTxnCd", "485404");
        request.put("FeCod", "485404");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("CusNam", "BANKCOMM");
        request.put("CrdNo", "6222600710007815865");
        request.put("ActNo", "6222600710007815865");
        request.put("IdTyp", "15");
        request.put("IdNo", "44010419850301501X");
        request.put("MobTel", "13570959854");
        request.put("FixTel", "");
        request.put("Email", "");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@WEL_A"),
                request,
                TransationFactory.WEL485404);
        System.out.println(responseMap.get("LotNam"));
        /*System.out.println(responseMap.get("IdNo"));*/

    }
}
