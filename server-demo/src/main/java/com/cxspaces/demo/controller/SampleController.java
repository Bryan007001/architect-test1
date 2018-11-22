package com.cxspaces.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	@Autowired
	private GreetingService service;
	
    @RequestMapping("/")
    String home() {
        return service.sayHello();
    }
    
	@RequestMapping(value="/getSessionId")
	@ResponseBody
	public String getSessionId(HttpServletRequest request){
		return service.getSessionId(request);
	}
}

@Component
class GreetingService {
	public String getName() {
		return System.getProperty("instance-name", "UNKNOWN");
	}
	
	public String getSessionId(HttpServletRequest request){
		
		Object creator = request.getSession().getAttribute("creator");
		if(creator == null){
			creator = getName() + ":" + request.getLocalPort();
			request.getSession().setAttribute("creator", creator);
		}
		
		Integer count = 0;
		Object countObj = request.getSession().getAttribute("count");
		
		if (count instanceof Integer && countObj != null)
			count = (Integer)countObj;
		count ++;
		request.getSession().setAttribute("count", count);
		
		return "creator=" + creator +  " sessionId=" + request.getSession().getId() +"<br/>" + "count=" + count;
	}

	public String sayHello() {
		return "hello from service " + getName();
	}
}