package com.gdbocom.util.communication.custom.efek;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

/**
 * 南方电网460443交易的报文配置类
 * @author qm
 *
 */
public class Efek460430 extends Transation {

	protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        Object[][] format = {
        		{"Func", "%-1s", FieldSource.VAR},
        		{"DWBM", "%-8s", FieldSource.VAR},
        		{"JFH", "%-20s", FieldSource.VAR},
        		{"JSHMC", "%-64s", FieldSource.VAR},
        		{"YQYYHDM", "%-4s", FieldSource.VAR},
        		{"YQYZH", "%-32s", FieldSource.VAR},
        		{"YQYZHMC", "%-128s", FieldSource.VAR},
        		{"XQYZH", "%-32s", FieldSource.VAR},
        		{"XQYZHMC", "%-128s", FieldSource.VAR},
        		{"ActFlg", "%-1s", FieldSource.VAR},
        		{"IdTyp", "%-2s", FieldSource.VAR},
        		{"ZJHM", "%-32s", FieldSource.VAR},
        		{"LXDH", "%-20s", FieldSource.VAR},
        		{"SJHM", "%-20s", FieldSource.VAR},
        		{"EMAIL", "%-64s", FieldSource.VAR},
        		{"BZ", "%-128s", FieldSource.VAR},
        		{"QYZT", "%-1s", FieldSource.VAR},
        		{"VchTyp", "%-3s", FieldSource.VAR},
        		{"VchCod", "%-8s", FieldSource.VAR},
        		{"ActSqn", "%-5s", FieldSource.VAR},
        		{"Pswd", "%-20s", FieldSource.VAR},
        		{"PfaSub", "%-3s", FieldSource.VAR},
        		{"BCusId", "%-18s", FieldSource.VAR},
        		{"PayCod", "%-30s", FieldSource.VAR},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "460430");
        request.put("FeCod", "460430");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("Func", "0");//0增加；1修改；2查询；3删除
        request.put("DWBM", "032025");
        request.put("JFH", "0320009900178466");
        request.put("JSHMC", "林金华");
        request.put("YQYYHDM", "");
        request.put("YQYZH", "");
        request.put("YQYZHMC", "");
        request.put("XQYZH", "60142890710180319");
        request.put("XQYZHMC", "杨立文");
        request.put("ActFlg", "4");
        request.put("IdTyp", "15");
        request.put("ZJHM", "440528197203072416");
        request.put("LXDH", "82110517");
        request.put("SJHM", "13632394114");
        request.put("EMAIL", "");
        request.put("BZ", "");
        request.put("QYZT", "0");
        request.put("VchTyp", "");
        request.put("VchCod", "");
        request.put("ActSqn", "");
        request.put("Pswd", "1234567890");
        request.put("PfaSub", "");
        request.put("BCusId", "");
        request.put("PayCod", "");

        Map responseMap = Transation
                .exchangeData(IcsServer.getServer("@EFEK"),
                request,
                TransationFactory.EFEK460430);
        System.out.println(responseMap);
        /*System.out.println(responseMap.get("IdNo"));*/

    }

}
