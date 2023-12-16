package ctu.fee.dsv.sem.cmdline;

public class NodeConfiguration {

    private final Integer id;
    private final String nodeName;

    private final String brokerHostname;

    private final String brokerUrl;

    private final String loginNodeName;

    private final Integer loginNodeId;

    private final HeartbeatLogsConfigEnum heartbeatLogsConfigEnum;

    private final String addressList;

    public NodeConfiguration(Integer id,
                             String nodeName,
                             String brokerHostname,
                             String brokerUrl,
                             String loginNodeName,
                             Integer loginNodeId,
                             HeartbeatLogsConfigEnum heartbeatLogsConfigEnum,
                             String addressList) {
        this.id = id;
        this.nodeName = nodeName;
        this.brokerHostname = brokerHostname;
        this.brokerUrl = brokerUrl;
        this.loginNodeName = loginNodeName;
        this.loginNodeId = loginNodeId;
        this.heartbeatLogsConfigEnum = heartbeatLogsConfigEnum;
        this.addressList = addressList;
    }

    public static NodeConfiguration createWithLocalhostIp(String[] cmdArgs)
    {
        return new NodeConfiguration(
                Integer.parseInt(cmdArgs[0]),
                cmdArgs[1],
                "localhost",
                "http://localhost/imq/tunnel",
                cmdArgs[3],
                Integer.parseInt(cmdArgs[2]),
                HeartbeatLogsConfigEnum.fromString(cmdArgs[4]),
                "localhost:7676"
        );
    }

    public static NodeConfiguration create(String[] cmdArgs)
    {
        return new NodeConfiguration(
                Integer.parseInt(cmdArgs[0]),
                cmdArgs[1],
                cmdArgs[4],
                "http://" + cmdArgs[4] + "/imq/tunnel",
                cmdArgs[3],
                Integer.parseInt(cmdArgs[2]),
                HeartbeatLogsConfigEnum.fromString(cmdArgs[5]),
                cmdArgs[4] + ":7676"
        );
    }

    public static NodeConfiguration createClusterConfiguration(String[] cmdArgs)
    {
        return new NodeConfiguration(
                Integer.parseInt(cmdArgs[0]),
                cmdArgs[1],
                cmdArgs[4],
                "http://" + cmdArgs[4] + "/imq/tunnel",
                cmdArgs[3],
                Integer.parseInt(cmdArgs[2]),
                HeartbeatLogsConfigEnum.fromString(cmdArgs[6]),
                cmdArgs[4] + ":7676," + cmdArgs[5] + ":7676"
        );
    }

    public Integer getId() {
        return id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getBrokerHostname() {
        return brokerHostname;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getLoginNodeName() {
        return loginNodeName;
    }

    public Integer getLoginNodeId() {
        return loginNodeId;
    }

    public HeartbeatLogsConfigEnum getHeartbeatLogsConfigEnum() {
        return heartbeatLogsConfigEnum;
    }

    public String getAddressList() {
        return addressList;
    }
}
