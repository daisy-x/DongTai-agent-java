package com.secnium.iast.agent.monitor;

import com.secnium.iast.agent.*;
import com.secnium.iast.agent.manager.EngineManager;
import com.secnium.iast.agent.report.AgentRegisterReport;
import com.secnium.iast.agent.util.http.HttpClientUtils;
import com.secnium.iast.log.DongTaiLog;
import org.json.JSONObject;

/**
 * @author dongzhiyong@huoxian.cn
 */
public class EngineMonitor implements IMonitor {
    private final EngineManager engineManager;
    public static Boolean isCoreRegisterStart = false;

    public EngineMonitor(EngineManager engineManager) {
        this.engineManager = engineManager;
    }

    @Override
    public void check() {

        String status = checkForStatus();

        if ("notcmd".equals(status)){
            return;
        }

        if ("coreRegisterStart".equals(status)) {
            isCoreRegisterStart = true;
            startEngine();
        }else if ("coreStop".equals(status) && isCoreRegisterStart) {
            DongTaiLog.info("engine stop");
            engineManager.stop();
        } else if ("coreStart".equals(status) && isCoreRegisterStart) {
            DongTaiLog.info("engine start");
            engineManager.start();
        }
    }

    private String checkForStatus() {
        try {
            String respRaw = String.valueOf(HttpClientUtils.sendGet(Constant.API_ENGINE_ACTION, "agentId", String.valueOf(AgentRegisterReport.getAgentFlag())));
            if (respRaw != null && !respRaw.isEmpty()) {
                JSONObject resp = new JSONObject(respRaw);
                return resp.get("data").toString();
            }
        } catch (Exception e) {
            return "other";
        }
        return "other";
    }

    public void startEngine() {
        boolean status = engineManager.downloadEnginePackage();
        status = status && engineManager.install();
        status = status && engineManager.start();
        if (!status) {
            DongTaiLog.info("DongTai IAST started failure");
        }
    }
}
