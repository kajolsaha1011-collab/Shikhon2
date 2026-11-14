package com.abhi.shikhon;

import android.os.Bundle;
import android.webkit.WebView;
import com.getcapacitor.BridgeActivity;

/**
 * শিখন ভল্ট — মূল Activity।
 * ভিডিও চলার সময় অ্যাপ মিনিমাইজ বা স্ক্রিন বন্ধ করলেও WebView যেন সক্রিয় থাকে,
 * সেজন্য onPause/onStop-এর পর WebView আবার "resume" করা হয়।
 */
public class MainActivity extends BridgeActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(KeepAlivePlugin.class);
        super.onCreate(savedInstanceState);
    }

    private void keepWebViewAlive() {
        if (!KeepAlivePlugin.active) return; // ভিডিও না চললে স্বাভাবিক আচরণ, ব্যাটারি বাঁচে
        try {
            WebView w = getBridge().getWebView();
            if (w != null) {
                w.onResume();
                w.resumeTimers();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        keepWebViewAlive();
    }

    @Override
    public void onStop() {
        super.onStop();
        keepWebViewAlive();
    }
}
