# eThriftpool 

Connection pool for  [facebook-thrift](https://github.com/apache/thrift)

eThriftPool can be used with any thrift client in your application, it use plain TSocket or TFrameTransport instead off pooling connection with pre-defined protocol.



###### Prerequisites:

* Java 1.7 (1.6 is not tested)
* Apache Common pool 
* Thrift library

# Usage: For example, you define your thrift application like this:
    
    service PlusMe {
        i32 plusMe(1:i32 a1, 2: i32 a2),
    }

Save to file name plusme.thrift. Gen java code using command:
    $ thrift --gen java  plusme.thrift

Import gen files to your netbeans/eclipse application and use it as following


## Java Code 

    import com.ethicconsultant.thriftpool.GenericConnectionProvider;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import org.apache.thrift.TException;
    import org.apache.thrift.protocol.TBinaryProtocol;
    import org.apache.thrift.protocol.TProtocol;
    import org.apache.thrift.transport.TFramedTransport;
    import org.apache.thrift.transport.TTransport;

    public class Test {

        private static TTransport connection;

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


##### License
Do whatever you want

