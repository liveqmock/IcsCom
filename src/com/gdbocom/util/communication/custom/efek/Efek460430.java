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
        		{"Func", "%-1s", FieldTypes.STATIC},
        		{"DWBM", "%-8s", FieldTypes.STATIC},
        		{"JFH", "%-20s", FieldTypes.STATIC},
        		{"JSHMC", "%-64s", FieldTypes.STATIC},
        		{"YQYYHDM", "%-4s", FieldTypes.STATIC},
        		{"YQYZH", "%-32s", FieldTypes.STATIC},
        		{"YQYZHMC", "%-128s", FieldTypes.STATIC},
        		{"XQYZH", "%-32s", FieldTypes.STATIC},
        		{"XQYZHMC", "%-128s", FieldTypes.STATIC},
        		{"ActFlg", "%-1s", FieldTypes.STATIC},
        		{"IdTyp", "%-2s", FieldTypes.STATIC},
        		{"ZJHM", "%-32s", FieldTypes.STATIC},
        		{"LXDH", "%-20s", FieldTypes.STATIC},
        		{"SJHM", "%-20s", FieldTypes.STATIC},
        		{"EMAIL", "%-64s", FieldTypes.STATIC},
        		{"BZ", "%-128s", FieldTypes.STATIC},
        		{"QYZT", "%-1s", FieldTypes.STATIC},
        		{"VchTyp", "%-3s", FieldTypes.STATIC},
        		{"VchCod", "%-8s", FieldTypes.STATIC},
        		{"ActSqn", "%-5s", FieldTypes.STATIC},
        		{"Pswd", "%-20s", FieldTypes.STATIC},
        		{"PfaSub", "%-3s", FieldTypes.STATIC},
        		{"BCusId", "%-18s", FieldTypes.STATIC},
        		{"PayCod", "%-30s", FieldTypes.STATIC},
        };
        return Transation.packetSequence(request, format);
    }

    
    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
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
        request.put("Func", "");
        request.put("DWBM", "");
        request.put("JFH", "");
        request.put("JSHMC", "");
        request.put("YQYYHDM", "");
        request.put("YQYZH", "");
        request.put("YQYZHMC", "");
        request.put("XQYZH", "");
        request.put("XQYZHMC", "");
        request.put("ActFlg", "");
        request.put("IdTyp", "");
        request.put("ZJHM", "");
        request.put("LXDH", "");
        request.put("SJHM", "");
        request.put("EMAIL", "");
        request.put("BZ", "");
        request.put("QYZT", "");
        request.put("VchTyp", "");
        request.put("VchCod", "");
        request.put("ActSqn", "");
        request.put("Pswd", "");
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
