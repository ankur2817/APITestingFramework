package rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestIP {

    public static void main(String[] args) {

	try {
	    System.out.println("http://" + InetAddress.getLocalHost().getHostAddress()
		    + ":8080/job/APITestingFramework/Extent_20Reports/");
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}
    }
}
