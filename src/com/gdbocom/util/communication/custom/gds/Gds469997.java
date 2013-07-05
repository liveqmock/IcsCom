package com.gdbocom.util.communication.custom.gds;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.gdbocom.util.communication.FieldTypes;
import com.gdbocom.util.communication.IcsServer;
import com.gdbocom.util.communication.Transation;
import com.gdbocom.util.communication.TransationFactory;

/**
 * 这个469997通讯并未写好，暂时只有水务才分各个区，因此暂时使用在jsp页面上枚举的
 * 方式。
 * @author qm
 * @deprecated
 */
public class Gds469997 extends Transation {


    protected byte[] buildRequestBody(Map request)
            throws UnsupportedEncodingException {

        /*Object[][] format = {
                <Item name="Func"   length=" 1" desc="功能选择"/>
                <Item name="OrgCod" length="12" expression="DELSPACE($OrgCod,all)" desc=
        "机构代码"/>
                <Item name="OrgNam" length="40" expression="DELSPACE($OrgNam,all)" desc=
        "机构名称"/>
                <Item name="CityCd" length=" 4" expression="DELSPACE($CityCd,all)" desc=
        "城市代码"/>
                {"Func", "%-1s", "4"},//"账户性质,4:太平洋卡
                {"OrgCod", "%-21s", FieldSource.VAR},
                {"OrgNam", "%-5s", ""},
                {"CityCd", "%-1s", "0"},
};*/
        //return Transation.packetSequence(request, format);
        return "".getBytes();
    }


    protected Map parseNormalResponseBody(byte[] response)
            throws UnsupportedEncodingException {

        Object[][] format = {
                {"TmpDat","4",FieldTypes.STATIC},
                {"ApCode", "2", FieldTypes.STATIC},
                {"OFmtCd", "3", FieldTypes.STATIC},
                {"ActNo", "21", FieldTypes.STATIC},//帐号
                {"CcyCod", "3", FieldTypes.STATIC},//币种
                {"ActTyp", "1", FieldTypes.STATIC},//账卡标识
                {"Bal", "15", FieldTypes.STATIC},//账户余额
                {"HldAmt", "15", FieldTypes.STATIC},//银证冻结额
                {"ActSts", "1", FieldTypes.STATIC},//帐户状态
                {"CrdTyp", "1", FieldTypes.STATIC},//卡种类
                {"VchCod", "3", FieldTypes.STATIC},//凭证代码
                {"Vch", "8", FieldTypes.STATIC},//凭证号码
                {"IDTyp", "2", FieldTypes.STATIC},//证件种类
                {"IDNo", "30", FieldTypes.STATIC},//证件号码
                {"CusNo", "13", FieldTypes.STATIC},//客户号
                {"ActNam", "60", FieldTypes.STATIC},//户名
                {"ManFlg", "1", FieldTypes.STATIC},//主附卡标志
                {"CpFlg", "1", FieldTypes.STATIC},//商户个人标志
        };
        return Transation.unpacketsSequence(response, format);

    }

    public static void main(String[] args) throws UnknownHostException, IOException{
        Map request = new HashMap();
        //报文头字段
        request.put("TTxnCd", "469998");
        request.put("FeCod", "469998");
        request.put("TxnSrc", "MB441");
        //报文体字段
        request.put("ActNo", "6222600710007815865");

        Transation.exchangeData(IcsServer.getServer("@GDS"),
                request,
                TransationFactory.GDS469998);

    }
}
