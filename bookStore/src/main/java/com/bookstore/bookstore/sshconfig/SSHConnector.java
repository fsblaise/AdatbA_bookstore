package com.bookstore.bookstore.sshconfig;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnector {
    private final static int LOCAl_PORT = 1521;
    private final static int REMOTE_PORT = 1521;
    private final static int SSH_REMOTE_PORT = 22;
    private final static String SSH_REMOTE_SERVER = "linux.inf.u-szeged.hu";
    private final static String SSH_USERNAME = ""; //H-s identifier
    private final static String SSH_PASSWORD = ""; //H-s password

    private final static String MYSQL_REMOTE_SERVER = "10.2.1.49";

    private Session session; //represents each ssh session

    public void closeSSH() {
        session.disconnect();
    }

    public SSHConnector() throws Throwable {
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        JSch jsch = new JSch();
        session = jsch.getSession(SSH_USERNAME, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        session.setPassword(SSH_PASSWORD);
        session.setConfig(config);
        session.connect(); //ssh connection established!
        System.out.println("Connected to ssh");

        //by security policy, you must connect through a fowarded port
        session.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);

    }
}
