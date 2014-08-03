package com.ethicconsultant.thriftpool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hungnguyen
 * @version 0.1
 * @since JDK1.7
 */
public class ThriftPoolableObjectFactory implements PoolableObjectFactory {

    public static final Logger logger = LoggerFactory
            .getLogger(ThriftPoolableObjectFactory.class);
    private String serviceIP;
    private int servicePort;
    private int timeOut;

    /**
     *
     * @param serviceIP
     * @param servicePort
     * @param timeOut
     */
    public ThriftPoolableObjectFactory(String serviceIP, int servicePort,
            int timeOut) {
        this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.timeOut = timeOut;
    }

    @Override
    public void destroyObject(Object arg0) throws Exception {
        if (arg0 instanceof TSocket) {
            TSocket socket = (TSocket) arg0;
            if (socket.isOpen()) {
                socket.close();
            }
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    @Override
    public Object makeObject() throws Exception {
        try {
            TFramedTransport transport = new TFramedTransport(new TSocket(this.serviceIP,
                    this.servicePort, this.timeOut));
            transport.open();
            return transport;
        } catch (Exception e) {
            logger.error("error ThriftPoolableObjectFactory()", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateObject(Object arg0) {
        try {
            if (arg0 instanceof TSocket) {
                TSocket thriftSocket = (TSocket) arg0;
                if (thriftSocket.isOpen()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void passivateObject(Object arg0) throws Exception {
        // DO NOTHING
    }

    @Override
    public void activateObject(Object arg0) throws Exception {
        // DO NOTHING
    }

    public String getServiceIP() {
        return serviceIP;
    }

    public void setServiceIP(String serviceIP) {
        this.serviceIP = serviceIP;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
