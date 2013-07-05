package com.gdbocom.util.communication.custom.lsha;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.gdbocom.util.communication.FieldSource;
import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

public class Lsha482150 extends Transation {


    protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {
        /*
         *  key:关键字, 
         *  String.format{String} eg:%6c ：输出格式, 
         *  [FieldSource|value]：来源或值, 
         */
        Object[][] format = {
                {"TxnDat", "%-8s", FieldSource.VAR},
                {"AppNm", "%-8s", FieldSource.VAR},
                {"PrtFlg", "%-1s", FieldSource.VAR}
        };
        return Transation.packetSequence(request, format);
    }

    protected Map parseNormalResponseBody(
            byte[] response) throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode","2",FieldTypes.STATIC},
                {"OFmtCd","3",FieldTypes.STATIC},
                {"RpMsg1","60",FieldTypes.STATIC},
        };
        return Transation.unpacketsSequence(response, format);
    }

    public static void main(String[] args) throws Exception{
        //System.out.println("%-1s".replaceAll("[^\\d]+", ""));
        Map request = new HashMap();
        request.put("TTxnCd", "482150");
        request.put("FeCod", "482150");
        request.put("TxnSrc", "MB441");
        request.put("TxnDat", "20140320");
        request.put("AppNm", "GZ_UNC");
//        request.put("AppNm", "GZ_UNC中发动机看");
        request.put("PrtFlg", "1");
        
        Transation.exchangeData(IcsServer.getServer("@LSHA"),
                request,
                TransationFactory.LSHA482150);
    }

}
