package com.example.track_hailing;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 叶泽锐 on 2017/11/30.
 */

public class SoapObjectUser {
    public static Object call(String url, String nameSpace, String methodName,
                              HashMap<String, Object> params) {

        String SOAP_ACTION = nameSpace + methodName;
        Object result = null;

        try {
            SoapObject request = new SoapObject(nameSpace, methodName);


            //请求参数
            if (params != null && !params.isEmpty()) {
                for (Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry e = (Map.Entry) it.next();
                    request.addProperty(e.getKey().toString(), e.getValue());
                }
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true; //.net 支持

            //发送请求
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            //得到返回结果
            if (envelope.getResponse() != null)
                result = envelope.getResponse();

            androidHttpTransport.reset();
        } catch (Exception e) {

            //new ToastMessageTask().execute( "访问服务器连接异常，请重试！");
        }
        return result;
    }
}
