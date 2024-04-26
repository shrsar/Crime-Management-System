/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package crimemgmt;

import java.sql.Date;

/**
 *
 * @author hp
 */public class Complaint {
    String FID, UID, CTYPE, STS, STN, GENDER, PHN;
    Date F_DATE, C_DATE;
    public Complaint(String FID, String UID, String CTYPE, String STS, String STN, Date F_DATE, Date C_DATE, String GENDER){
        this.FID=FID;
        this.UID=UID;
        this.CTYPE=CTYPE;
        this.STS=STS;
        this.STN=STN;
        this.F_DATE=F_DATE;
        this.C_DATE=C_DATE;
        this.GENDER=GENDER;
        getFID();
        getUID();
        getCTYPE();
        getSTS();
        getSTN();
        getF_DATE();
        getC_DATE();
        getGENDER();
    }
    
    public String getFID(){
        return FID;
    }
    
    public String getUID(){
        return UID;
    }
    
    public String getCTYPE(){
        return CTYPE;
    }
    
    public String getSTS(){
        return STS;
    }
    
    public String getSTN(){
        return STN;
    }
    
    public Date getF_DATE(){
        return F_DATE;
    }
    
    public Date getC_DATE(){
        return C_DATE;
    }
    
    public String getGENDER(){
        return GENDER;
    }
}
