
package com.reactlibrary;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RNFastpayModule extends ReactContextBaseJavaModule {

    public static String WX_APPID = "";


    public RNFastpayModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "FastPay";
    }

    @ReactMethod
    public void setAlipaySandbox(Boolean isSandbox) {
        if (isSandbox) {
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        } else {
            EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
        }
    }

    @ReactMethod
    public void alipay(final String orderInfo, final Callback promise) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getCurrentActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                WritableMap map = Arguments.createMap();
                map.putString("memo", result.get("memo"));
                map.putString("result", result.get("result"));
                map.putString("resultStatus", result.get("resultStatus"));
                promise.invoke(map);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @ReactMethod
    public void asiabillpay(final String orderInfo, final Callback promise) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    com.asiabill.sdk.PayTask payTask = new com.asiabill.sdk.PayTask(getCurrentActivity());
                    String result = payTask.pay(orderInfo);
                    JSONObject obj = new JSONObject(result);
                    WritableMap map = Arguments.createMap();
                    map.putString("resultStatus", obj.getString("code"));
                    map.putString("memo", obj.getString("message"));
                    JSONObject optional = obj.getJSONObject("optionl");
                    WritableMap mapOptional = Arguments.createMap();
                    mapOptional.putString("detailCode",optional.getString("detailCode"));
                    mapOptional.putString("detailMessage",optional.getString("detailMessage"));
                    map.putMap("result",mapOptional);
                    promise.invoke(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @ReactMethod
    public void setWxId(String id) {
        WX_APPID = id;
    }

    @ReactMethod
    public void wxPay(ReadableMap params, final Callback callback) {
        IWXAPI api = WXAPIFactory.createWXAPI(getCurrentActivity(), WX_APPID);
        //data  根据服务器返回的json数据创建的实体类对象
        PayReq req = new PayReq();
        req.appId = WX_APPID;
        req.partnerId = params.getString("partnerId");
        req.prepayId = params.getString("prepayId");
        req.packageValue = params.getString("packageValue");
        req.nonceStr = params.getString("nonceStr");
        req.timeStamp = params.getString("timeStamp");
        req.sign = params.getString("sign");
        api.registerApp(WX_APPID);
        XWXPayEntryActivity.callback = new WXPayCallBack() {
            @Override
            public void callBack(WritableMap result) {
                callback.invoke(result);
            }
        };
        //发起请求
        api.sendReq(req);
    }

}
