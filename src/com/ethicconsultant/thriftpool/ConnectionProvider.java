package com.ethicconsultant.thriftpool;

import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
// *  
// *  author  hungnguyen 
// *  Version  0.1 
// *  Since  JDK1.7

public interface ConnectionProvider {

    // use plain TSocket
//    public TSocket getConnection();
//    public void returnCon(TSocket socket);

    // use Frame along with socket
    public TTransport getConnection(boolean useFrame);
    public void returnCon(TTransport socket);
}
