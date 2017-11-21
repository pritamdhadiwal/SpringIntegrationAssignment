package com.assignment.springintegration.file;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

public class SFTPTest {

    public static void main(String[] args) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession("username", "mywebsite.com", 22); //default port is 22
            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            session.setPassword("123456".getBytes());
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("Connected");
        } catch (JSchException e) {
            e.printStackTrace(System.out);
        } catch (Exception e){
            e.printStackTrace(System.out);
        } finally{
            session.disconnect();
            System.out.println("Disconnected");
        }
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {

        @Override
        public String getPassphrase() {
            return null;
        }
        @Override
        public String getPassword() {
            return null;
        }
        @Override
        public boolean promptPassphrase(String arg0) {
            return false;
        }
        @Override
        public boolean promptPassword(String arg0) {
            return false;
        }
        @Override
        public boolean promptYesNo(String arg0) {
            return false;
        }
        @Override
        public void showMessage(String arg0) {
        }
        @Override
        public String[] promptKeyboardInteractive(String arg0, String arg1,
                String arg2, String[] arg3, boolean[] arg4) {
            return null;
        }
    }
}
