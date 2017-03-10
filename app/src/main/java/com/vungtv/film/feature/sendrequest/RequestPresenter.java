package com.vungtv.film.feature.sendrequest;

import android.content.Context;

import com.vungtv.film.R;
import com.vungtv.film.data.source.remote.service.RequestServices;
import com.vungtv.film.util.StringUtils;

/**
 *
 * Created by pc on 3/10/2017.
 */

public class RequestPresenter implements RequestContract.Presenter, RequestServices.ResultCallback {

    private final Context context;

    private final RequestContract.View activityView;

    private final RequestServices requestServices;

    public RequestPresenter(Context context, RequestContract.View activityView) {
        this.context = context;
        this.activityView = activityView;

        this.activityView.setPresenter(this);

        requestServices = new RequestServices(context);
        requestServices.setResultCallback(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        requestServices.cancel();
    }

    @Override
    public void sendRequest(String title, String content) {
        if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(content)) {
            activityView.showLoading(true);
            requestServices.sendRequest(title, content);
        } else {
            activityView.showMsgError(context.getString(R.string.request_error_require));
        }
    }

    @Override
    public void onFailure(int code, String error) {
        activityView.showLoading(false);
        activityView.showMsgError(error);
    }

    @Override
    public void onRequestResultSuccess(String msg) {
        activityView.showLoading(false);
        activityView.showMsgError(msg);
        activityView.resetEdittext();
    }
}
