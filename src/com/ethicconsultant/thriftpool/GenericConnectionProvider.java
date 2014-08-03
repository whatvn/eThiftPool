package com.ethicconsultant.thriftpool;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
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
public class GenericConnectionProvider implements ConnectionProvider {

    public static final Logger logger = LoggerFactory
            .getLogger(GenericConnectionProvider.class);
    private int conTimeOut;
    private boolean useFrame = false;
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
    private ObjectPool objectPool = null;
    private volatile static GenericConnectionProvider instance = null;

    /**
     *
     * @param host
     * @param port
     * @return instance of GenericConnection Manager 
     */
    public static GenericConnectionProvider getInstance(String host, int port) {
        if (instance == null) {
            synchronized (GenericConnectionProvider.class) {
                if (instance == null) {
                    instance = new GenericConnectionProvider(host, port);
                }
            }
        }
        return instance;
    }

    public GenericConnectionProvider(String host, int port) {
        objectPool = new GenericObjectPool();
        System.out.println("Initialize conntion pool to server: " + host + " Port: " + port);
        ((GenericObjectPool) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
        ((GenericObjectPool) objectPool).setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
        ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(
                host, port, conTimeOut);
        objectPool.setFactory(thriftPoolableObjectFactory);
    }

    private void destroy() {
        try {
            objectPool.close();
        } catch (Exception e) {
            throw new RuntimeException("error destroy()", e);
        }
    }

    @Override
    public TSocket getConnection() {

        try {
            return (TSocket) objectPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("error getConnection()", e);
        }
    }

    @Override
    public TFramedTransport getConnection(boolean useFrame) {
        try {
            return (TFramedTransport) objectPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("error getConnection()", e);
        }
    }

    @Override
    public void returnCon(TSocket socket) {
        try {
            objectPool.returnObject(socket);
        } catch (Exception e) {
            throw new RuntimeException("error returnCon()", e);
        }
    }

    @Override
    public void returnCon(TFramedTransport socket) {
        try {
            objectPool.returnObject(socket);
        } catch (Exception e) {
            throw new RuntimeException("error returnCon()", e);
        }
    }

    

    public int getConTimeOut() {
        return conTimeOut;
    }

    public void setConTimeOut(int conTimeOut) {
        this.conTimeOut = conTimeOut;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public ObjectPool getObjectPool() {
        return objectPool;
    }

    public void setObjectPool(ObjectPool objectPool) {
        this.objectPool = objectPool;
    }

    public boolean isUseFrame() {
        return useFrame;
    }

    public void setUseFrame(boolean useFrame) {
        this.useFrame = useFrame;
    }

}
