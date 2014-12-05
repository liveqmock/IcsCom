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
public class Sgd466612 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"TXNCNL", "%-1s", "6"},
        		{"PAYTYPE",  "%-2s", FieldSource.VAR},
        		{"LOGNO",  "%-14s", FieldSource.VAR},
        		{"PAYFLG",  "%-18s", FieldSource.VAR},
        		{"PAYACT",  "%-21s", FieldSource.VAR},
        		{"PAYNAM",  "%-40s", FieldSource.VAR},
        		{"PINBLK",  "%-20s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TCKNO","11",FieldTypes.STATIC},       //主机流水        
                {"BUSTYPE", "2", FieldTypes.STATIC},  //交易类型
                {"CLI_CODE", "18", FieldTypes.STATIC},           //客户代码
                {"PAYBNK", "6", FieldTypes.STATIC},            //付款银行
                {"COMPNO","6", FieldTypes.STATIC},              //收费企业
                {"PAYACT", "40", FieldTypes.STATIC},           //帐号
                {"PAYFLG", "40", FieldTypes.STATIC},          //交费标示
                {"PAYNAM", "40", FieldTypes.STATIC},   //帐户户名
                {"TXNAMT","15",FieldTypes.STATIC},   //金额
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "466612");
        request.put("FeCod", "466612");
//        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("PAYTYPE", "02");
        request.put("LOGNO", "14120275100017");
        request.put("PAYFLG", "16253736");
        request.put("PAYACT", "6222600710002414219");
        request.put("PAYNAM", "BANKCOMM");
        request.put("PINBLK", "7EB3FE2C1A278F18");
        Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466612);
        
        
        if("E".equals(responseMap.get("MsgTyp"))){
        	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
        }else{
        	
        	System.out.println(responseMap+"1111=========");
        	 System.out.println(responseMap.get("LotNam"));
        }

    }
}
