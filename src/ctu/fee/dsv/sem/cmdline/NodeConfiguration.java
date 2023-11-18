package ctu.fee.dsv.sem.cmdline;

public class NodeConfiguration {

    private final Integer id;
    private final String nodeName;

    private final String brokerHostname;

    private final String brokerUrl;

    private final String loginNodeName;

    private final Integer loginNodeId;

    public NodeConfiguration(Integer id, String nodeName, String brokerHostname, String brokerUrl, String loginNodeName, Integer loginNodeId) {
        this.id = id;
        this.nodeName = nodeName;
        this.brokerHostname = brokerHostname;
        this.brokerUrl = brokerUrl;
        this.loginNodeName = loginNodeName;
        this.loginNodeId = loginNodeId;
    }

    public static NodeConfiguration createWithLocalhostIp(String[] cmdArgs)
    {
        return new NodeConfiguration(
                Integer.parseInt(cmdArgs[0]),
                cmdArgs[1],
                "localhost",
                "http://localhost/imq/tunnel",
                cmdArgs[3],
                Integer.parseInt(cmdArgs[2])
        );
    }

    public static NodeConfiguration create(String[] cmdArgs)
    {
        return new NodeConfiguration(
                Integer.parseInt(cmdArgs[0]),
                cmdArgs[1],
                cmdArgs[2],
                "http://" + cmdArgs[2] + "/imq/tunnel",
                cmdArgs[4],
                Integer.parseInt(cmdArgs[3])
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
}
