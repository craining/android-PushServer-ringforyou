package com.zgy.pushserverringforyou;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushTagMessageRequest;
import com.baidu.yun.channel.model.PushTagMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pushTagMessage();
	}

	private void pushTagMessage() {
		/*
		 * @brief 推送单播消息(消息类型为透传，由开发方应用自己来解析消息内容) message_type = 0 (默认为0)
		 */

		// 1. 设置developer平台的ApiKey/SecretKey
		String apiKey = "zv2f7R1Q2bqcBK3SYZoNq8Zq";
		String secretKey = "tKTSwji6lDP4hmBHtu2GAycdRGz48LyA";
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		channelClient.setChannelLogHandler(mHandler);

		try {

			// 4. 创建请求类对象
			PushTagMessageRequest request = new PushTagMessageRequest();
			request.setDeviceType(3); // device_type => 1: web 2: pc 3:android 4:ios 5:wp
			request.setTagName("DEBUG");
			// request.setMessage("Hello Channel");
			request.setMessageType(1);
//			String message = "{\"custom_content\":{\"content\":\"value2\",\"title\":\"value1\"},\"description\":\"ringforyou_push_msg\",\"title\":\"ringforyou_push_msg\"}";
//			request.setMessage(message);
			 request.setMessage("{\"title\":\"Notify_title_danbo\",\"description\":\"Notify_description_content\"}");

			// 5. 调用pushMessage接口
			PushTagMessageResponse response = channelClient.pushTagMessage(request);

			// 6. 认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount());

		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			e.printStackTrace();
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			System.out.println(String.format("request_id: %d, error_code: %d, error_message: %s", e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		}

	}

	private YunLogHandler mHandler = new YunLogHandler() {

		@Override
		public void onHandle(YunLogEvent event) {
			System.out.println(event.getMessage());
		}
	};

}
