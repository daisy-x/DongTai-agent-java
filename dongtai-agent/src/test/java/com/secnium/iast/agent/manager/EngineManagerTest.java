package com.secnium.iast.agent.manager;

import com.secnium.iast.agent.Agent;
import com.secnium.iast.agent.AttachLauncher;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import org.junit.Test;

public class EngineManagerTest {

    @Test
    public void download() {
        String token = "a6573d3a-7054-4235-baca-8261b6da0fbd";
        token = "e7509bf7-e44f-4e1f-8e25-5079e2540c63";
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        EngineManager engineManager = EngineManager.getInstance(null, "", runtimeMXBean.getName().split("@")[0]);
        engineManager.updateEnginePackage();
    }

    @Test
    public void install() {
        String token = "a6573d3a-7054-4235-baca-8261b6da0fbd";
        token = "e7509bf7-e44f-4e1f-8e25-5079e2540c63";
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String pid = runtimeMXBean.getName().split("@")[0];
        EngineManager engineManager = EngineManager.getInstance(null, "", runtimeMXBean.getName().split("@")[0]);
        Agent.appendToolsPath();
        try {
            AttachLauncher.attach(pid, "");
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }
}
