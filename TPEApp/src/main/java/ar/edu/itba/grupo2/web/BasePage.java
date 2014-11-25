package ar.edu.itba.grupo2.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.grupo2.web.widget.PageHeader;

public class BasePage extends WebPage {
	
	
	public BasePage() {
		super();
		add(new PageHeader("page-header"));
	}

}
