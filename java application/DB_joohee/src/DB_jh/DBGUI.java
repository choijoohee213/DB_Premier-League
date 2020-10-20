package DB_jh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Optional;

import DB_jh.Nodes;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DBGUI extends Application {

// data members
   private Connection con = MyConnection.mackConnection();
   private TableView table;
   TreeView<String> tree;
   Button[] buttons;
   Label[] labels;
   TextField[] txt;
   TextArea txtArea;
   TextField field;
   private final String[] btntext = { "clear", "save", "update", "delete", "print", "search" };

   
// function members
   private HBox addCenterPane() {
      
      HBox hb1 = new HBox();
      
      // Add TableView
      VBox vb = new VBox();

      table = new TableView<>();
      table.setMinSize(800, 200);
      table.setMaxSize(800, 200);
      table.setStyle("-fx-border-color: Black;");
      table.prefWidthProperty().bind(vb.prefWidthProperty());
      table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      table.getSelectionModel().setCellSelectionEnabled(false);
      
      table.getSelectionModel().selectedItemProperty().addListener((v,oldV,newV)->{
    	  if(newV!=null) {
    	  showFields();
      	}
      });
       

      // Add Labels and TextFields
      GridPane  gp = new GridPane (); 
      gp.setPadding(new Insets(15, 15, 15, 125));
      gp.setHgap(10);
      gp.setVgap(10);
      gp .setStyle("-fx-border-color: Blue;");
      gp.prefHeightProperty().bind(table.prefWidthProperty());
        txt=new TextField[12]; 
        labels= new Label[12];
        
        for (int i = 0; i < labels.length; i++) { 
           labels[i]= new Label("Label..");
           labels[i].setMinSize(150, 20);
            txt[i]= new TextField(" Text.. "); 
            txt[i].setMinSize(300, 20);
            gp.addRow(i, labels[i],txt[i] );
            labels[i].prefHeightProperty().bind(gp.widthProperty());
            txt[i].prefHeightProperty().bind(gp.widthProperty());
         }
    
        
       vb.getChildren().addAll(table, gp);
      
       // Add TreeView
            StackPane stack = new StackPane();

            // Create the tree
            tree = addNodestoTree();
            tree.setShowRoot(true);
            
            tree.getSelectionModel().selectedItemProperty().addListener((v,oldV,newV)->{/// old new 
            if(oldV!=newV) {
               String str=mySelectNode();
               txtArea.appendText("You have Selected"+str+ " \n");
               
               if(str.contentEquals(Nodes.Matches.toString()) ||
            		   str.contentEquals(Nodes.Players.toString())||
            		   str.contentEquals(Nodes.Record.toString()) ||
            		   str.contentEquals(Nodes.Stadium.toString())||
            		   str.contentEquals(Nodes.Teams.toString()))
            	   {
            	   rsToTableView(str);
            	   }
               
               if(str.contentEquals(Nodes.About.toString()))
               {
            	   //about - new window open
            	    Stage newWindow = new Stage();
            	    
            	    Label secondLabel = new Label("<About DataBase>\n\n\n"
            	    		+ "Database Name : premier league\n\n"
            		      	+ "number of tables : 5\n\n"+"number of Stored Procedures : 15\n\n"
            		    	+ "number of Table-valued functions : 11\n\n"
            		      	+ "Server Name : DESKTOP-68PTQOL\\SQLEXPRESS:1433\n\n"
            		    	+ "Developer Name : 최주희\n\n");
            	    secondLabel.setFont(new Font("Arial",20)); 
            	    
            		StackPane secondaryLayout = new StackPane();
            		secondaryLayout.getChildren().add(secondLabel);

            		Scene secondScene = new Scene(secondaryLayout, 700, 450);

            		// New window (Stage)
            		newWindow.setTitle("About");
            		newWindow.setScene(secondScene);

            		newWindow.centerOnScreen();
            		newWindow.show();
            		}
               
               
               		if(str.contentEquals(Nodes.Exit.toString()))
               		{
               			//exit alert
               			Alert alert = new Alert(AlertType.CONFIRMATION); 
               			alert.setTitle("Exit Program"); 
               			alert.setHeaderText("Do you really want to exit the program?"); 
               			alert.setContentText("Click the OK button to exit the program."); 
               			Optional<ButtonType> result = alert.showAndWait(); 
               			if(result.get() == ButtonType.OK) { System.exit(0); }
               			else if(result.get() == ButtonType.CANCEL) {  alert.close(); }

               		}
               }
           
            });
            tree.setMaxWidth(150);
            tree.prefWidthProperty().bind(stack.prefWidthProperty());
            stack.getChildren().add(tree);

      hb1.getChildren().addAll(stack,vb);
      hb1.setStyle("-fx-border-color:black;");
      hb1.setSpacing(20);
      hb1.prefHeightProperty().bind(vb.prefWidthProperty());
   

      return hb1;
   }
   
    
   private StackPane addBottomPane() {

      StackPane  stack = new StackPane();
      stack.setMaxHeight(110);
      stack.setMinHeight(110);
      stack.setPrefHeight(110);
      
      stack.setStyle("-fx-border-color: #336699;");
      txtArea  = new TextArea();
     // txtArea.appendText(MyConnection.sb.toString());
      txtArea.prefHeightProperty().bind(stack.prefWidthProperty());
      stack.getChildren().add(txtArea);
      return stack;
   }
   
   
   private HBox addTopPane() {

      HBox hbox = new HBox();
      hbox.setPadding(new Insets(15, 15, 15, 15));
      hbox.setSpacing(10); // Gap between nodes
      //hbox.setStyle("-fx-background-color: #336699;");
      hbox.setStyle("-fx-border-color: Blue;");
      field=new TextField();
      field.setText("Search PK");

      buttons = new Button[6];
      for (int i = 0; i < buttons.length; i++) {

         buttons[i] = new Button(btntext[i]);
         buttons[i].setPrefSize(80, 20);
         buttons[i].prefHeightProperty().bind(hbox.prefWidthProperty());
      }
 
      field.setPrefSize(80, 20);
      field.prefHeightProperty().bind(hbox.prefWidthProperty());
      
      for(int i=0; i<buttons.length; i++) {
    	  final int j=i;
    	  buttons[j].setOnAction((event)->{
    		  String str=buttons[j].getText();
    		  txtArea.appendText("You have clicked+"+str+"\n");
    		  if("clear".equals(str)) { clearTextFields();}
    		  else if("save".equals(str)) {
    			  if(mySelectNode().equals(Nodes.Matches.toString()))
    				  //insertBranchIS();
    			  	  //insertBranchRS();
    				  insertMatchesSP();
    			  
    			  if(mySelectNode().equals(Nodes.Players.toString()))
    				  //insertBranchIS();
    			  	  //insertBranchRS();
    				  insertPlayersSP();
    			  
    			  if(mySelectNode().equals(Nodes.Record.toString()))
    				  //insertBranchIS();
    			  	  //insertBranchRS();
    				  insertRecordSP();
    			  
    			  if(mySelectNode().equals(Nodes.Stadium.toString()))
    				  //insertBranchIS();
    			  	  //insertBranchRS();
    				  insertStadiumSP();
    			  
    			  if(mySelectNode().equals(Nodes.Teams.toString()))
    				  //insertBranchIS();
    			  	  //insertBranchRS();
    				  insertTeamsSP();
    		  }
    		  else if("delete".equals(str)) {
    			  if(mySelectNode().equals(Nodes.Matches.toString()))
    				  //deleteBranchDS();
    				  //deleteBranchRS();
    				  deleteMatchesSP();
    			  
    			  if(mySelectNode().equals(Nodes.Players.toString()))
    				  //deleteBranchDS();
    				  //deleteBranchRS();
    				  deletePlayersSP();
    			  
    			  if(mySelectNode().equals(Nodes.Record.toString()))
    				  //deleteBranchDS();
    				  //deleteBranchRS();
    				  deleteRecordSP();
    			  
    			  if(mySelectNode().equals(Nodes.Stadium.toString()))
    				  //deleteBranchDS();
    				  //deleteBranchRS();
    				  deleteStadiumSP();
    			  
    			  if(mySelectNode().equals(Nodes.Teams.toString()))
    				  //deleteBranchDS();
    				  //deleteBranchRS();
    				  deleteTeamsSP();
    		  }
    		  else if("update".equals(str)) {
    			  if(mySelectNode().equals(Nodes.Matches.toString()))
    				  //updateBranchUS();
    				  //updateBranchRS();
    				  updateMatchesSP();
    			  
    			  if(mySelectNode().equals(Nodes.Players.toString()))
    				  //updateBranchUS();
    				  //updateBranchRS();
    				  updatePlayersSP();
    			  
    			  if(mySelectNode().equals(Nodes.Record.toString()))
    				  //updateBranchUS();
    				  //updateBranchRS();
    				  updateRecordSP();
    			  
    			  if(mySelectNode().equals(Nodes.Stadium.toString()))
    				  //updateBranchUS();
    				  //updateBranchRS();
    				  updateStadiumSP();
    			  
    			  if(mySelectNode().equals(Nodes.Teams.toString()))
    				  //updateBranchUS();
    				  //updateBranchRS();
    				  updateTeamsSP();
    		  }
    		  
    		  else if("print".equals(str)) {
    			  if(mySelectNode().equals(Nodes.Matches.toString()))
        			  reportMatches();
    			  if(mySelectNode().equals(Nodes.Players.toString()))
        			  reportPlayers();
    			  if(mySelectNode().equals(Nodes.Record.toString()))
        			  reportRecord();
    			  if(mySelectNode().equals(Nodes.Stadium.toString()))
        			  reportStadium();
    			  if(mySelectNode().equals(Nodes.Teams.toString()))
        			  reportTeams();
    		  }
    		  else if("search".equals(str)) {
    			  if(mySelectNode().equals(Nodes.Matches.toString()))
        			  searchPK(Nodes.Matches.toString());
    			  if(mySelectNode().equals(Nodes.Players.toString()))
    				  searchPK(Nodes.Players.toString());
    			  if(mySelectNode().equals(Nodes.Record.toString()))
    				  searchPK(Nodes.Record.toString());
    			  if(mySelectNode().equals(Nodes.Stadium.toString()))
    				  searchPK(Nodes.Stadium.toString());
    			  if(mySelectNode().equals(Nodes.Teams.toString()))
    				  searchPK(Nodes.Teams.toString());
    		  }
    	  });
    	  
    	  
      }

      hbox.getChildren().addAll(buttons);
      hbox.getChildren().addAll(field);

      return hbox;
   }

   

private  TreeView<String> addNodestoTree() {
       TreeView<String> tree = new TreeView<String>();
       
       TreeItem<String> root, tables, reports, exit, about; //variables
       
          root = new TreeItem<String>("premier league");
          
          tables = new TreeItem<String>("Tables");
          
          makeChild(Nodes.Matches.toString(), tables);
          makeChild(Nodes.Players.toString(), tables);
          makeChild(Nodes.Record.toString(), tables);
          makeChild(Nodes.Stadium.toString(), tables);
          makeChild(Nodes.Teams.toString(), tables);

          reports = new TreeItem<String>("Reports");
          makeChild(Nodes.Report01.toString(), reports);
          makeChild(Nodes.Report02.toString(), reports);
          makeChild(Nodes.Report03.toString(), reports);
          
          exit = new TreeItem<String>(Nodes.Exit.toString());
          about=  new TreeItem<String>(Nodes.About.toString());
           root.getChildren().addAll(tables,reports, exit, about);
           tree.setRoot(root);
         return tree;

    }   
    
   // Create child
   private TreeItem<String> makeChild(String title, TreeItem<String> parent) {
      TreeItem<String> item = new TreeItem<>(title);
      item.setExpanded(false);
      parent.getChildren().add(item);
      return item;
   }


   @Override
   public void start(Stage stage) {

      // Add controls and Layouts
      
      VBox vbox = new VBox();
      vbox.setSpacing(20);
      vbox.setMinSize(1000, 700);
      vbox.setMaxSize(1000, 850);
      vbox.setPadding(new Insets(15, 15, 15, 15));
      vbox.setSpacing(10); // Gap between nodes
      vbox.setStyle("-fx-border-color: Black;");
      
      // Top Box
      HBox tbox=addTopPane();
      tbox.prefHeightProperty().bind(vbox.prefWidthProperty());
      vbox.getChildren().add(tbox);
      
      // Center box
      HBox cbox=addCenterPane();
      cbox.prefHeightProperty().bind(vbox.prefWidthProperty());
      vbox.getChildren().add(cbox);
         
      StackPane bbox=addBottomPane();
      bbox.prefHeightProperty().bind(vbox.prefWidthProperty());
      vbox.getChildren().add(bbox);

      
      
      
      //create and show stage 
      Scene scene = new Scene(vbox);
      stage.setScene(scene);
      stage.setTitle("premier league");
      stage.show();
   }
   private String mySelectNode() {
      TreeItem ti= tree.getSelectionModel().selectedItemProperty().getValue();
      return ti.getValue().toString();
   }

   private void showFields() {
	   clearFields();
	   TablePosition pos= (TablePosition)table.getSelectionModel().getSelectedCells().get(0);
	   int row=pos.getRow();
	   int cols=table.getColumns().size();
	   
	   for(int j=0; j<cols; j++) {
		   Object ch=((TableColumnBase)table.getColumns().get(j)).getText();
		   Object cell = ((TableColumnBase)table.getColumns().get(j)).getCellData(row).toString();
		   txt[j].setText(cell.toString());
		   txt[j].setVisible(true);
		   labels[j].setText(ch.toString());
		   labels[j].setVisible(true);
	   }
   }
   private void clearFields() {
	   for(int i=0; i<txt.length; i++) {
		   txt[i].setText("");
		   txt[i].setVisible(false);
		   labels[i].setText("");
		   labels[i].setVisible(false);
	   }
   }
   private void clearTextFields() {
	   int noc = table.getColumns().size();
	   for(int i=0; i<noc; i++) txt[i].setText("");
   }
   
   private void rsToTableView(String s) {
		
		table.getColumns().clear();
		for ( int i = 0; i<table.getItems().size(); i++) {
		   table.getItems().clear();
		}
		
		ObservableList data = FXCollections.observableArrayList();
		try {
			String query = "select * from " + s + "";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int colCount = rsmd.getColumnCount();

			// get data columns

			for (int i = 0; i < colCount; i++) {

				//int dataType = rsmd.getColumnType(i + 1);

				final int j = i;
				TableColumn col = new TableColumn(rsmd.getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
				

				table.getColumns().addAll(col);
			}
			
			//get.rows
			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int k = 1; k <= colCount; k++) {
					String str1=rs.getString(k);
					if(str1==null) {
		                  str1="null";
		               }
					row.add(rs.getString(k));
				}
				data.add(row);
				
			}

			table.setItems(data);

			table.getSelectionModel().select(0);  //첫번쨰 행 선택됨
			showFields();
			table.scrollTo(0);

		} catch (Exception e) {
			txtArea.appendText(e.getMessage());
		} finally {
		}
	}
   


 //insert a record in Matches table using Stored Procedures
 private void insertMatchesSP() {
 	   
 	   String sql="{call usp_insertMatches(?,?,?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());
 		   cst.setString(9, txt[8].getText());
 		   cst.setString(10, txt[9].getText());

 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is inserted by using insertMatchesSP()"+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not inserted"+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //insert a record in Players table using Stored Procedures
 private void insertPlayersSP() {
 	   
 	   String sql="{call usp_insertPlayers(?,?,?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());
 		   cst.setString(9, txt[8].getText());
 		   cst.setString(10, txt[9].getText());

 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is inserted by using insertPlayersSP()"+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not inserted"+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //insert a record in Record table using Stored Procedures
 private void insertRecordSP() {
 	   
 	   String sql="{call usp_insertRecord(?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());


 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is inserted by using insertRecordSP()"+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not inserted"+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //insert a record in Stadium table using Stored Procedures
 private void insertStadiumSP() {
 	   
 	   String sql="{call usp_insertStadium(?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());

 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is inserted by using insertStadiumSP()"+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not inserted"+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //insert a record in Teams table using Stored Procedures
 private void insertTeamsSP() {
 	   
 	   String sql="{call usp_insertTeams(?,?,?,?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());

 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is inserted by using insertTeamsSP()"+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not inserted"+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }




 //delete a record in Matches table using Stored Procedure
 private void deleteMatchesSP() {
 	   String sql="{call usp_deleteMatches(?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is deleted.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not deleted.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //delete a record in Players table using Stored Procedure
 private void deletePlayersSP() {
 	   String sql="{call usp_deletePlayers(?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is deleted.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not deleted.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //delete a record in Record table using Stored Procedure
 private void deleteRecordSP() {
 	   String sql="{call usp_deleteRecord(?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is deleted.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not deleted.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //delete a record in Stadium table using Stored Procedure
 private void deleteStadiumSP() {
 	   String sql="{call usp_deleteStadium(?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is deleted.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not deleted.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //delete a record in Teams table using Stored Procedure
 private void deleteTeamsSP() {
 	   String sql="{call usp_deleteTeams(?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is deleted.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not deleted.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }





 //update a record in Matches table using Stored Procedure
 private void updateMatchesSP() {
 	   String sql="{call usp_updateMatches(?,?,?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());
 		   cst.setString(9, txt[8].getText());
 		   cst.setString(10, txt[9].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is updated.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not updated.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //update a record in Players table using Stored Procedure
 private void updatePlayersSP() {
 	   String sql="{call usp_updatePlayers(?,?,?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());
 		   cst.setString(9, txt[8].getText());
 		   cst.setString(10, txt[9].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is updated.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not updated.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }


 //update a record in Record table using Stored Procedure
 private void updateRecordSP() {
 	   String sql="{call usp_updateRecord(?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is updated.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not updated.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //update a record in Stadium table using Stored Procedure
 private void updateStadiumSP() {
 	   String sql="{call usp_updateStadium(?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is updated.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not updated.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }

 //update a record in Teams table using Stored Procedure
 private void updateTeamsSP() {
 	   String sql="{call usp_updateTeams(?,?,?,?,?,?,?,?)}";
 	   try {
 		   CallableStatement cst=con.prepareCall(sql);
 		   cst.setString(1, txt[0].getText());
 		   cst.setString(2, txt[1].getText());
 		   cst.setString(3, txt[2].getText());
 		   cst.setString(4, txt[3].getText());
 		   cst.setString(5, txt[4].getText());
 		   cst.setString(6, txt[5].getText());
 		   cst.setString(7, txt[6].getText());
 		   cst.setString(8, txt[7].getText());
 		 
 		   cst.execute();
 		   cst.close();
 		   txtArea.appendText("Record is updated.."+"\n");
 	   }
 	   
 	   catch(Exception e){
 		   txtArea.appendText("Record is not updated.."+"\n");
 		   txtArea.appendText(e.getMessage());
 	   }
 	   
 	   finally {}
 }
 
 
 private void reportMatches() {
	   String fileName="C:\\Users\\choij\\JaspersoftWorkspace\\MyReports\\ReportMatches.jrxml";
	   try {
		   JasperReport jr=JasperCompileManager.compileReport(fileName);
		   JasperPrint jp=JasperFillManager.fillReport(jr, null,con);
		   JasperViewer.viewReport(jp,false);
	   }
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in printing report..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
 }
 
 private void reportPlayers() {
	   String fileName="C:\\Users\\choij\\JaspersoftWorkspace\\MyReports\\ReportPlayers.jrxml";
	   try {
		   JasperReport jr=JasperCompileManager.compileReport(fileName);
		   JasperPrint jp=JasperFillManager.fillReport(jr, null,con);
		   JasperViewer.viewReport(jp,false);
	   }
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in printing report..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
}
 
 private void reportRecord() {
	   String fileName="C:\\Users\\choij\\JaspersoftWorkspace\\MyReports\\ReportRecord.jrxml";
	   try {
		   JasperReport jr=JasperCompileManager.compileReport(fileName);
		   JasperPrint jp=JasperFillManager.fillReport(jr, null,con);
		   JasperViewer.viewReport(jp,false);
	   }
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in printing report..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
}
 
 private void reportStadium() {
	   String fileName="C:\\Users\\choij\\JaspersoftWorkspace\\MyReports\\ReportStadium.jrxml";
	   try {
		   JasperReport jr=JasperCompileManager.compileReport(fileName);
		   JasperPrint jp=JasperFillManager.fillReport(jr, null,con);
		   JasperViewer.viewReport(jp,false);
	   }
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in printing report..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
}
 
 private void reportTeams() {
	   String fileName="C:\\Users\\choij\\JaspersoftWorkspace\\MyReports\\ReportTeams.jrxml";
	   try {
		   JasperReport jr=JasperCompileManager.compileReport(fileName);
		   JasperPrint jp=JasperFillManager.fillReport(jr, null,con);
		   JasperViewer.viewReport(jp,false);
	   }
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in printing report..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
}

 private void searchPK(String s) {
	 	String query = "select * from "+ s +"";
	   try {
		   
		   String searchcon = field.getText();
		   int rownum=1;;
		   boolean searching=false;
		   
		   PreparedStatement pst=con.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,
				   ResultSet.CONCUR_UPDATABLE);   
		   ResultSet rs = pst.executeQuery();
		  
		   while (rs.next()&&!searching) {
				if(searchcon.equals(rs.getString(1))) {
						rownum=rs.getRow();
						searching=true;
				}
			}
		   
		   
		   if(searching==true) {
		   table.getSelectionModel().select(rownum-1);
		   table.scrollTo(rownum-1);
		   txtArea.appendText("Search sucess..\n");
		   }
		   else {
			   txtArea.appendText("There is no data you are looking for....\n");
		   }
		   rs.close();
		   pst.close();
		}
		 
			
	   catch(Exception e) {
		
		txtArea.appendText("There is a problem in searching..\n");
		txtArea.appendText(e.getMessage());
	   }
	   finally{}
}

   


   public static void main(String[] args) {
      launch(args);
   }

}