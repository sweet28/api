package com.arttraining.api.testmybatis;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.qiniu.pili.Client;
import com.qiniu.pili.Config;
import com.qiniu.pili.Hub;
import com.qiniu.pili.PiliException;
import com.qiniu.pili.Stream;

public class ClientTest {
	String accessKey;
    String secretKey;
    String streamKeyPrefix;
    String hubName;
    Client cli;
    Hub hub;
    String keyA = "A";


    @Before
    public void prepare() {
        // local test environment
    	// 配置企业开发者密钥
    	// 密钥使用七牛账号登录 https://portal.qiniu.com/user/key 获取
        accessKey = "ZqROaKfnfjygnaWWfYjPUkqvrSdlAHOrJgnz0SDu";
        secretKey = "LvxeWmiHaXfqCP3AwXO_GWkKEPC6fyA0_xL6186W";
        // 直播空间名称
        // 直播空间必须事先存在，可以在 portal.qiniu.com 上创建
        hubName = "yhy-live";
        streamKeyPrefix = "javasdktest" + String.valueOf(new Date().getTime());

        cli = new Client(accessKey, secretKey);
        hub = cli.newHub(hubName);
    }

    private boolean skip() {
        return Config.APIHost != "pili.qiniuapi.com";
    }

    @Test
    public void testGetNoExistStream() {
        Assume.assumeTrue(skip());

        try {
            hub.get("nnnoexist");
            fail("should not exist");
        } catch (PiliException e) {
            assertTrue(e.isNotFound());
        }
    }

    @Test
    public void testStreamOperate() {
        Assume.assumeTrue(skip());

        String streamKey = streamKeyPrefix + keyA;
        // create
        try {
            hub.create(streamKey);
        } catch (PiliException e) {
            fail();
        }

        // get
        Stream stream = null;
        try {
            stream = hub.get(streamKey);
            assertEquals(0, stream.getDisabledTill());
            assertEquals(hubName, stream.getHub());
            assertEquals(streamKey, stream.getKey());
        } catch (PiliException e) {
            fail();
        }

        // create again
        try {
            hub.create(streamKey);
            fail("has already existed");
        } catch (PiliException e) {
            assertTrue(e.isDuplicate());
        }

        //disable
        try {
            stream = hub.get(streamKey);
            stream.disable();
            stream = hub.get(streamKey);
            assertEquals(-1, stream.getDisabledTill());
            assertEquals(hubName, stream.getHub());
            assertEquals(streamKey, stream.getKey());
        } catch (PiliException e) {
            fail();
        }

        //enable
        try {
            stream = hub.get(streamKey);
            stream.enable();
            stream = hub.get(streamKey);
            assertEquals(0, stream.getDisabledTill());
            assertEquals(hubName, stream.getHub());
            assertEquals(streamKey, stream.getKey());
        } catch (PiliException e) {
            fail();
        }

    }

    @Test
    public void testLiveStatus() {
        Assume.assumeTrue(skip());

        String streamKey = streamKeyPrefix + "livestatus";
        try {
            Stream stream = hub.create(streamKey);
            Stream.LiveStatus status = stream.liveStatus();
            fail();
        } catch (PiliException e) {
            assertTrue(e.isNotInLive());
        }
    }

    @Test
    public void testSave() {
        Assume.assumeTrue(skip());

        String streamKey = streamKeyPrefix + "save";
        try {
            Stream stream = hub.create(streamKey);
            stream.save(0, 0);
            fail();
        } catch (PiliException e) {
            assertTrue(e.isNotInLive());
        }
    }

    @Test
    public void testHistory() {
        Assume.assumeTrue(skip());

        String streamKey = streamKeyPrefix + "history";
        try {
            Stream stream = hub.create(streamKey);
            Stream.Record[] records = stream.historyRecord(0, 0);
            assertEquals(0, records.length);
        } catch (PiliException e) {
            fail();
        }
    }

    @Test
    public void testList() {
        Assume.assumeTrue(skip());

        String streamKeyB = streamKeyPrefix + "B";
        try {
            hub.create(streamKeyB + "1");
            hub.create(streamKeyB + "2");
        } catch (PiliException e) {
            fail();
        }

        try {
            Hub.ListRet listRet = hub.list(streamKeyB, 0, "");
            assertEquals(2, listRet.keys.length);
            assertEquals("", listRet.omarker);
        } catch (PiliException e) {
            fail();
        }
    }

    @Test
    public void testListLive() {
        Assume.assumeTrue(skip());

        try {
            Hub.ListRet listRet = hub.listLive(streamKeyPrefix, 0, "");
            assertEquals(0, listRet.keys.length);
        } catch (PiliException e) {
            fail();
        }
    }

    @Test
    public void TestURL() {
        String expect = "rtmp://publish-rtmp.test.com/" + hubName + "/key?e=";
	     // 生成 RTMP 推流地址
	     // publishDomain: 与直播空间绑定的 RTMP 推流域名，可以在 portal.qiniu.com 上绑定
	     // streamKey: 流名，流不需要事先存在，推流会自动创建流
	     // mac: 授权信息
	     // expireAfterSeconds: 生成的推流地址的有效时间
        //publishURL := pili.RTMPPublishURL(publishDomain, hub, streamKey, mac, expireAfterSeconds)
        
        String url = cli.RTMPPublishURL("publish-rtmp.test.com", hubName, "key", 3600);
        System.out.println(url);
        assertTrue(url.startsWith(expect));

        // 生成 RTMP 播放地址
        // rtmpDomain: 直播空间绑定的 RTMP 直播域名，可以在 portal.qiniu.com 上绑定
        //rtmpURL := pili.RTMPPlayURL(rtmpDomain, hubName, streamKey)
        expect = "rtmp://live-rtmp.test.com/" + hubName + "/key";
        url = cli.RTMPPlayURL("live-rtmp.test.com", hubName, "key");
        assertTrue(url.startsWith(expect));
	     
        // 生成 HLS 播放地址
	    // hlsDomain: 直播空间绑定的 HLS 直播域名，可以在 portal.qiniu.com 上绑定
	    // hlsURL := pili.HLSPlayURL(hlsDomain, hubName, streamKey)
        expect = "http://live-hls.test.com/" + hubName + "/key.m3u8";
        url = cli.HLSPlayURL("live-hls.test.com", hubName, "key");
        assertTrue(url.startsWith(expect));
	     
        // 生成 HDL 播放地址
	    // hdlDomain: 直播空间绑定的 HDL 直播域名，可以在 portal.qiniu.com 上绑定
	    // hdlURL := pili.HDLPlayURL(hdlDomain, hubName, streamKey)
        expect = "http://live-hdl.test.com/" + hubName + "/key.flv";
        url = cli.HDLPlayURL("live-hdl.test.com", hubName, "key");
        assertTrue(url.startsWith(expect));
	
	    // 生成直播封面地址
	    // snapshotDomain: 直播空间绑定的直播封面域名，可以在 portal.qiniu.com 上绑定
	    // snapshotURL := pili.SnapshotPlayURL(snapshotDomain, hubName, streamKey)
        expect = "http://live-snapshot.test.com/" + hubName + "/key.jpg";
        url = cli.SnapshotPlayURL("live-snapshot.test.com", hubName, "key");
        assertTrue(url.startsWith(expect));
    }
}
