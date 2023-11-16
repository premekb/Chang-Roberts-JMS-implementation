package ctu.fee.dsv.sem;

public class NodeAddress {

    private final String hostname;

    private final Integer port;

    public NodeAddress(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getPort() {
        return port;
    }
}
