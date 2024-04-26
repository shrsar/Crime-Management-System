/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package crimemgmt;

/**
 *
 * @author hp
 */
public class Complainants {String UID, UNAME, PHONE;
    public Complainants(String UID, String UNAME, String PHONE){
        this.UID=UID;
        this.UNAME=UNAME;
        this.PHONE=PHONE;
        getUID();
        getUNAME();
        getPHONE();
    }
    
    public String getUID(){
        return UID;
    }
    
    public String getUNAME(){
        return UNAME;
    }
    
    public String getPHONE(){
        return PHONE;
    }
}
