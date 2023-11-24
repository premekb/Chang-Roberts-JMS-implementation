package ctu.fee.dsv.sem.communication.util;

import ctu.fee.dsv.sem.Neighbours;

public class NeighboursEdgeCaseUtil {

    public static boolean isOneNodeConfig(Neighbours neighbours)
    {
        return (neighbours.next.equals(neighbours.prev) &&
                neighbours.prev.equals(neighbours.leader) &&
                neighbours.leader.equals(neighbours.nnext));
    }
    public static boolean isTwoNodesConfig(Neighbours neighbours)
    {
        if (isOneNodeConfig(neighbours))
        {
            return false;
        }

        if (neighbours.next.equals(neighbours.prev))
        {
            return true;
        }

        return false;
    }

    public static boolean isThreeNodesConfig(Neighbours neighbours)
    {
        if (isOneNodeConfig(neighbours))
        {
            return false;
        }

        if (isTwoNodesConfig(neighbours))
        {
            return false;
        }

        if (neighbours.prev.equals(neighbours.nnext))
        {
            return true;
        }

        return false;
    }
}
