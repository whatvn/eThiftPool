/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThriftTest.thrift;

import com.ethicconsultant.thriftpool.GenericConnectionProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
/**
 *
 * @author hungnguyen
 */
public class Test {

    private static TFramedTransport connection;

    public static int Test() {
        try {
            GenericConnectionProvider genericConnectionProvider =  GenericConnectionProvider.getInstance("127.0.0.1", 9999);
            connection = genericConnectionProvider.getConnection(true);
            TProtocol protocol = new TBinaryProtocol(connection);
            PlusMe.Client client = new PlusMe.Client(protocol);
            return client.plusMe(1, 2);
        } catch (TException ex) {
            connection = null;
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
//        } finally {
//            if (connection != null) {
//                genericConnectionProvider.returnCon(connection);
//            }
        }

    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i <= 2; i++) {
                System.out.println(Test());
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
