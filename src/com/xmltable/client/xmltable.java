package com.xmltable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class xmltable implements EntryPoint {
    private final String extention = ".xml";
    private FormPanel form = new FormPanel();
    private VerticalPanel mainPanel = new VerticalPanel();
    private String xmlText = null;
    final static Grid grid = new Grid(1, 5);
    private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
//        final Button button = new Button("Click me");
        final Button uploadButton = new Button("Upload");
        final Label label = new Label();
        final FileUpload fileUpload = new FileUpload();
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
//        form.setAction();
//        grid = new Grid(168, 5);
        grid.setText(0, 0, "Name");
        grid.setText(0, 1, "Email");
        grid.setText(0, 2, "Phone");
        grid.setText(0, 3, "State");
        grid.setText(0, 4, "Zip");
        grid.setBorderWidth(1);
        grid.setVisible(false);

        form.setAction(UPLOAD_ACTION_URL);

        fileUpload.setName("XMLUpload");

/*
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    xmltableService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });


*/


        uploadButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {


                //Get file name
                String filename = fileUpload.getFilename();


                // Check the length of the filename
                if (filename.length() != 0) {
                    // Get the extention
                    String fileExtention = filename.substring(filename.length() - extention.length(), filename.length());
                    // Check if the extention is '.pdf'
                    if (!fileExtention.equals(extention)) {
                        Window.alert("Only .xml files are allowed");
                    } else {
                        JavaScriptObject files = fileUpload.getElement().getPropertyJSO("files");

                        readTextFile(files);

                    }
                } else
                    Window.alert("No file choosen");
            }
        });
        RootPanel.get("slot1").add(label);
        RootPanel.get("slot2").add(fileUpload);
        RootPanel.get("slot3").add(grid);
        RootPanel.get("slot4").add(uploadButton);


    }

    public static void fileLoaded(String fileContents) {
        GWT.log("File contents: " + fileContents);
        parseMessage(fileContents);
    }

    public static native void readTextFile(JavaScriptObject files)
/*-{
    var reader = new FileReader();

    reader.onload = function (e) {


        @com.xmltable.client.xmltable::fileLoaded(*)(reader.result);
    }

    return reader.readAsText(files[0]);
}-*/;


    private static void parseMessage(String messageXml) {
        try {
            // parse the XML document into a DOM
            Document messageDom = XMLParser.parse(messageXml);
            int recordSize = messageDom.getElementsByTagName("record").getLength();
            //Пересчитываем количество строк в таблице
            grid.resizeRows(recordSize+1);

            //Показываем таблицу
            if (recordSize > 0) {
                grid.setVisible(true);
            }

            for (int i = 0; i < recordSize; i++) {

                String nameValue = messageDom.getElementsByTagName("Name").item(i)
                        .getFirstChild().getNodeValue();
                String emailValue = messageDom.getElementsByTagName("Email").item(i)
                        .getFirstChild().getNodeValue();
                String phoneValue = messageDom.getElementsByTagName("Phone").item(i)
                        .getFirstChild().getNodeValue();
                String stateValue = messageDom.getElementsByTagName("State").item(i)
                        .getFirstChild().getNodeValue();
                String zipValue = messageDom.getElementsByTagName("Zip").item(i)
                        .getFirstChild().getNodeValue();


                //i+1 потому, что нулевая строка таблицы - шапка
                grid.setText(i + 1, 0, nameValue);
                grid.setText(i + 1, 1, emailValue);
                grid.setText(i + 1, 2, phoneValue);
                grid.setText(i + 1, 3, stateValue);
                grid.setText(i + 1, 4, zipValue);
            }

            Window.alert("Файл успешно загружен и прочитан. Количество записей: " + recordSize);
        } catch (DOMException e) {
            Window.alert("Не получилось прочесть XML :(");
        }

    }
}



