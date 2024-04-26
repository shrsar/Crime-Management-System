/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package crimemgmt;

/**
 *
 * @author hp
 */
public class Wanted {
    String FID, CTYPE, STN;
    public Wanted(String FID, String CTYPE, String STN){
        this.FID=FID;
        this.CTYPE=CTYPE;
        this.STN=STN;
        getFID();
        getCTYPE();
        getSTN();
    }
    
    public String getFID(){
        return FID;
    }
    
    public String getCTYPE(){
        return CTYPE;
    }
    
    public String getSTN(){
        return STN;
    }
}
