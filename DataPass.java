/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package crimemgmt;

/**
 *
 * @author hp
 */
public class DataPass {
    private static final DataPass instance=new DataPass();
    
    private String phone, uid, stn, pid, otp;
    
    private DataPass(){}
    
    public static DataPass getInstance(){
        return instance;
    }
    
    public String getotp(){
        return otp;
    }
    
    public void setotp(String otp){
        this.otp=otp;
    }
    
    public String getstn(){
        return stn;
    }
    
    public void setstn(String stn){
        this.stn=stn;
    }
    
    public String getphone(){
        return phone;
    }
    
    public void setphone(String phone){
        this.phone=phone;
    }
    
    public String getuid(){
        return uid;
    }
    
    public void setuid(String uid){
        this.uid=uid;
    }
    
    public String getpid(){
        return pid;
    }
    
    public void setpid(String pid){
        this.pid=pid;
    }
}
