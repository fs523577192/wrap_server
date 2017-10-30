package org.firas.weixin.service;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.firas.weixin.datatype.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.service.HttpClientUtil;
import org.firas.common.helper.RandomHelper;
import org.firas.common.helper.Hash;
import org.firas.common.helper.HexString;
import org.firas.weixin.model.WeixinApp;
import org.firas.weixin.dao.MpAccessTokenDao;
import org.firas.weixin.dao.JsapiTicketDao;

/**
 * 微信公众平台接口
 */
@Service
@Slf4j
public class MpService {

    public static final String ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token";
    public static final Map<String, Object> ACCESS_TOKEN_PARAM =
            new HashMap<String, Object>();

    public static final String JSAPI_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public static final Map<String, Object> JSAPI_TICKET_PARAM =
            new HashMap<String, Object>();

    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    public static final String OAUTH2_AUTHORIZE_URL= "https://open.weixin.qq.com/connect/oauth2/authorize";

    public static final ParameterizedTypeReference<MpAccessToken>
            TYPE_ACCESS_TOKEN = new
            ParameterizedTypeReference<MpAccessToken>(){};

    public static final ParameterizedTypeReference<JsapiTicket>
            TYPE_JSAPI_TICKET = new
            ParameterizedTypeReference<JsapiTicket>(){};

    public static final ParameterizedTypeReference<String>
            AUTHORIZE_ACCESS_TOKEN = new
            ParameterizedTypeReference<String>(){};

    public static final ParameterizedTypeReference<String>
            WX_USER_INFO = new ParameterizedTypeReference<String>(){};



    static {
        ACCESS_TOKEN_PARAM.put("grant_type", "client_credential");
        JSAPI_TICKET_PARAM.put("type", "jsapi");
    }


    private WeixinAppService appService;
    @Autowired
    public void setAppService(WeixinAppService appService) {
        this.appService = appService;
    }

    private MpAccessTokenDao tokenDao;
    @Autowired
    public void setTokenDao(MpAccessTokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    private JsapiTicketDao ticketDao;
    @Autowired
    public void setTicketDao(JsapiTicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }


    public static String echoSign(
            String nonce, String token, String timestamp) {
        String[] parameters = new String[] {nonce, token, timestamp};
        Arrays.sort(parameters);
        StringBuilder buffer = new StringBuilder();
        for (String item : parameters) {
            buffer.append(item);
        }
        byte[] bytes = Hash.sha1(buffer.toString());
        return HexString.byteArray2HexString(bytes, "");
    }

    public static boolean checkEcho(
            String nonce, String token, String timestamp, String signature) {
        if (null == nonce || null == token || null == timestamp ||
                null == signature) {
            return false;
        }
        return echoSign(nonce, token, timestamp).equalsIgnoreCase(signature);
    }

    public static MpAccessToken getAccessToken(String appId, String appSecret)
            throws RestClientException, FileNotFoundException, IOException {
        Map<String, Object> param = new HashMap<String, Object>(
                ACCESS_TOKEN_PARAM);
        param.put("appid", appId);
        param.put("secret", appSecret);
        MpAccessToken accessToken = HttpClientUtil.doGet(
                ACCESS_TOKEN_URL, param, null, TYPE_ACCESS_TOKEN);
        try {
            log.info(new ObjectMapper().writeValueAsString(accessToken));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return accessToken;
    }

    public String getAccessToken(Integer weixinAppId)
            throws RestClientException,
            AppNotFoundException,
            FileNotFoundException,
            IOException {
        WeixinApp weixinApp = appService.getWeixinAppById(weixinAppId);
        if (null == weixinApp) throw new AppNotFoundException(
                "该ID的微信应用不存在");
        return getAccessToken(weixinApp);
    }

    public String getAccessToken(WeixinApp weixinApp)
            throws RestClientException, FileNotFoundException, IOException {
        String token = tokenDao.get(weixinApp.getId());
        if (null != token) return token;

        MpAccessToken result = getAccessToken(weixinApp.getAppId(),
                weixinApp.getAppSecret());
        if (result.getToken() == null) throw new RestClientException(
                "向微信公众平台请求获取AccessToken失败");

        if (result.getExpiresIn() > 0) {
            tokenDao.cache(weixinApp.getId(), result.getToken(),
                     result.getExpiresIn());
        }
        return result.getToken();
    }


    public static JsapiTicket getJsapiTicket(String accessToken)
            throws RestClientException, FileNotFoundException, IOException {
        Map<String, Object> param = new HashMap<String, Object>(
                JSAPI_TICKET_PARAM);
        param.put("access_token", accessToken);
        JsapiTicket ticket = HttpClientUtil.doGet(
                JSAPI_TICKET_URL, param, null, TYPE_JSAPI_TICKET);
        try {
            log.info(new ObjectMapper().writeValueAsString(ticket));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ticket;
    }

    public String getJsapiTicket(Integer weixinAppId)
            throws RestClientException,
            AppNotFoundException,
            FileNotFoundException,
            IOException {
        WeixinApp weixinApp = appService.getWeixinAppById(weixinAppId);
        if (null == weixinApp) throw new AppNotFoundException(
                "该ID的微信应用不存在");
        return getJsapiTicket(weixinApp);
    }

    public String getJsapiTicket(WeixinApp weixinApp)
            throws RestClientException, FileNotFoundException, IOException {
        String ticket = ticketDao.get(weixinApp.getId());
        if (null != ticket) return ticket;

        JsapiTicket result = getJsapiTicket(getAccessToken(weixinApp));
        if (result.getTicket() == null) throw new RestClientException(
                "向微信公众平台请求获取JsapiTicket失败");

        if (result.getExpiresIn() > 0) {
            ticketDao.cache(weixinApp.getId(), result.getTicket(),
                     result.getExpiresIn());
        }
        return result.getTicket();
    }


    public static MpSignResult sign(String url, String ticket, String appId) {
        MpSignResult result = new MpSignResult();
        result.setAppId(appId);
        result.setTimestamp(new Date().getTime() / 1000);
        result.setNonceStr(RandomHelper.randomAlphaNumericString(16));
        StringBuilder buffer = new StringBuilder("jsapi_ticket=");
        buffer.append(ticket);
        buffer.append("&noncestr=");
        buffer.append(result.getNonceStr());
        buffer.append("&timestamp=");
        buffer.append(result.getTimestamp());
        buffer.append("&url=");
        buffer.append(url);
        byte[] bytes = Hash.sha1(buffer.toString());
        result.setSignature(HexString.byteArray2HexString(bytes, ""));
        return result;
    }

    public MpSignResult sign(String url, WeixinApp weixinApp)
            throws RestClientException, FileNotFoundException, IOException {
        return sign(url, getJsapiTicket(weixinApp), weixinApp.getAppId());
    }


    public static final String DOWNLOAD_MEDIA_URL =
            "https://api.weixin.qq.com/cgi-bin/media/get";

    public static final ParameterizedTypeReference<byte[]>
            TYPE_BYTE_ARRAY = new
            ParameterizedTypeReference<byte[]>(){};

    public static final Map<String, Object> EMPTY_MAP =
            new HashMap<String, Object>();

    public ResponseEntity<byte[]> downloadMedia(
            WeixinApp weixinApp, String mediaId)
            throws RestClientException, FileNotFoundException, IOException {
        StringBuilder url = new StringBuilder(DOWNLOAD_MEDIA_URL);
        url.append("?access_token=");
        url.append(getAccessToken(weixinApp));
        url.append("&media_id=");
        url.append(mediaId);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url.toString(), HttpMethod.GET,
                HttpEntity.EMPTY, TYPE_BYTE_ARRAY, EMPTY_MAP);
    }

    public MpAuthorizeAccessToken getAuthorizeAccessToken(
            String appName, String code
    ) throws  RestClientException, FileNotFoundException, IOException{
        Map<String, Object> map = new HashMap<String, Object>();
        WeixinApp app = appService.getWeixinAppByName(appName);
        String appId = app.getAppId();
        String appSecret = app.getAppSecret();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("code", code);
        map.put("grant_type", "authorization_code");
        String result = HttpClientUtil.doGet(
                OAUTH2_ACCESS_TOKEN_URL, map, null, AUTHORIZE_ACCESS_TOKEN);
        log.debug(result);
        MpAuthorizeAccessToken mpAuthorizeAccessToken = (new ObjectMapper())
                .readValue(result, MpAuthorizeAccessToken.class);
        return mpAuthorizeAccessToken;
    }

    public MpUserInfo getWeiXinUserInfo(
            String accessToken, String openid, String lang
    )  throws  RestClientException, FileNotFoundException, IOException{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", accessToken);
        map.put("openid", openid);
        map.put("lang", lang);
        String result = HttpClientUtil.doGet(
                USER_INFO_URL, map, null, WX_USER_INFO);
        log.debug(result);
        MpUserInfo userInfo = (new ObjectMapper())
                .readValue(result, MpUserInfo.class);
        return userInfo;
    }


    public String getAuthorizeUrl(
            String appName, String uri, String state
    ) throws  RestClientException {
        StringBuilder url = new StringBuilder(OAUTH2_AUTHORIZE_URL);
        WeixinApp app = appService.getWeixinAppByName(appName);
        String appId = app.getAppId();
        url.append("?appid=");
        url.append(appId);
        url.append("&redirect_uri=");
        url.append(uri);
        url.append("&response_type=code&scope=snsapi_userinfo&state=");
        url.append(state);
        url.append("#wechat_redirect");
        return url.toString();
    }

}
