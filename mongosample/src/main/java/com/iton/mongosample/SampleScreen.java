package com.iton.mongosample;

import java.util.ArrayList;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class SampleScreen extends CustomComponent{
	
	private VerticalLayout mainLayout;
	private TextField nameField;
	private TextField salaryField;
	private Button submitButton;
	private JavaMongoDemo javaMongoDemo;
	private User user;
    private ArrayList<User> userCollection;
	private Grid grid;
	private Button updateButton;
	private String editNameValue;
	private String deleteNameValue;
	private String editSalaryValue;
	private Button cancelButton;
	private FormLayout formField;
	private Button button2;
	public SampleScreen() {
		buildMainLayout();
		setSizeFull();
	
		setCompositionRoot(mainLayout);
	}
	
	private VerticalLayout buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		mainLayout.addStyleName("sample");
		formField = new FormLayout();
		formField.addStyleName("formfield");
		formField.setWidth("-1px");
		nameField = new TextField("Name");
		nameField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		salaryField = new TextField("Salary");
		salaryField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		HorizontalLayout hl = new HorizontalLayout();
		
		submitButton  = new Button("Add");
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		updateButton  = new Button("Update");
		updateButton.addStyleName(ValoTheme.BUTTON_SMALL);
		updateButton.setVisible(false);
		cancelButton  = new Button("Cancel");
		cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cancelButton.setVisible(false);
		hl.addComponents(submitButton,updateButton,cancelButton);
		formField.addComponents(nameField,salaryField,hl);
		mainLayout.addComponent(formField);
		mainLayout.setComponentAlignment(formField, Alignment.MIDDLE_CENTER);
		 javaMongoDemo = new JavaMongoDemo();
		 user= new User();
		 submitButton.addClickListener(e->{
			 
			 String name = nameField.getValue();
			 String salary = salaryField.getValue();
			 if(name==null||name.trim().equalsIgnoreCase("")||salary==null||salary.trim().equalsIgnoreCase("")) {
				 Notification.show("please fill name,salary fields", Type.WARNING_MESSAGE);
			 }else {
			user.setName(name);
			user.setSalary(salary);
			javaMongoDemo.addFields(user);
			userCollection  =  javaMongoDemo.getUserCollection();
			grid.setItems(userCollection);
			nameField.clear();
			salaryField.clear();
//			grid.setHeightByRows(userCollection.size());
			if(userCollection.size()<7) {
				grid.setHeightByRows(userCollection.size());
			}else {
				grid.setHeightByRows(6);
			}
			Notification.show("Details are successfully added");
			}
		 });
		 userCollection  =  javaMongoDemo.getUserCollection();
		 grid =new Grid<>(User.class);
		 grid.setWidth("96%");
		 grid.addStyleName("samplegrid");
		 grid.setSelectionMode(SelectionMode.NONE);
		 
		 grid.addComponentColumn(e -> {
		      Button button = new Button("Edit");
		      button.addStyleNames(ValoTheme.BUTTON_SMALL,ValoTheme.BUTTON_ICON_ONLY);
		      button.setIcon(FontAwesome.EDIT);
		      button.addClickListener(click ->{
		      updateButton.setVisible(true);
		      cancelButton.setVisible(true);
		      submitButton.setVisible(false);
//		            Notification.show("Clicked: " + ((User) e).getName());
		            editNameValue = ((User) e).getName();
				      nameField.setValue(editNameValue);
				      
				      editSalaryValue = ((User) e).getSalary();
				      salaryField.setValue(editSalaryValue);
				
				      });
		 
		    
		      return button;
		});
		 
		 
		 grid.addComponentColumn(e1 -> {
		       button2 = new Button("Delete");
		      button2.setIcon(FontAwesome.TRASH);
		      button2.addStyleNames(ValoTheme.BUTTON_SMALL,ValoTheme.BUTTON_ICON_ONLY);
		      button2.addClickListener(click ->{
		    	  Window window = new Window();
		    	  VerticalLayout vl = new VerticalLayout();
		      UI.getCurrent().addWindow(window);
		      window.setContent(vl);
		      window.center();
		      window.setModal(true);
//		      window.setHeight("180px");
		      HorizontalLayout deleteLayout = new HorizontalLayout();
		      Button confirmDelete = new Button("Confirm");
		      confirmDelete.addStyleNames(ValoTheme.BUTTON_SMALL);
		      Button cancelDelete = new Button("Cancel");
		      cancelDelete.addStyleName(ValoTheme.BUTTON_SMALL);
		      deleteLayout.addComponents(confirmDelete,cancelDelete);
		      vl.addComponent(new Label("Are you sure to Delete"));
		      vl.setWidth("250px");
//		      vl.setHeight("150px");
		      vl.addComponents(deleteLayout);
		      confirmDelete.addClickListener(e2->{
		    	  deleteNameValue = ((User) e1).getName();
				   javaMongoDemo.deleteDocument(deleteNameValue);
				   userCollection  =  javaMongoDemo.getUserCollection();
					grid.setItems(userCollection);
					grid.setHeightByRows(userCollection.size());
					Notification.show("Details are successfully deleted");
					window.close();
					
		      });
		          cancelDelete.addClickListener(e3->{
		        	 window.close(); 
		          });
				      });
		 
		    
		      return button2;
		});
		 
		System.out.println( grid.getColumns() );
		 updateButton.addClickListener(e->{
			 String name = nameField.getValue();
			String salary = salaryField.getValue();
			 if(name==null||name.trim().equalsIgnoreCase("")||salary==null||salary.trim().equalsIgnoreCase("")) {
				 Notification.show("please fill name,salary fields", Type.WARNING_MESSAGE);
			 }else {
			user.setName(name);
			user.setSalary(salary);
			javaMongoDemo.updateDocument(user,editNameValue,editSalaryValue);
			userCollection  =  javaMongoDemo.getUserCollection();
			grid.setItems(userCollection);
			updateButton.setVisible(false);
			cancelButton.setVisible(false);
			submitButton.setVisible(true);
			nameField.clear();
			salaryField.clear();
			grid.setHeightByRows(userCollection.size());
			Notification.show("Details are successfully updated");
			 }
		 });
		 
		 cancelButton.addClickListener(e->{
			 updateButton.setVisible(false);
			 cancelButton.setVisible(false);
			 submitButton.setVisible(true);
			 nameField.clear();
			 salaryField.clear();
		 });
		grid.setItems(userCollection);
		grid.setHeightByRows(userCollection.size());
	grid.getColumn("name").setExpandRatio(1);
	grid.getColumn("salary").setExpandRatio(1);
	grid.getColumn("id").setExpandRatio(3);

		mainLayout.addComponent(grid);
		return mainLayout;
	}

	
	
}
