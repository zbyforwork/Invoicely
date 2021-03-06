package com.dzfp.dzfp.presenter;

import android.content.Context;
import android.util.Log;

import com.dzfp.dzfp.model.ResultRoot;
import com.dzfp.dzfp.model.User.Data;
import com.dzfp.dzfp.control.RegisterControl;
import com.dzfp.dzfp.model.User.UserMessage;
import com.dzfp.dzfp.model.User.UserRoot;
import com.dzfp.dzfp.nohttp.CallServer;
import com.dzfp.dzfp.nohttp.HttpListener;
import com.dzfp.dzfp.ui.util.UrlUtils;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class RegisterPresenter implements RegisterControl.Presenter {
    private RegisterControl.View mView;
    private Context mContext;

    public RegisterPresenter(RegisterControl.View mView) {
        this.mView = mView;
        mContext = mView.getViewContext();
    }

    @Override
    public void register(String requestParameter) {
        Request<JSONObject> mRequest = NoHttp.createJsonObjectRequest(UrlUtils.mainUrl, RequestMethod.POST);
        mRequest.setDefineRequestBodyForJson(requestParameter);
        CallServer.getRequestInstance().add(0, mContext, mRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                switch (mView.getRegisterType()) {
                    case 0:
                        ResultRoot mRoot = new Gson().fromJson(response.get().toString(), ResultRoot.class);
                        mView.hintMessage(mRoot.getMsg());
                        if (mRoot.getResultType().equals("SUCCESS")) {
                            mView.registerSuccess();
                        } else {
                            mView.OnHttpListenerFailed("");
                        }
                        break;
                    case 1:
                        UserRoot mUserRoot = new Gson().fromJson(response.get().toString(), UserRoot.class);
                        if (mUserRoot.getResultType().equals("SUCCESS")) {
                            Data mData = mUserRoot.getData();
                            // mData.setAthID("BF11C2E3BA0C4D0CB6831F8DECF81516");
                            UserMessage.newInstance().setUserBean(mData);
                            mView.registerSuccess();
                        } else {
                            mView.OnHttpListenerFailed("");
                        }
                        mView.hintMessage(mUserRoot.getMsg());
                        break;
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, String error, int resCode, long ms) {
                Log.e(TAG, "RegisterPresenter_register_onFailed_url: " + url);
                Log.e(TAG, "RegisterPresenter_register_onFailed_tag: " + tag);
                Log.e(TAG, "RegisterPresenter_register_onFailed_error: " + error);
                Log.e(TAG, "RegisterPresenter_register_onFailed_resCode: " + resCode);
                Log.e(TAG, "RegisterPresenter_register_onFailed_ms: " + ms);
                mView.OnHttpListenerFailed(error);
            }
        });
    }

    @Override
    public void start() {

    }
}
