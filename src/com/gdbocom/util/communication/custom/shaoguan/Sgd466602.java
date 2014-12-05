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
public class Sgd466602 extends Transation {
	
	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"TXNCNL", "%-1s", "6"},
        		{"CLI_IDENTITY_CARD",  "%-18s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"DATATMP","4",FieldTypes.STATIC},               
                {"CLI_IDENTITY_CARD", "18", FieldTypes.STATIC},  //客户身份证
                {"CLI_NAME", "40", FieldTypes.STATIC},           //客户姓名
                {"CLI_SEX", "1", FieldTypes.STATIC},            //性别
                {"CLI_AGE","3", FieldTypes.STATIC},              //年龄
                {"CLI_LEVEL", "1", FieldTypes.STATIC},           //客户级别
                {"CLI_STATUS", "1", FieldTypes.STATIC},          //客户状态
                {"CLI_HOME_ADDRESS", "40", FieldTypes.STATIC},   //家庭住址
                {"CLI_HOME_TELEPHONE","40",FieldTypes.STATIC},   //家庭电话
                {"CLI_HOME_POST", "6", FieldTypes.STATIC},       //家庭邮编
                {"CLI_EMAIL", "40", FieldTypes.STATIC},          //电子信箱
                {"CLI_HOMEPAGE", "40", FieldTypes.STATIC},       //主页
                {"CLI_UNIT_NAME","40",FieldTypes.STATIC},        //单位名称
                {"CLI_UNIT_ADRESS", "40", FieldTypes.STATIC},    //单位地址
                {"CLI_UNIT_TELEPHONE", "40", FieldTypes.STATIC}, //单位电话
                {"CLI_UNIT_POST", "6", FieldTypes.STATIC},       //单位邮编
                {"CLI_MARROW_UNIT_NAME","40",FieldTypes.STATIC}, //配偶姓名
                {"CLI_MARROW_NAME", "40", FieldTypes.STATIC},    //配偶单位 名称
                {"CLI_MARROW_UNIT_ADDRESS", "40", FieldTypes.STATIC},//配偶地址
                {"CLI_MARROW_UNIT_TELEPHONE", "40", FieldTypes.STATIC},//配偶电话
                {"CLI_MARROW_UNIT_POST", "6", FieldTypes.STATIC},    //配偶邮编
                {"CLI_MENO", "254", FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
    	 Map request = new HashMap();
         //报文头字段
         request.put("TTxnCd", "466602");
         request.put("FeCod", "466602");
//         request.put("TxnSrc", "MB441");
         //报文体字段
         request.put("CLI_IDENTITY_CARD", "3333444456666");
         Map responseMap = Transation.exchangeData(IcsServer.getServer("@SGD_A"),request,TransationFactory.SGD466602);
         
         
         if("E".equals(responseMap.get("MsgTyp"))){
         	 System.out.println(responseMap+"=========="+responseMap.get("RspMsg"));
         }else{
         	
         	System.out.println(responseMap+"1111=========");

         	 System.out.println(responseMap.get("LotNam"));
         }
         

        
         /*System.out.println(responseMap.get("IdNo"));*/

    }
}
