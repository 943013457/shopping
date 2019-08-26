package com.Util;

/**
 * @Creator Ming
 * @Time 2019/8/25
 * @Other
 */
public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101400684652";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgDR+GvEhr4C6HiRbbojxtoCKO73xcWoLa5jbWRQs7XxUqxzKVqfRHpe9rbyduTLIBN9c1hWan9zNSEBm3jrnRfWX4q3MiyRGS9sc67LIW9grMyr/PuofyF/09qJ1N6lPLljv4DEojVHy5GjWjGYVo+WZVldItrl2tjpJFMIamG9IZjfBxI/cOcttqdwt+0nnfilHCPlkTFcXuju1KZhQfLiqLNpb8OlkdU7GqDWqzGKJnEY4kotRn3KCEZMRQv4PGMDz3oHxSmVlVUuM7XHjR5G0N6G68ZAyP5els1CdmxXLqJF/fPWpbGFMVKdHKJS208JsyorImvuelChqI1lnTAgMBAAECggEAWsl245+z2PVOj3U08Rsi+fBHChtQyLZS2caSvpJln6T8xco+qGTCe03l9GQV0txQcJ5zRhnItGhlxRv66oNcZjeiex8N7WJuxJWVR5hyINIqKCauDTA64xm2fZ5CtKrCI46xr6+Q8l9/bCQw3ragVX57DPsyQNVSsBtaX0L6bggd6R++gx+BrO7dk8JQ1ZQqv12NWdExhb+IdkU5fDPyDKo6JLe5shbfsFuxyZsVqnMtVivQ6xXdpQwXPae1MD81VEJ0zqcTRZXdvEA5iQ5uRolpS6ndwOA9Nj3Ky857IweF/DvTqNLRU34IB2dNqJQf/sT/yBYUo6Rs0bUBOkcY8QKBgQDWpNXusXMBIQ6Qegi5Grzg33RZZsgY58YYt9iuI9ehmZgPf7HPz+tow/9ZXN4Bh2ODmYS9rV9O/EiHyRR7XehGqgzd7Nm3sUo5aK80jjVDWRXd6AZToFG0Xsf47mYY01mrBIPytky7qEIka+jqa7vhbOBAxNHiGIJyEhjuOrIXaQKBgQC+44sMYdnRmUq9qVSDvT+xEIA1MhP+cHv2Fh74FJ8VQXw/rII04Xo1ErbW4J2sVGEdf78+EBLEhv6wO6ztlnUqj89EkDbJ8RttOs59RZNpanU3gwUc/AlSoZr9Bdyf4ZVah5hJmCbCh3UDJnOycrFvdu6yI8nTMNi1FDiAl5pb2wKBgEtrmBBLIdHfv7Jcbvw7vOp3Gfbf+qdlqWG+F9D4nygVRC0N/Xi74kfptqu7yfZXkEwJgW70oGyskP/FVAEVOiy6S9MfClFVZnNNqWKaJXUItHpIsRBpYMw0EagSfKV0filtaL+79rJpqPQLRsg4GshW7WVUwzriydMzMLdkwHchAoGBALt16k+2qJpV2Dh52i2l5K6WbdHP5CKzhIkfIDqwxamyLbq6RwR3ifQk9LoAy9c45kZ59e2VC1lE2Ulo0WJhp56sq/fBSIz44t5aKf6MHUAg9AwX9yWHvgAQBI0G/Ktmda36C2v6p9XSAO+fC2/rgqTcP31h+AoWpjruKK7gMw6RAoGAelI1t0tAv6m3cDjvgBIAgdkZcUzaDl2UOGXPWz2xgywOfJsNaFBXmD6/WGuu8vzia9LQwRNME/SZZkvZfx8YdFuHzmI5ejaMHQdcI3DT62qyF77WrQIX1I4nW3yDcvG6W1nHm3xQ6ZL2mSpiCxzDAJcuuzHo3ddm/MuxPdaCtxo=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg6mF2pDlE3ZAfMkYiglXq8Jji7/VtXm47IyfrIQ3N6ntipvaZi/ARhMsAAqy6Yy/Bye+1nexnRHt4j6SxUTQelxvpY/gDZfObyJ1b8y7yYDiY178pLE1yTtTObcQxwufcj0+uDnys0TBxfBpk+eE3PfQH0Onehkq9au1Yxady1s1/TMI0Kel3JNsAuYrkdzLC6xpR9W4JNK4CP85kB79t60fiiCpxDZK0cVbG6+YqJ0TdlRmVL268Fgr2bWXhVKlkTSSJV4Z6NSa8rHPlPzlwh2OfJzcR95rNOZGwy8dBTKFxAcbQKkzsxl8uMQHrBFZXnz/4bHOI0uJx8UiQqn8GQIDAQAB";
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://16607710426.zicp.vip/notifyUrl";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://16607710426.zicp.vip/returnUrl";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    //沙箱网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    // 仅支持JSON
    public static String format = "JSON";
}
