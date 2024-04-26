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
public class Missing {
    String FID, MPLC, PSTN;
    Date MDATE;
    
    public Missing(String FID, String MPLC, Date MDATE, String PSTN){
        this.FID=FID;
        this.MPLC=MPLC;
        this.MDATE=MDATE;
        this.PSTN=PSTN;
        getFID();
        getMPLC();
        getMDATE();
        getPSTN();
    }
    
    public String getFID(){
        return FID;
    }
    
    public String getMPLC(){
        return MPLC;
    }
    
    public Date getMDATE(){
        return MDATE;
    }
    
    public String getPSTN(){
        return PSTN;
    }
}
