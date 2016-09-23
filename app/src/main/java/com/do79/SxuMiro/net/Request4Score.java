package com.do79.SxuMiro.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.do79.SxuMiro.model.score.StudentScore;
import com.do79.SxuMiro.utils.UserInfoUtil;
import com.do79.SxuMiro.utils.logger.Logger;

import org.json.JSONObject;


public class Request4Score extends Request<StudentScore> {

    private Response.Listener<StudentScore> listener;

    public Request4Score(String url, Response.Listener<StudentScore> listener,
                         Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<StudentScore> parseNetworkResponse(NetworkResponse response) {

        try {
            String resultStr = new String(response.data, HttpHeaderParser.parseCharset(response
                    .headers));
            Logger.e(resultStr);
            JSONObject jsonObject = new JSONObject(resultStr);
            if (jsonObject.opt("status").equals("ok")) {
                StudentScore studentScore = new StudentScore();
                studentScore = (StudentScore) JSONParser.toObject(resultStr, StudentScore.class);
                return Response.success(studentScore, HttpHeaderParser.parseCacheHeaders(response));
//				JSONObject contentObject = jsonObject.optJSONObject("post");
//				return Response.success(contentObject.optString("content"), HttpHeaderParser.parseCacheHeaders
//						(response));
            } else {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(StudentScore response) {
        listener.onResponse(response);
    }

}
