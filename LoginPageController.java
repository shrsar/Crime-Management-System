/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package crimemgmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class LoginPageController implements Initializable {
    
    @FXML
    private LineChart<?, ?> curstncrmline;
    
    @FXML
    private BarChart<?, ?> vicgenbar, allstncrmbar;
    
    @FXML
    private Label polstn, cmpid, bcklbl, totcrm, stncrm;
    
    @FXML
    private Button compdashboardbtn, compreportbtn, comptrackbtn, complodgerefbtn, comptrackrefbtn, loginbtn, wantedbtn, missingbtn, forgotpswdbtn;
    
    @FXML
    private Button poldashboardbtn, polstatusbtn, poltrackbtn, polcrimbtn, polloginbtn, logoutbtn, readbtn, custimprtbtn, addcrmimprtbtn, polstsimgimprtbtn, addwntdbtn, showusrsbtn;
    
    @FXML
    private Button addwntd_imprtbtn, msbtn, msngimprtbtn, wntd_ref, wntdref;
    
    @FXML
    private AnchorPane compdashboardpane, compreportpane, comptrackpane, sgnuppane, dashboardpane, statuspane, casespane, crimpane, addwntdpane, shwusrpane, mspane;
    
    @FXML
    private PasswordField pswd, polpswd;
    
    @FXML
    private TextField sgnup_uname, usrnm, polusrnm, addcrmnme, chkstsfirid;

    @FXML
    private TextField sgnup_otp;

    @FXML
    private TextField sgnup_pswd;

    @FXML
    private TextField sgnup_seckey, uid1, seckey, npswd, otpval;

    @FXML
    private TextField comp_plc, addcrmfid, addcrmplc, addcrmcid, polstsfirid;

    @FXML
    private TextField sgnup_phn, comptrack_fid, addwntd_fid, addms_firid, addms_plc;

    @FXML
    private ImageView sgnup_img, custreportimg, comptrack_img, addcrm_img, polstsimg, chkstsimg, addwntd_img, msimg, addwntd_img1, msimg1;
    
    @FXML
    private ComboBox<?> comp_crime, comp_station, addcrmsts, vicgen, sgnup_city;
    
    @FXML
    private DatePicker comp_date, addcrmdob, addcrmdoa, addms_date;
    
    @FXML
    private TextArea comp_descrpt, comptrack_descrpt, polstsprg, chkstsdes;
    
    @FXML
    private TableView<Complaint> comp_table, polcomp_table, pol_table;
    
    @FXML
    private TableView<Criminals> addcrimtable;
    
    @FXML
    private TableView<Complainants> usrlst_table;
    
    @FXML
    private TableView<Wanted> addwntd_table;
    
    @FXML
    private TableView<Missing> addms_table;
    
    @FXML
    private TableColumn<?, ?> comp_fid, polcomp_fid, comp_crme, polcomp_crme, comp_sts, polcomp_gndr, comp_cdate, polcomp_cdate, comp_fdate, polcomp_fdate, comp_stn, pol_fid, pol_crme, pol_sts, pol_cdate, pol_fdate, pol_stn, addcrm_cid, addcrm_doa, addcrm_dob, addcrm_plc, addcrm_fid, addcrm_nme, polcomp_cmp, usrlst_uid, usrlst_uname, usrlst_phn;

    @FXML
    private TableColumn<?, ?> addwntd_stn, addwntd_crme, addwntd_fir, polcomp_sts, addms_mplc, addms_ms, addms_fid, addms_pstn, comp_gdr;
    
    private Alert alert;
    private Image img;
    
    private final String[] crimesList={"Hit and run", "Robbery", "Murder", "Deceit", "Missing"};
    private final String[] stationsList={"Prayagraj", "Lucknow", "Kanpur", "Ghaziabad"};
    private final String[] genderList={"Male", "Female", "Non Living"};
    
    public void sendotpbtnOnAction() throws Exception{
        String uid=uid1.getText(), pwd=npswd.getText(), skey=seckey.getText(), phn="", sk="";
        String url1="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT * FROM LOGIN WHERE UID='"+uid+"';";
        int otp=-1;
        try{
            Connection conn=DriverManager.getConnection(url1, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                phn=rs.getString("PHONE");
                sk=rs.getString("SECKEY");
            }
            if(phn.equals("") || uid.isBlank() || pwd.isBlank() || skey.isBlank() || !sk.equals(skey)){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Credentials!");
                alert.showAndWait();
            }
            else{
                String myURI = "https://api.bulksms.com/v1/messages";

                // change these values to match your own account
                String myUsername = "varshi_018";
                String myPassword = "#varshi123";

                Random random=new Random();
                int min=100000;
                int max=999999;
                otp=random.nextInt((max-min)+min);
                phn="+91"+phn;
                // the details of the message we want to send
                String myData = "{to: \"" + phn + "\", body: \"Your App code is:" + otp + "\"}";

                // if your message does not contain unicode, the "encoding" is not required:
                // String myData = "{to: \"1111111\", body: \"Hello Mr. Smith!\"}";

                // build the request based on the supplied settings
                URL url = new URL(myURI);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setDoOutput(true);

                // supply the credentials
                String authStr = myUsername + ":" + myPassword;
                String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
                request.setRequestProperty("Authorization", "Basic " + authEncoded);

                // we want to use HTTP POST
                request.setRequestMethod("POST");
                request.setRequestProperty( "Content-Type", "application/json");

                // write the data to the request
                OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
                out.write(myData);
                out.close();

                // try ... catch to handle errors nicely
                try {
                  // make the call to the API
                  InputStream response = request.getInputStream();
                  BufferedReader in = new BufferedReader(new InputStreamReader(response));
                  String replyText;
                  while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                  }
                  in.close();
                } catch (IOException ex) {
                  System.out.println("An error occurred:" + ex.getMessage());
                  BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
                  // print the detail that comes with the error
                  String replyText;
                  while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                  }
                  in.close();
                }
                request.disconnect();
                    }
            DataPass dp=DataPass.getInstance();
            dp.setotp(Integer.toString(otp));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void sendotpbtnOnAction1() throws Exception{
        String url1="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        int otp=-1;
        try{
            if(sgnup_uname.getText().isBlank() || sgnup_pswd.getText().isBlank() || ((String)sgnup_city.getSelectionModel().getSelectedItem()).equals("") || sgnup_seckey.getText().isBlank() || sgnup_phn.getText().isBlank()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Empty Field(s)!");
                alert.showAndWait();
            }
            else{
                String phn=sgnup_phn.getText();
                String myURI = "https://api.bulksms.com/v1/messages";

                // change these values to match your own account
                String myUsername = "varshi_018";
                String myPassword = "#varshi123";

                Random random=new Random();
                int min=100000;
                int max=999999;
                otp=random.nextInt((max-min)+min);
                phn="+91"+phn;
                // the details of the message we want to send
                String myData = "{to: \"" + phn + "\", body: \"Your App code is:" + otp + "\"}";

                // if your message does not contain unicode, the "encoding" is not required:
                // String myData = "{to: \"1111111\", body: \"Hello Mr. Smith!\"}";

                // build the request based on the supplied settings
                URL url = new URL(myURI);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.setDoOutput(true);

                // supply the credentials
                String authStr = myUsername + ":" + myPassword;
                String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
                request.setRequestProperty("Authorization", "Basic " + authEncoded);

                // we want to use HTTP POST
                request.setRequestMethod("POST");
                request.setRequestProperty( "Content-Type", "application/json");

                // write the data to the request
                OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
                out.write(myData);
                out.close();

                // try ... catch to handle errors nicely
                try {
                  // make the call to the API
                  InputStream response = request.getInputStream();
                  BufferedReader in = new BufferedReader(new InputStreamReader(response));
                  String replyText;
                  while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                  }
                  in.close();
                } catch (IOException ex) {
                  System.out.println("An error occurred:" + ex.getMessage());
                  BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
                  // print the detail that comes with the error
                  String replyText;
                  while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                  }
                  in.close();
                }
                request.disconnect();
                    }
            DataPass dp=DataPass.getInstance();
            dp.setotp(Integer.toString(otp));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
    public void displaydashbrd() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query1="SELECT COUNT(FID) FROM FIR WHERE F_DATE='"+java.sql.Date.valueOf(java.time.LocalDate.now())+"' AND PID='"+dp.getpid()+"';";
        String query2="SELECT COUNT(FID) FROM FIR WHERE F_DATE='"+java.sql.Date.valueOf(java.time.LocalDate.now())+"';";
        try{
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps1, ps2;
            ps1=conn.prepareStatement(query1);
            ps2=conn.prepareStatement(query2);
            ResultSet rs1, rs2;
            rs1=ps1.executeQuery();
            rs2=ps2.executeQuery();
            while(rs1.next()){
                stncrm.setText(rs1.getString(1));
            }
            while(rs2.next()){
                totcrm.setText(rs2.getString(1));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String plstn(String stn){
        switch(stn){
            case "Prayagraj" -> {
                return "P1";
            }
            case "Lucknow" -> {
                return "P2";
            }
            case "Kanpur" -> {
                return "P3";
            }
            case "Ghaziabad" -> {
                return "P4";
            }
        }
        return null;
    }
    
    public String gtplstn(String pid){
        switch(pid){
            case "P1" -> {
                return "Prayagraj";
            }
            case "P2" -> {
                return "Lucknow";
            }
            case "P3" -> {
                return "Kanpur";
            }
            case "P4" -> {
                return "Ghaziabad";
            }
        }
        return null;
    }
    
    public void displaylinechart(){
        curstncrmline.getData().clear();
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="SELECT F_DATE, COUNT(FID) FROM FIR WHERE PID='"+dp.getpid()+"' GROUP BY F_DATE;";
        try{
            XYChart.Series chart=new XYChart.Series<>();
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            curstncrmline.getData().add(chart);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void displaybarchart1() throws Exception{
        vicgenbar.getData().clear();
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT GENDER, COUNT(FID) FROM FIR GROUP BY GENDER;";
        try{
            XYChart.Series chart=new XYChart.Series<>();
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            vicgenbar.getData().add(chart);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void displaybarchart2() throws Exception{
        allstncrmbar.getData().clear();
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT STN, COUNT(FID) FROM FIR GROUP BY STN;";
        try{
            XYChart.Series chart=new XYChart.Series<>();
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getInt(2)));
            }
            allstncrmbar.getData().add(chart);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void crimesList(){
        List<String> listS=new ArrayList<>();
        for(String data:crimesList){
            listS.add(data);
        }
        ObservableList listData=FXCollections.observableArrayList(listS);
        comp_crime.setItems(listData);
    }
    
    public void stationsList(){
        List<String> listS=new ArrayList<>();
        for(String data:stationsList){
            listS.add(data);
        }
        ObservableList listData=FXCollections.observableArrayList(listS);
        comp_station.setItems(listData);
    }
    
    public void citiesList(){
        List<String> listS=new ArrayList<>();
        for(String data:stationsList){
            listS.add(data);
        }
        ObservableList listData=FXCollections.observableArrayList(listS);
        sgnup_city.setItems(listData);
    }
    
    public void genderList(){
        List<String> listS=new ArrayList<>();
        for(String data:genderList){
            listS.add(data);
        }
        ObservableList listData=FXCollections.observableArrayList(listS);
        vicgen.setItems(listData);
    }
    
    private ObservableList<Complaint> complaintlistdata, polcomplaintlistdata;
    
    public void imprtbtnOnAction(ActionEvent e) throws Exception{
        FileChooser open=new FileChooser();
        if(e.getSource()==custimprtbtn){
            File file=open.showOpenDialog(compreportpane.getScene().getWindow());
            if(file!=null){
                data.path=file.getAbsolutePath();
                img=new Image(file.toURI().toString(), 200, 200, false, true);
                custreportimg.setImage(img);
            }
        }
        if(e.getSource()==addcrmimprtbtn){
            File file=open.showOpenDialog(crimpane.getScene().getWindow());
            if(file!=null){
                data.path=file.getAbsolutePath();
                img=new Image(file.toURI().toString(), 200, 200, false, true);
                addcrm_img.setImage(img);
            }
        }
        if(e.getSource()==polstsimgimprtbtn){
            File file=open.showOpenDialog(statuspane.getScene().getWindow());
            if(file!=null){
                data.path=file.getAbsolutePath();
                img=new Image(file.toURI().toString(), 200, 200, false, true);
                polstsimg.setImage(img);
            }
        }
        if(e.getSource()==addwntd_imprtbtn){
            File file=open.showOpenDialog(addwntdpane.getScene().getWindow());
            if(file!=null){
                data.path=file.getAbsolutePath();
                img=new Image(file.toURI().toString(), 200, 200, false, true);
                addwntd_img.setImage(img);
            }
        }
        if(e.getSource()==msngimprtbtn){
            File file=open.showOpenDialog(mspane.getScene().getWindow());
            if(file!=null){
                data.path=file.getAbsolutePath();
                img=new Image(file.toURI().toString(), 200, 200, false, true);
                msimg.setImage(img);
            }
        }
    }
    
    public void wntdshwbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT * FROM WANTED WHERE FID='"+addwntd_fid.getText()+"';";
        if(addwntd_fid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String path="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                }
                if(path.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Field!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    addwntd_img.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void wntdshwbtnOnAction1() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT * FROM WANTED WHERE FID='"+addwntd_fid.getText()+"';";
        if(addwntd_fid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String path="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                }
                if(path.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Field!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    addwntd_img1.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void stsgetbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"' AND STS='In Progress';";
        if(polstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String descrpt="", path="";
                while(rs.next()){
                    if(rs.getString("FID").equals(polstsfirid.getText())){
                        descrpt=rs.getString("PRFDESCRPT");
                        path="File:"+rs.getString("PRFIMG");
                    }
                }
                if(descrpt.equals("") || path.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Field!");
                    alert.showAndWait();
                }
                else{
                    polstsprg.setText(descrpt);
                    Image image=new Image(path, 200, 200, false, true);
                    polstsimg.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void polstssetdescrpt() throws Exception{
        String descrpt=polstsprg.getText();
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="UPDATE FIR SET PRFDESCRPT='"+descrpt+"' WHERE FID='"+polstsfirid.getText()+"' AND STS='In Progress';";
        if(polstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else if(polstsprg.getText().isBlank()){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Description remained as it was!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                Statement stmnt=conn.createStatement();
                int rows=stmnt.executeUpdate(query);
                if(rows>0){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Description updated!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void polstssetprf() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        if(polstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                Statement stmnt=conn.createStatement();
                String path=data.path;
                if(path.equals("")){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Proof kept as it was!");
                    alert.showAndWait();
                }
                else{
                    path=path.replace("\\", "\\\\");
                    String query="UPDATE FIR SET PRFIMG='"+path+"' WHERE FID='"+polstsfirid.getText()+"' AND STS='In Progress';";
                    int rows=stmnt.executeUpdate(query);
                    if(rows>0){
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Proof updated!");
                        alert.showAndWait();
                    }
                    else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Wrong entries!");
                        alert.showAndWait();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void unreslvbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="UPDATE FIR SET STS='In Progress' WHERE FID='"+chkstsfirid.getText()+"';";
        String chkquery="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"' AND FID='"+chkstsfirid.getText()+"';";
        if(chkstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(chkquery);
                ResultSet rs=ps.executeQuery();
                String path="", descrpt="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                    descrpt=rs.getString("DESCRPT");
                }
                if(path.equals("") || descrpt.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Statement stmnt=conn.createStatement();
                    stmnt.executeUpdate(query);
                    polshowfirtrack();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Reopened!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void reslvbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="UPDATE FIR SET STS='Solved' WHERE FID='"+chkstsfirid.getText()+"';";
        String chkquery="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"' AND FID='"+chkstsfirid.getText()+"';";
        if(chkstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(chkquery);
                ResultSet rs=ps.executeQuery();
                String path="", descrpt="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                    descrpt=rs.getString("DESCRPT");
                }
                if(path.equals("") || descrpt.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Statement stmnt=conn.createStatement();
                    stmnt.executeUpdate(query);
                    polshowfirtrack();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Resolved!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void insmsngbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String chk_query="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"' AND FID='"+addms_firid.getText()+"' AND CTYPE='Missing';";
        if(addms_firid.getText().isBlank() || addms_plc.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(chk_query);
                ResultSet rs=ps.executeQuery();
                String fid="";
                while(rs.next()){
                    fid=rs.getString("FID");
                }
                if(!fid.equals("")){
                    String query="INSERT INTO MISSING VALUES(?, ?, ?, ?, ?)";
                    ps=conn.prepareStatement(query);
                    ps.setString(1, addms_firid.getText());
                    ps.setDate(2, java.sql.Date.valueOf(addms_date.getValue()));
                    ps.setString(3, addms_plc.getText());
                    String path=data.path;
                    path=path.replace("\\", "\\\\");
                    ps.setString(4, path);
                    ps.setString(5, dp.getpid());
                    ps.execute();
                    showmsng();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Record Added!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void delmsngbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String chk_query="SELECT * FROM MISSING WHERE PID='"+dp.getpid()+"' AND FID='"+addms_firid.getText()+"';";
        if(addms_firid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(chk_query);
                ResultSet rs=ps.executeQuery();
                String fid="";
                while(rs.next()){
                    fid=rs.getString("FID");
                }
                if(!fid.equals("")){
                    String query="DELETE FROM MISSING WHERE FID='"+addms_firid.getText()+"';";
                    Statement stmnt=conn.createStatement();
                    stmnt.executeUpdate(query);
                    showmsng();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Record Deleted!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void msngrefbtnOnAction() throws Exception{
        showmsng();
        addms_firid.setText("");
        addms_date.setValue(null);
        addms_plc.setText("");
        msimg.setImage(null);
    }
    
    public void shwmsngimgbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT * FROM MISSING WHERE FID='"+addms_firid.getText()+"';";
        if(addms_firid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String fid="", path="";
                while(rs.next()){
                    fid=rs.getString("FID");
                    path="File:"+rs.getString("IMG");
                }
                if(fid.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    msimg.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void shwmsngimgbtnOnAction1() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="SELECT * FROM MISSING WHERE FID='"+addms_firid.getText()+"';";
        if(addms_firid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String fid="", path="";
                while(rs.next()){
                    fid=rs.getString("FID");
                    path="File:"+rs.getString("IMG");
                }
                if(fid.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    msimg1.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void polshwbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String query="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"' AND FID='"+chkstsfirid.getText()+"';";
        if(chkstsfirid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String path="", descrpt="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                    descrpt=rs.getString("DESCRPT");
                }
                if(path.equals("") || descrpt.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    chkstsimg.setImage(image);
                    chkstsdes.setWrapText(true);
                    chkstsdes.setText(descrpt);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void addcrmbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="INSERT INTO CRIMINALS VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        if(addcrmcid.getText().isBlank() || addcrmfid.getText().isBlank() || addcrmnme.getText().isBlank() || addcrmdob.getValue()==null || addcrmdoa.getValue()==null || addcrmplc.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Field(s) empty!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ps.setString(1, addcrmfid.getText());
                ps.setString(2, addcrmnme.getText());
                ps.setDate(3, java.sql.Date.valueOf(addcrmdob.getValue()));
                ps.setDate(4, java.sql.Date.valueOf(addcrmdoa.getValue()));
                ps.setString(5, addcrmplc.getText());
                String path=data.path;
                path=path.replace("\\", "\\\\");
                ps.setString(6, path);
                ps.setString(7, addcrmcid.getText());
                DataPass dp=DataPass.getInstance();
                ps.setString(8, dp.getpid());
                ps.execute();
                crmshowdata();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Added!");
                alert.showAndWait();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void delcrmbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        DataPass dp=DataPass.getInstance();
        String checkquery="UPDATE CRIMINALS SET CID='"+addcrmcid.getText()+"' WHERE PID='"+dp.getpid()+"';";
        String query="DELETE FROM CRIMINALS WHERE CID='"+addcrmcid.getText()+"';";
        try{
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            Statement stmnt=conn.createStatement();
            int rows=stmnt.executeUpdate(checkquery);
            if(rows>0){
                stmnt.executeUpdate(query);
                crmshowdata();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Record deleted!");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Entries!");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void refcrmbtnOnAction() throws Exception{
        addcrmcid.setText("");
        addcrmfid.setText("");
        addcrmnme.setText("");
        addcrmdob.setValue(null);
        addcrmdoa.setValue(null);
        addcrmplc.setText("");
        addcrm_img.setImage(null);
    }
    
    public void showcrmsbtnOnAction() throws Exception{
        if(addcrmcid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Record doesn't exist!");
            alert.showAndWait();
        }
        else{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            String query="SELECT * FROM CRIMINALS WHERE CID='"+addcrmcid.getText()+"';";
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                String path="";
                while(rs.next()){
                    path="File:"+rs.getString("IMG");
                }
                if(path.equals("")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Record doesn't exist!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    addcrm_img.setImage(image);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public ObservableList<Complaint> polfirtracklist() throws Exception{
        ObservableList<Complaint> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            DataPass dp=DataPass.getInstance();
            String query="SELECT * FROM FIR WHERE PID='"+dp.getpid()+"';";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Complaint cmp;
            while(rs.next()){
                cmp=new Complaint(rs.getString("FID"), rs.getString("UID"), rs.getString("CTYPE"), rs.getString("STS"), rs.getString("STN"), rs.getDate("F_DATE"), rs.getDate("C_DATE"), rs.getString("GENDER"));
                listData.add(cmp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }            
        return listData;
    }
    
    private ObservableList<Complaint> polfirslistdata;
    
    public void polshowfirtrack() throws Exception{
        polfirslistdata=polfirtracklist();
        polcomp_fid.setCellValueFactory(new PropertyValueFactory<>("FID"));
        polcomp_gndr.setCellValueFactory(new PropertyValueFactory<>("GENDER"));
        polcomp_fdate.setCellValueFactory(new PropertyValueFactory<>("F_DATE"));
        polcomp_cdate.setCellValueFactory(new PropertyValueFactory<>("C_DATE"));
        polcomp_crme.setCellValueFactory(new PropertyValueFactory<>("CTYPE"));
        polcomp_cmp.setCellValueFactory(new PropertyValueFactory<>("UID"));
        polcomp_sts.setCellValueFactory(new PropertyValueFactory<>("STS"));
        polcomp_table.setItems(polfirslistdata);
    }
    
    public ObservableList<Wanted> wntdlist() throws Exception{
        ObservableList<Wanted> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            DataPass dp=DataPass.getInstance();
            String query="SELECT * FROM WANTED";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Wanted cmp;
            while(rs.next()){
                cmp=new Wanted(rs.getString("FID"), rs.getString("CTYPE"), rs.getString("STN"));
                listData.add(cmp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }            
        return listData;
    }
    
    private ObservableList<Wanted> wntdlistdata;
    
    public void showwntdlist() throws Exception{
        wntdlistdata=wntdlist();
        addwntd_fir.setCellValueFactory(new PropertyValueFactory<>("FID"));
        addwntd_crme.setCellValueFactory(new PropertyValueFactory<>("CTYPE"));
        addwntd_stn.setCellValueFactory(new PropertyValueFactory<>("STN"));
        addwntd_table.setItems(wntdlistdata);
    }
    
    public void inswntdbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="INSERT INTO WANTED VALUES(?, ?, ?, ?)";
        String ct=(String)comp_crime.getSelectionModel().getSelectedItem();
        if(addwntd_fid.getText().isBlank() || ct.equals("") || ct.equals("")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                DataPass dp=DataPass.getInstance();
                ps.setString(1, addwntd_fid.getText());
                ps.setString(2, ct);
                ps.setString(3, dp.getstn());
                String path=data.path;
                path=path.replace("\\", "\\\\");
                ps.setString(4, path);
                ps.execute();
                showwntdlist();
                addwntd_fid.setText("");
                comp_crime.setValue(null);
                addwntd_img.setImage(null);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Record Added!");
                alert.showAndWait();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void delwntdbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="DELETE FROM WANTED WHERE FID='"+addwntd_fid.getText()+"';";
        if(addwntd_fid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else{
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                Statement stmnt=conn.createStatement();
                int rows=stmnt.executeUpdate(query);
                if(rows>0){
                    showwntdlist();
                    addwntd_fid.setText("");
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Record Deleted!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Record doesn't exist!");
                    alert.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void updtwntdbtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String path=data.path;
        if(path==null){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Image kept as it was!");
            alert.showAndWait();
        }
        else{
            String query="UPDATE WANTED SET FID='"+addwntd_fid.getText()+"' WHERE FID='"+addwntd_fid.getText()+"';";
            if(addwntd_fid.getText().isBlank()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Empty Field(s)!");
                alert.showAndWait();
            }
            else{
                try{
                    Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                    Statement stmnt=conn.createStatement();
                    int rows=stmnt.executeUpdate(query);
                    if(rows>0){
                        path=data.path;
                        path=path.replace("\\", "\\\\");
                        query="UPDATE WANTED SET IMG='"+path+"' WHERE FID='"+addwntd_fid.getText()+"';";
                        stmnt.executeUpdate(query);
                        showwntdlist();
                        addwntd_fid.setText("");
                        addwntd_img.setImage(null);
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Record Updated!");
                        alert.showAndWait();
                    }
                    else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Record doesn't exist!");
                        alert.showAndWait();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void wntdrefbtnOnAction() throws Exception{
        showwntdlist();
        addwntd_fid.setText("");
        comp_crime.setValue(null);
        addwntd_img.setImage(null);
    }
    
    public void casesrefbtnOnAction() throws Exception{
        polshowfirtrack();
        chkstsfirid.setText("");
        chkstsimg.setImage(null);
        chkstsdes.setText("");
    }
    
    public ObservableList<Missing> msnglist() throws Exception{
        ObservableList<Missing> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            String query="SELECT * FROM MISSING";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Missing cmp;
            while(rs.next()){
                cmp=new Missing(rs.getString("FID"), rs.getString("MPLC"), rs.getDate("MDATE"), gtplstn(rs.getString("PID")));
                listData.add(cmp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }            
        return listData;
    }
    
    private ObservableList<Missing> msnglistdata;
    
    public void showmsng() throws Exception{
        msnglistdata=msnglist();
        addms_fid.setCellValueFactory(new PropertyValueFactory<>("FID"));
        addms_ms.setCellValueFactory(new PropertyValueFactory<>("MDATE"));
        addms_mplc.setCellValueFactory(new PropertyValueFactory<>("MPLC"));
        addms_pstn.setCellValueFactory(new PropertyValueFactory<>("PSTN"));
        addms_table.setItems(msnglistdata);
    }
    
    public ObservableList<Complaint> firtracklist() throws Exception{
        ObservableList<Complaint> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            DataPass dp=DataPass.getInstance();
            String query="SELECT * FROM FIR WHERE UID='"+dp.getuid()+"';";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Complaint cmp;
            while(rs.next()){
                cmp=new Complaint(rs.getString("FID"), rs.getString("UID"), rs.getString("CTYPE"), rs.getString("STS"), rs.getString("STN"), rs.getDate("F_DATE"), rs.getDate("C_DATE"), rs.getString("GENDER"));
                listData.add(cmp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }            
        return listData;
    }
    
    private ObservableList<Complaint> firslistdata;
    
    public void showfirtrack() throws Exception{
        firslistdata=firtracklist();
        comp_fid.setCellValueFactory(new PropertyValueFactory<>("FID"));
        comp_crme.setCellValueFactory(new PropertyValueFactory<>("CTYPE"));
        comp_sts.setCellValueFactory(new PropertyValueFactory<>("STS"));
        comp_cdate.setCellValueFactory(new PropertyValueFactory<>("C_DATE"));
        comp_fdate.setCellValueFactory(new PropertyValueFactory<>("F_DATE"));
        comp_stn.setCellValueFactory(new PropertyValueFactory<>("STN"));
        comp_gdr.setCellValueFactory(new PropertyValueFactory<>("GENDER"));
        comp_table.setItems(firslistdata);
    }
    
    public ObservableList<Complainants> usrlist() throws Exception{
        ObservableList<Complainants> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            String query="SELECT * FROM LOGIN";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Complainants cmpts;
            while(rs.next()){
                cmpts=new Complainants(rs.getString("UID"), rs.getString("UNAME"), rs.getString("PHONE"));
                listData.add(cmpts);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }            
        return listData;
    }
    
    private ObservableList<Complainants> usrlistdata;
    
    public void showusrs() throws Exception{
        usrlistdata=usrlist();
        usrlst_uid.setCellValueFactory(new PropertyValueFactory<>("UID"));
        usrlst_uname.setCellValueFactory(new PropertyValueFactory<>("UNAME"));
        usrlst_phn.setCellValueFactory(new PropertyValueFactory<>("PHONE"));
        usrlst_table.setItems(usrlistdata);
    }
    
    public ObservableList<Criminals> crmList(){
        String query="SELECT * FROM CRIMINALS";
        ObservableList<Criminals> listData=FXCollections.observableArrayList();
        try{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            Criminals crm;
            while(rs.next()){
                crm=new Criminals(rs.getString("CID"), rs.getString("FID"), rs.getString("NME"), rs.getDate("DOB"), rs.getDate("DOA"), rs.getString("PLC"));
                listData.add(crm);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    
    private ObservableList<Criminals> crmlistdata;
    
    public void crmshowdata(){
        crmlistdata=crmList();
        addcrm_cid.setCellValueFactory(new PropertyValueFactory<>("CID"));
        addcrm_fid.setCellValueFactory(new PropertyValueFactory<>("FID"));
        addcrm_nme.setCellValueFactory(new PropertyValueFactory<>("NME"));
        addcrm_dob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        addcrm_doa.setCellValueFactory(new PropertyValueFactory<>("DOA"));
        addcrm_plc.setCellValueFactory(new PropertyValueFactory<>("PLC"));
        addcrimtable.setItems(crmlistdata);
    }
    
    public void showtrackbtnOnAction() throws Exception{
        if(comptrack_fid.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field!");
            alert.showAndWait();
        }
        else{
            DataPass dp=DataPass.getInstance();
            String oid=comptrack_fid.getText();
            String uid=dp.getuid();
            try{
                String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
                String mysqlusr="root";
                String mysqlpwd="1234";
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                String query="SELECT * FROM FIR WHERE FID='"+oid+"' AND UID='"+uid+"';";
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                int flag=0;
                String path="", sts="", txt="";
                while(rs.next()){
                    sts=rs.getString("STS");
                    path="File:"+rs.getString("IMG");
                    txt=rs.getString("DESCRPT");
                    flag=1;
                }
                if(flag==0){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong entries!");
                    alert.showAndWait();
                }
                else{
                    Image image=new Image(path, 200, 200, false, true);
                    comptrack_img.setImage(image);
                    comptrack_descrpt.setWrapText(true);
                    comptrack_descrpt.setText(txt);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void complodgebtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="INSERT INTO FIR VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String c_query="SELECT COUNT(FID) FROM FIR";
        String ctype=(String)comp_crime.getSelectionModel().getSelectedItem();
        String stn=(String)comp_station.getSelectionModel().getSelectedItem();
        String gender=(String)vicgen.getSelectionModel().getSelectedItem();
        String fid="";
        try{
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            PreparedStatement ps=conn.prepareStatement(c_query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                fid="F"+Integer.toString(rs.getInt(1)+1);
            }
            if(ctype.equals("") || stn.equals("") || comp_date.getValue()==null || ctype.equals("") || stn.equals("") || gender.equals("")){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Field(s) Missing!");
                alert.showAndWait();
            }
            else{
                DataPass dp=DataPass.getInstance();
                ps=conn.prepareStatement(query);
                ps.setString(1, dp.getuid());
                ps.setString(2, fid);
                ps.setString(3, ctype);
                ps.setString(4, stn);
                ps.setString(5, comp_descrpt.getText());
                String path=data.path;
                path=path.replace("\\", "\\\\");
                ps.setString(6, path);
                ps.setString(7, "In Progress");
                ps.setDate(8, java.sql.Date.valueOf(java.time.LocalDate.now()));
                ps.setDate(9, java.sql.Date.valueOf(comp_date.getValue()));
                ps.setString(10, plstn(stn));
                ps.setString(11, gender);
                ps.setString(12, path);
                ps.setString(13, comp_descrpt.getText());
                ps.execute();
                comp_crime.getSelectionModel().clearSelection();
                comp_station.getSelectionModel().clearSelection();
                comp_date.setValue(null);
                comp_descrpt.setText("");
                vicgen.getSelectionModel().clearSelection();
                custreportimg.setImage(null);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Your FIR ID: "+fid+"!");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void forgotpswdchangebtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="SELECT * FROM LOGIN WHERE UID='"+uid1.getText()+"';";
        try{
            Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
            String uid="", pwd="", skey="", phn="";
            PreparedStatement ps=conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                uid=rs.getString("UID");
                skey=rs.getString("SECKEY");
            }
            DataPass dp=DataPass.getInstance();
            String otp=dp.getotp();
            if(uid.equals("") || !seckey.getText().equals(skey) || seckey.getText().isBlank() || otpval.getText().isBlank()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Credentials!");
                alert.showAndWait();
            }
            else if(otpval.getText().equals(otp)){
                query="UPDATE LOGIN SET PSWD='"+npswd.getText()+"' WHERE UID='"+uid1.getText()+"';";
                Statement stmnt=conn.createStatement();
                stmnt.executeUpdate(query);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Password Changed!");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong OTP!");
                alert.showAndWait();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void signupbtnOnAction() throws Exception{
        Parent root=FXMLLoader.load(getClass().getResource("signup.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setMaxWidth(1230);
        stage.setMaxHeight(820);
        stage.setScene(scene);
        stage.show();
    }
    
    public void signuppagebtnOnAction() throws Exception{
        String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
        String mysqlusr="root";
        String mysqlpwd="1234";
        String query="INSERT INTO LOGIN VALUES(?, ?, ?, ?, ?, ?, ?)";
        String c_query="SELECT COUNT(UID) FROM LOGIN";
        try{
            String uid="", temp=(String)sgnup_city.getSelectionModel().getSelectedItem();
            if(sgnup_otp.getText().isBlank() || sgnup_uname.getText().isBlank() || sgnup_pswd.getText().isBlank() || temp.equals("") || sgnup_phn.getText().isBlank() || sgnup_seckey.getText().isBlank()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Empty Field(s)!");
                alert.showAndWait();
            }
            else{
                DataPass dp=DataPass.getInstance();
                if(!dp.getotp().equals(sgnup_otp.getText())){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong OTP!");
                    alert.showAndWait();
                }
                else{
                    String chk_query="SELECT PHONE FROM LOGIN";
                    Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                    PreparedStatement ps=conn.prepareStatement(chk_query);
                    ResultSet rs=ps.executeQuery();
                    int flag=0;
                    String temp1="";
                    while(rs.next()){
                        temp1=rs.getString("PHONE");
                        if(temp1.equals(sgnup_phn.getText())){
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0){
                        ps=conn.prepareStatement(c_query);
                        rs=ps.executeQuery();
                        while(rs.next()){
                            uid="U"+Integer.toString(rs.getInt(1)+1);
                        }
                        ps=conn.prepareStatement(query);
                        ps.setString(1, uid);
                        ps.setString(2, sgnup_uname.getText());
                        ps.setString(3, sgnup_pswd.getText());
                        ps.setString(4, (String)sgnup_city.getSelectionModel().getSelectedItem());
                        ps.setString(5, sgnup_phn.getText());
                        dp.setphone(sgnup_phn.getText());
                        ps.setString(6, sgnup_seckey.getText());
                        ps.setString(7, plstn((String)sgnup_city.getSelectionModel().getSelectedItem()));
                        ps.execute();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Your UID is: "+uid+" and you have signed up!");
                        alert.showAndWait();
                    }
                    else{
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Phone number exists!");
                        alert.showAndWait();
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loginbtnOnAction() throws Exception{
        if(usrnm.getText().isBlank() || pswd.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            String query="SELECT * FROM LOGIN WHERE UID='"+usrnm.getText()+"';";
            String uid="", pwd="", nme="", pid="";
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    uid=rs.getString("UID");
                    pwd=rs.getString("PSWD");
                    nme=rs.getString("UNAME");
                    pid=rs.getString("DEFPID");
                }
                if(uid.equals("") || !pwd.equals(pswd.getText())){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Credentials!");
                    alert.showAndWait();
                }
                else{
                    DataPass dp=DataPass.getInstance();
                    dp.setpid(pid);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome, "+nme+"!");
                    alert.showAndWait();
                    dp.setuid(uid);
                    Parent root=FXMLLoader.load(getClass().getResource("complainant.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);
                    stage.setMaxWidth(1214);
                    stage.setMaxHeight(800);
                    stage.setScene(scene);
                    stage.show();
                    loginbtn.getScene().getWindow().hide();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void polloginbtnOnAction() throws Exception{
        if(polusrnm.getText().isBlank() || polpswd.getText().isBlank()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Empty Field(s)!");
            alert.showAndWait();
        }
        else{
            String url="jdbc:mysql://localhost:3306/crime?useSSL=false";
            String mysqlusr="root";
            String mysqlpwd="1234";
            String query="SELECT * FROM POLICE WHERE PID='"+polusrnm.getText()+"';";
            String uid="", stn="";
            try{
                Connection conn=DriverManager.getConnection(url, mysqlusr, mysqlpwd);
                PreparedStatement ps=conn.prepareStatement(query);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    uid=rs.getString("PID");
                    stn=rs.getString("STN");
                }
                if(uid.equals("") || !polpswd.getText().equals("police123")){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Credentials!");
                    alert.showAndWait();
                }
                else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Police Station: "+stn+"!");
                    alert.showAndWait();
                    DataPass dp=DataPass.getInstance();
                    dp.setstn(stn);
                    dp.setpid(uid);
                    Parent root=FXMLLoader.load(getClass().getResource("police.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);
                    stage.setMaxWidth(1214);
                    stage.setMaxHeight(800);
                    stage.setScene(scene);
                    stage.show();
                    polloginbtn.getScene().getWindow().hide();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void logoutbtnOnAction(ActionEvent e) throws Exception{
        Stage stage=(Stage)logoutbtn.getScene().getWindow();
        stage.close();
        Parent root=FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        Scene scene=new Scene(root);
        stage.setMaxWidth(1214);
        stage.setMaxHeight(800);
        stage.setScene(scene);
        stage.show();
    }
    
    public void readbtnOnAction(ActionEvent e) throws Exception{
        if(e.getSource()==readbtn){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Security key can be your first school name, your favourite book, etc!");
            alert.showAndWait();
        }
    }
    
    public void switchpane(ActionEvent e) throws Exception{
        if(e.getSource()==compdashboardbtn){
            compdashboardpane.setVisible(true);
            compreportpane.setVisible(false);
            comptrackpane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            cmpid.setText(dp.getuid());
            
            displaydashbrd();
            displaylinechart();
            displaybarchart1();
            displaybarchart2();
        }
        
        if(e.getSource()==compreportbtn){
            compdashboardpane.setVisible(false);
            compreportpane.setVisible(true);
            comptrackpane.setVisible(false);
            
            comp_descrpt.setWrapText(true);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            cmpid.setText(dp.getuid());
            
            comp_crime.getSelectionModel().clearSelection();
            comp_station.getSelectionModel().clearSelection();
            comp_date.setValue(null);
            comp_descrpt.setText("");
            vicgen.getSelectionModel().clearSelection();
            custreportimg.setImage(null);
            
            crimesList();
            stationsList();
            genderList();
        }
        
        if(e.getSource()==comptrackbtn){
            compdashboardpane.setVisible(false);
            compreportpane.setVisible(false);
            comptrackpane.setVisible(true);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            cmpid.setText(dp.getuid());
            
            showfirtrack();
        }
        
        if(e.getSource()==complodgerefbtn){
            comp_crime.getSelectionModel().clearSelection();
            comp_station.getSelectionModel().clearSelection();
            comp_date.setValue(null);
            comp_descrpt.setText("");
            vicgen.getSelectionModel().clearSelection();
            custreportimg.setImage(null);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            cmpid.setText(dp.getuid());
        }
        
        if(e.getSource()==wantedbtn){
            Parent root=FXMLLoader.load(getClass().getResource("wanted.fxml"));
            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setMaxWidth(1150);
            stage.setMaxHeight(780);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("WANTED CRIMINALS");
        }
        
        if(e.getSource()==missingbtn){
            Parent root=FXMLLoader.load(getClass().getResource("missing.fxml"));
            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setMaxWidth(1100);
            stage.setMaxHeight(730);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("MISSING");
        }
        
        if(e.getSource()==forgotpswdbtn){
            Parent root=FXMLLoader.load(getClass().getResource("forgotpswd.fxml"));
            Stage stage=new Stage();
            Scene scene=new Scene(root);
            stage.setMaxWidth(1100);
            stage.setMaxHeight(720);
            stage.setScene(scene);
            stage.show();
        }
        
        if(e.getSource()==poldashboardbtn){
            dashboardpane.setVisible(true);
            statuspane.setVisible(false);
            casespane.setVisible(false);
            crimpane.setVisible(false);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(false);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            displaydashbrd();
            displaylinechart();
            displaybarchart1();
            displaybarchart2();
        }
        
        if(e.getSource()==polstatusbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(true);
            casespane.setVisible(false);
            crimpane.setVisible(false);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(false);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
        }
        
        if(e.getSource()==poltrackbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(false);
            casespane.setVisible(true);
            crimpane.setVisible(false);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(false);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            polshowfirtrack();
        }
        
        if(e.getSource()==polcrimbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(false);
            casespane.setVisible(false);
            crimpane.setVisible(true);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(false);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            crmshowdata();
        }
        
        if(e.getSource()==addwntdbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(false);
            casespane.setVisible(false);
            crimpane.setVisible(false);
            addwntdpane.setVisible(true);
            shwusrpane.setVisible(false);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            crimesList();
            showwntdlist();
        }
        
        if(e.getSource()==showusrsbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(false);
            casespane.setVisible(false);
            crimpane.setVisible(false);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(true);
            mspane.setVisible(false);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            showusrs();
        }
        if(e.getSource()==msbtn){
            dashboardpane.setVisible(false);
            statuspane.setVisible(false);
            casespane.setVisible(false);
            crimpane.setVisible(false);
            addwntdpane.setVisible(false);
            shwusrpane.setVisible(false);
            mspane.setVisible(true);
            
            bcklbl.setText("");
            
            DataPass dp=DataPass.getInstance();
            polstn.setText(dp.getstn());
            
            showmsng();
        }
    }
    
    public void refbtnOnAction(ActionEvent e) throws Exception{
        if(e.getSource()==comptrackrefbtn){
            comptrack_img.setImage(null);
            comptrack_descrpt.setText("");
            comptrack_fid.setText("");
        }
        if(e.getSource()==wntd_ref){
            showmsng();
            addms_firid.setText("");
            msimg1.setImage(null);
        }
        if(e.getSource()==wntdref){
            showwntdlist();
            addwntd_fid.setText("");
            addwntd_img1.setImage(null);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
