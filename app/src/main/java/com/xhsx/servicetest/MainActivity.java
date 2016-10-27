package com.xhsx.servicetest;

import android.app.Activity;
import android.os.Bundle;

import com.xhsx.service.core.PollingService;
import com.xhsx.service.core.PollingServiceConfig;
import com.xhsx.service.core.PollingUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PollingServiceConfig pollingServiceConfig = new PollingServiceConfig.Builder(this).action(PollingService.ACTION)
                .cls(PollingService.class).passWord("123456").userName("05fd50e477d043a4b56b6674cc373608_3").seconds(2).build();
        PollingUtils.startPollingService(pollingServiceConfig);


    }
}
