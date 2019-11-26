//Brad Dennis
//Skills Test Json Rest Parser
package com.braddennis.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class DemoController {


 private String inboundJson="you should never see this";
 private String inboundTemp="you shouldnt see this";
 
 //request mapping handles all the http requests and headers
 //any invalid json data submitted will not be accepted and return an http error code
 @RequestMapping(value="/inboundJson",method = RequestMethod.POST)
 public JsonNode index(@RequestBody JsonNode jsonNode) {
        //jsonNode accepts any valid json data. this is also adds the application/json header
	 	this.inboundJson=jsonNode.toString();
        System.out.println("Received JSON:"+inboundJson);
        return jsonNode;
    }

//if JsoneNode recived invalid data this method will return an error until proper data is recived
 //created as a JSONObject allows us the return a proper formatted json file
@RequestMapping(value="/outboundJson",method = RequestMethod.GET)
public JSONObject JsonConvert() throws ParseException {
	
	//store data from JsonNode
	inboundTemp=inboundJson;
	
	//Creates Parser and parses the json data received from jsonNode
	JSONObject jsonObject = (JSONObject) new JSONParser().parse(inboundTemp);
	
	//creates object that gets the first level of the received Json
	JSONObject BillCycleExceptions = (JSONObject) jsonObject.get("BillCycleExceptions");
	
	//creates object that gets the second level of the received Json
	JSONObject results = (JSONObject) BillCycleExceptions.get("results");
	
	//creates array that gets the third level of the received Json
	JSONArray headers = (JSONArray) results.get("headers");
	
	//creates array that gets the last level of the received Json
	JSONArray rows = (JSONArray) results.get("rows");
	
	//stores json header information
	List<String> catagories = new ArrayList<String>();
	
	//creates json object that will allow us to build the new json tree in reverse
	JSONObject jsonTitle = new JSONObject();
	JSONObject jsonRows = new JSONObject();
	JSONObject jsonResults = new JSONObject();
	
	//creates array that will hold all of our data from rows
	JSONArray jsonArray = new JSONArray();
	
	//put does not allow something like ((String) (rowrow.get(1))) to be used so we use temp variable to store it
	String TempHeader="";
	String TempData="";
	
	//iterate through headers array 
	for (int i=0; i<headers.size();i++) {
		catagories.add((String) headers.get(i));
	} 
	
	
	//iterate though rows array
	for (int i=0; i<rows.size();i++) {
		
		//new jsonArray created every iteration through rows. This allows us to get data from multidemensional arrays
		JSONArray rowrow = (JSONArray) rows.get(i);
		
		//new Json object created every iteration through rows. Otherwise pointer only points to last element added
		JSONObject TempString = new JSONObject();
		
		//.put adds a new key value pair to the json object
		TempHeader=catagories.get(1);
		TempData=((String) (rowrow.get(1)));
		TempString.put(TempHeader,TempData);
	
	
		TempHeader=catagories.get(0);
		TempData=((String) (rowrow.get(0)));
		TempString.put(TempHeader,TempData);
	
		TempHeader=catagories.get(2);
		TempData=((String) (rowrow.get(2)));
		TempString.put(TempHeader,TempData);
		
		//.add adds the new json object to jsonArray as a new object
		jsonArray.add(TempString);
		
		
		
	} 
	
	//builds the converted json tree in reverse
	jsonRows.put("rows",jsonArray);
	jsonResults.put("results", jsonRows);
	jsonTitle.put("BillCycleExceptions", jsonResults);
	
	
	System.out.println(jsonTitle.toString());
	
	//returns an application/json file
	return jsonTitle;
}



}
