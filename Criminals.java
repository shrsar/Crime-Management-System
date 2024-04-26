/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package crimemgmt;

import java.sql.Date;
/**
 *
 * @author hp
 */
public class Criminals{
    String CID, FID, NME, PLC;
    Date DOB, DOA;
    public Criminals(String CID, String FID, String NME, Date DOB, Date DOA, String PLC){
        this.CID=CID;
        this.FID=FID;
        this.NME=NME;
        this.DOB=DOB;
        this.DOA=DOA;
        this.PLC=PLC;
        getCID();
        getFID();
        getNME();
        getDOB();
        getDOA();
        getPLC();
    }
    
    public String getCID(){
        return CID;
    }
    
    public String getFID(){
        return FID;
    }
    
    public String getNME(){
        return NME;
    }
    
    public Date getDOB(){
        return DOB;
    }
    
    public Date getDOA(){
        return DOA;
    }
    
    public String getPLC(){
        return PLC;
    }
}
