package org.example.utils;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;

public class SmsUtil {
    public Boolean sendSms(String phone, String templateCode, String templateParam) {
        // 创建阿里云客户端
        Config config = new Config()
                .setAccessKeyId("")// 配置访问密钥 ID
                .setAccessKeySecret("");// 配置密钥
        config.endpoint = "dysmsapi.aliyuncs.com";// 配置访问端点
        Client client;
        try {
            client = new Client(config);
        } catch (Exception e) {
            System.out.println("阿里云短信服务初始化失败" + e);
            return false;
        }
        // 构建发送请求
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("HIT的平时成绩管理") // 设置签名
                .setTemplateCode(templateCode) // 设置模板
                .setPhoneNumbers(phone) // 设置手机号为参数传入的值
                .setTemplateParam(templateParam); // 设置模板为参数传入的值

        RuntimeOptions runtime = new RuntimeOptions();// 运行时选择，可以设置不同的属性来配置运行时环境的参数。
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            if (!"OK".equals(sendSmsResponse.getBody().getCode())) {
                System.out.println("短信发送失败:{}"+ sendSmsResponse.getBody().getMessage());
                return false;
            }
            System.out.println("短信发送成功");
            return true;

        } catch (Exception error) {
            System.out.println("短信发送失败:{}" + error.getMessage());
            return false;
        }
    }
}
